package org.spectrum3847.robot.subsystems;

import org.spectrum3847.lib.drivers.DriveSignal;
import org.spectrum3847.lib.drivers.SpectrumSpeedController;
import org.spectrum3847.lib.trajectory.Path;
import org.spectrum3847.lib.util.Pose;
import org.spectrum3847.lib.util.StateHolder;
import org.spectrum3847.lib.util.Util;
import org.spectrum3847.robot.Constants;
import org.spectrum3847.robot.HW;
import org.spectrum3847.robot.subsystems.controllers.DriveFinishLineController;
import org.spectrum3847.robot.subsystems.controllers.DrivePathController;
import org.spectrum3847.robot.subsystems.controllers.DriveStraightController;
import org.spectrum3847.robot.subsystems.controllers.TurnInPlaceController;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.communication.UsageReporting;
import edu.wpi.first.wpilibj.communication.FRCNetworkCommunicationsLibrary.tInstances;
import edu.wpi.first.wpilibj.communication.FRCNetworkCommunicationsLibrary.tResourceType;

public class Drive extends Subsystem {

    public interface DriveController {
        DriveSignal update(Pose pose);
        Pose getCurrentSetpoint();
        public boolean onTarget();

    }

    public SpectrumSpeedController m_left_motor;
    public SpectrumSpeedController m_right_motor;
    public Encoder m_left_encoder;
    public Encoder m_right_encoder;
    //public GyroThread m_gyro; Will Replace with NavX
    private DriveController m_controller = null;

    protected final double m_inches_per_tick = Constants.kDriveWheelSizeInches
            * Math.PI / Constants.kDriveEncoderCountsPerRev;
    protected final double m_wheelbase_width = 26.0; // Get from CAD
    protected final double m_turn_slip_factor = 1.2; // Measure empirically
    private Pose m_cached_pose = new Pose(0, 0, 0, 0, 0, 0); // Don't allocate poses at 200Hz!

    public Drive(String name, SpectrumSpeedController left_drive,
    			SpectrumSpeedController right_drive, Encoder left_encoder,
                 Encoder right_encoder) {
        super(name);
        this.m_left_motor = left_drive;
        this.m_right_motor = right_drive;
        this.m_left_encoder = left_encoder;
        this.m_right_encoder = right_encoder;
        this.m_left_encoder.setDistancePerPulse(m_inches_per_tick);
        this.m_right_encoder.setDistancePerPulse(m_inches_per_tick);
        //this.m_gyro = gyro;
    }
    
    public Drive(String name, int left_drive, int left_drive_PDP,
    			int right_drive, int right_drive_PDP, Encoder left_encoder,
                 Encoder right_encoder){
    	this(name, new SpectrumSpeedController(new Talon(left_drive), left_drive_PDP), 
    			new SpectrumSpeedController(new Talon(right_drive), right_drive_PDP), 
    			left_encoder, right_encoder);
    }

	public void defaultCommand(Command c){
		setDefaultCommand(c);
	}
	
    public void setOpenLoop(DriveSignal signal) {
        m_controller = null;
        setDriveOutputs(signal);
    }
    
    /**
     * Arcade drive implements single stick driving. This function lets you
     * directly provide joystick values from any source.
     *$
     * @param moveValue The value to use for forwards/backwards
     * @param rotateValue The value to use for the rotate right/left
     * @param squaredInputs If set, decreases the sensitivity at low speeds
     */
    public void arcadeDrive(double moveValue, double rotateValue, boolean squaredInputs) {
      double leftMotorSpeed;
      double rightMotorSpeed;
      
      moveValue = Util.limit(moveValue, 1);
      rotateValue = Util.limit(rotateValue, 1);
      
      if (Math.abs(moveValue) < 0.1){
    	  moveValue = 0;
      }
      
      if (Math.abs(rotateValue) < 0.1){
    	  rotateValue = 0;
      }

      if (squaredInputs) {
        // square the inputs (while preserving the sign) to increase fine control
        // while permitting full power
        if (moveValue >= 0.0) {
          moveValue = (moveValue * moveValue);
        } else {
          moveValue = -(moveValue * moveValue);
        }
        if (rotateValue >= 0.0) {
          rotateValue = (rotateValue * rotateValue);
        } else {
          rotateValue = -(rotateValue * rotateValue);
        }
      }
      
      if (moveValue > 0.0) {
          if (rotateValue > 0.0) {
            leftMotorSpeed = moveValue - rotateValue;
            rightMotorSpeed = Math.max(moveValue, rotateValue);
          } else {
            leftMotorSpeed = Math.max(moveValue, -rotateValue);
            rightMotorSpeed = moveValue + rotateValue;
          }
        } else {
          if (rotateValue > 0.0) {
            leftMotorSpeed = -Math.max(-moveValue, rotateValue);
            rightMotorSpeed = moveValue + rotateValue;
          } else {
            leftMotorSpeed = moveValue - rotateValue;
            rightMotorSpeed = -Math.max(-moveValue, -rotateValue);
          }
        }
      
      
        this.setOpenLoop(new DriveSignal(leftMotorSpeed, rightMotorSpeed));
    }

    public void setDistanceSetpoint(double distance) {
        setDistanceSetpoint(distance, Constants.kDriveMaxSpeedInchesPerSec);
    }

    public void setDistanceSetpoint(double distance, double velocity) {
        // 0 < vel < max_vel
        double vel_to_use = Math.min(Constants.kDriveMaxSpeedInchesPerSec, Math.max(velocity, 0));
        m_controller = new DriveStraightController(
                getPoseToContinueFrom(false),
                distance,
                vel_to_use);
    }

    public void setTurnSetPoint(double heading) {
        setTurnSetPoint(heading, Constants.kTurnMaxSpeedRadsPerSec);
    }

    public void setTurnSetPoint(double heading, double velocity) {
        velocity = Math.min(Constants.kTurnMaxSpeedRadsPerSec, Math.max(velocity, 0));
        m_controller = new TurnInPlaceController(getPoseToContinueFrom(true), heading, velocity);
    }

    public void reset() {
        m_left_encoder.reset();
        m_right_encoder.reset();
    }

    public void setPathSetpoint(Path path) {
        reset();
        m_controller = new DrivePathController(path);
    }

    public void setFinishLineSetpoint(double distance, double heading) {
        reset();
        m_controller = new DriveFinishLineController(distance, heading, 1.0);
    }

    public void getState(StateHolder states) {
        //states.put("gyro_angle", m_gyro.getAngle());
        states.put("left_encoder", m_left_encoder.getDistance());
        states.put("left_encoder_rate", m_left_encoder.getRate());
        states.put("right_encoder_rate", m_right_encoder.getRate());
        states.put("right_encoder", m_right_encoder.getDistance());

        Pose setPointPose = m_controller == null
                ? getPhysicalPose()
                : m_controller.getCurrentSetpoint();
        states.put(
                "drive_set_point_pos",
                DriveStraightController.encoderDistance(setPointPose));
        states.put("turn_set_point_pos", setPointPose.getHeading());
        states.put("left_signal", m_left_motor.get());
        states.put("right_signal", m_right_motor.get());
        states.put("on_target", (m_controller != null && m_controller.onTarget()) ? 1.0 : 0.0);
    }

    public void update() {
        if (m_controller == null) {
            return;
        }
        setDriveOutputs(m_controller.update(getPhysicalPose()));
    }

    private void setDriveOutputs(DriveSignal signal) {
        m_left_motor.set(signal.leftMotor);
        m_right_motor.set(-signal.rightMotor);
    }

    private Pose getPoseToContinueFrom(boolean for_turn_controller) {
        if (!for_turn_controller && m_controller instanceof TurnInPlaceController) {
            Pose pose_to_use = getPhysicalPose();
            pose_to_use.m_heading = ((TurnInPlaceController) m_controller).getHeadingGoal();
            pose_to_use.m_heading_velocity = 0;
            return pose_to_use;
        } else if (m_controller == null || (m_controller instanceof DriveStraightController && for_turn_controller)) {
            return getPhysicalPose();
        } else if (m_controller instanceof DriveFinishLineController) {
            return getPhysicalPose();
        } else if (m_controller.onTarget()) {
            return m_controller.getCurrentSetpoint();
        } else {
            return getPhysicalPose();
        }
    }

    /**
     * @return The pose according to the current sensor state
     */
    public Pose getPhysicalPose() {
        m_cached_pose.reset(
                m_left_encoder.getDistance(),
                m_right_encoder.getDistance(),
                m_left_encoder.getRate(),
                m_right_encoder.getRate(),
                0,0); //m_gyro.getAngle(),
                //m_gyro.getRate());
        return m_cached_pose;
    }

    public Drive.DriveController getController() {
        return m_controller;
    }

    public void reloadConstants() {
        // TODO Auto-generated method stub

    }

    public boolean controllerOnTarget() {
        return m_controller != null && m_controller.onTarget();
    }

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
}
