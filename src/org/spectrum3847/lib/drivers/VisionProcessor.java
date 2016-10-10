package org.spectrum3847.lib.drivers;

import java.util.Comparator;
import java.util.Vector;

import org.spectrum3847.robot.Constants;
import com.ni.vision.NIVision;
import com.ni.vision.NIVision.DrawMode;
import com.ni.vision.NIVision.GetImageSizeResult;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ImageType;
import com.ni.vision.NIVision.Rect;
import com.ni.vision.NIVision.ShapeMode;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class VisionProcessor extends Thread {

	private boolean runState = true;
	private double center;

	private int session;

	//Images
	private Image frame;
	private Image binaryFrame;

	//Constants
	private static final NIVision.Range R_RANGE = new NIVision.Range(210, 255);	//Default hue range for yellow tote
	private static final NIVision.Range G_RANGE = new NIVision.Range(150, 255);	//Default saturation range for yellow tote
	private static final NIVision.Range B_RANGE = new NIVision.Range(0, 100);	//Default value range for yellow tote
	private static final double AREA_MINIMUM = 0.5; //Default Area minimum for particle as a percentage of total image area
	private static final NIVision.ParticleFilterCriteria2[] CRITERIA = new NIVision.ParticleFilterCriteria2[1];
	private static final NIVision.ParticleFilterOptions2 FILTER_OPTIONS = new NIVision.ParticleFilterOptions2(0, 0, 1, 1);

	public VisionProcessor() {
		// create images
		frame = NIVision.imaqCreateImage(ImageType.IMAGE_RGB, 0);
		binaryFrame = NIVision.imaqCreateImage(ImageType.IMAGE_U8, 0);
		CRITERIA[0] = new NIVision.ParticleFilterCriteria2(NIVision.MeasurementType.MT_AREA_BY_IMAGE_AREA, AREA_MINIMUM, 100.0, 0, 0);

		createSession();
	}

	private void createSession() {
		try {
			session = NIVision.IMAQdxOpenCamera("cam0",
					NIVision.IMAQdxCameraControlMode.CameraControlModeController);
			NIVision.IMAQdxConfigureGrab(session);
		} catch (Exception e) { }
	}

	public void stopVision() {
		runState = false;
		interrupt();
	}

	public double getCenter() {
		return center - Constants.kCenter_Calibration;
	}

	public void run() {
		while (runState) {
			double  newCenter = 0;
			if (session == 0) {
				createSession();
				newCenter = 0;
			} else {
				NIVision.IMAQdxGrab(session, frame, 1);
				GetImageSizeResult size = NIVision.imaqGetImageSize(frame);
				
				Rect topMask = new Rect();
				topMask.left = 0;
				topMask.top = 0;
				topMask.height = size.height / 2;
				topMask.width = size.width;
				
				Rect leftMask = new Rect();
				leftMask.left = 0;
				leftMask.top = 0;
				leftMask.height = size.height;
				leftMask.width = size.width / 4;
				
				Rect rightMask = new Rect();
				rightMask.left = (int)(size.width * 0.75);
				rightMask.top = 0;
				rightMask.height = size.height;
				rightMask.width = size.width / 4;
				
				NIVision.imaqDrawShapeOnImage(frame, frame, topMask, DrawMode.PAINT_VALUE, ShapeMode.SHAPE_RECT, 1);
				NIVision.imaqDrawShapeOnImage(frame, frame, leftMask, DrawMode.PAINT_VALUE, ShapeMode.SHAPE_RECT, 1);
				NIVision.imaqDrawShapeOnImage(frame, frame, rightMask, DrawMode.PAINT_VALUE, ShapeMode.SHAPE_RECT, 1);

				//Threshold the image looking for yellow (tote color)
				NIVision.imaqColorThreshold(binaryFrame, frame, 255, NIVision.ColorMode.RGB, R_RANGE, G_RANGE, B_RANGE);

				//Send particle count to dashboard
				int numParticles = NIVision.imaqCountParticles(binaryFrame, 1);

				//filter out small particles
				float areaMin = (float)SmartDashboard.getNumber("Area min %", AREA_MINIMUM);
				CRITERIA[0].lower = areaMin;
				NIVision.imaqParticleFilter4(binaryFrame, binaryFrame, CRITERIA, FILTER_OPTIONS, null);

				//Send particle count after filtering to dashboard
				numParticles = NIVision.imaqCountParticles(binaryFrame, 1);

				SmartDashboard.putNumber("Number of Particles", numParticles);
				if (numParticles > 0) {
					//Measure particles and sort by particle size
					Vector<ParticleReport> particles = new Vector<ParticleReport>();
					for (int particleIndex = 0; particleIndex < numParticles; particleIndex++) {
						ParticleReport par = new ParticleReport();
						par.PercentAreaToImageArea = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_AREA_BY_IMAGE_AREA);
						par.Area = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_AREA);
						par.ConvexHullArea = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_CONVEX_HULL_AREA);
						par.BoundingRectTop = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_TOP);
						par.BoundingRectLeft = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_LEFT);
						par.BoundingRectBottom = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_BOTTOM);
						par.BoundingRectRight = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_RIGHT);
						particles.add(par);
					}
					particles.sort(null);

					ParticleReport tote = particles.get(particles.size() - 1);
					newCenter = (tote.BoundingRectRight + tote.BoundingRectLeft) / 2.0;
					newCenter = newCenter - (size.width / 2);
					
					SmartDashboard.putNumber("Vision Percentage", tote.PercentAreaToImageArea);
					if (tote.PercentAreaToImageArea > 35) {
						center = 0;
						continue;
					}

					Rect square = new Rect();
					square.left = (int)tote.BoundingRectLeft;
					square.top = (int)tote.BoundingRectTop;
					square.height = (int)(tote.BoundingRectBottom - tote.BoundingRectTop);
					square.width = (int)(tote.BoundingRectRight - tote.BoundingRectLeft);

					Rect dot = new Rect();
					dot.left = (int)((tote.BoundingRectRight + tote.BoundingRectLeft) / 2);
					dot.top = (int)((tote.BoundingRectTop + tote.BoundingRectBottom) / 2);
					dot.height = 5;
					dot.width = 5;

					NIVision.imaqDrawShapeOnImage(frame, frame, square, DrawMode.DRAW_VALUE, ShapeMode.SHAPE_RECT, 1);
					NIVision.imaqDrawShapeOnImage(frame, frame, dot, DrawMode.PAINT_VALUE, ShapeMode.SHAPE_OVAL, 5);
				} else {
					newCenter = 0;
				}


				NIVision.Point topCenter = new NIVision.Point(size.width / 2, 0);
				NIVision.Point bottomCenter = new NIVision.Point(size.width / 2, size.height - 1);
				NIVision.imaqDrawLineOnImage(frame, frame, DrawMode.DRAW_VALUE, topCenter, bottomCenter, 255);

				CameraServer.getInstance().setImage(frame);
				Timer.delay(0.005);
			}
			center = newCenter;
		}
	}

	//A structure to hold measurements of a particle
	class ParticleReport implements Comparator<ParticleReport>, Comparable<ParticleReport> {
		double PercentAreaToImageArea;
		double Area;
		double ConvexHullArea;
		double BoundingRectLeft;
		double BoundingRectTop;
		double BoundingRectRight;
		double BoundingRectBottom;

		public int compareTo(ParticleReport r) {
			return (int)(r.Area - this.Area);
		}

		public int compare(ParticleReport r1, ParticleReport r2) {
			return (int)(r1.Area - r2.Area);
		}
	};
}
