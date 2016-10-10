package org.spectrum3847.robot.subsystems.controllers;

import org.spectrum3847.lib.drivers.DriveSignal;
import org.spectrum3847.lib.trajectory.LegacyTrajectoryFollower;
import org.spectrum3847.lib.trajectory.Path;
import org.spectrum3847.lib.trajectory.Trajectory;
import org.spectrum3847.lib.util.ChezyMath;
import org.spectrum3847.lib.util.Pose;
import org.spectrum3847.robot.Constants;
import org.spectrum3847.robot.subsystems.Drive;

/**
 * DrivePathController.java This controller drives the robot along a specified
 * trajectory.
 *
 * @author Tom Bottiglieri FRC 254
 */
public class DrivePathController implements Drive.DriveController {

    public DrivePathController(Path path) {
        init();
        loadProfile(path.getLeftWheelTrajectory(), path.getRightWheelTrajectory(), 1.0, 0.0);
    }

    Trajectory trajectory;
    LegacyTrajectoryFollower followerLeft = new LegacyTrajectoryFollower("left");
    LegacyTrajectoryFollower followerRight = new LegacyTrajectoryFollower("right");
    double direction;
    double heading;
    double kTurn = -Constants.kDrivePathHeadingFollowKp;

    public boolean onTarget() {
        return followerLeft.isFinishedTrajectory();
    }

    private void init() {
        followerLeft.configure(Constants.kDrivePositionKp,
                Constants.kDrivePositionKi, Constants.kDrivePositionKd,
                Constants.kDrivePositionKv, Constants.kDrivePositionKa);
        followerRight.configure(Constants.kDrivePositionKp,
                Constants.kDrivePositionKi, Constants.kDrivePositionKd,
                Constants.kDrivePositionKv, Constants.kDrivePositionKa);
    }

    private void loadProfile(Trajectory leftProfile, Trajectory rightProfile,
                             double direction, double heading) {
        reset();
        followerLeft.setTrajectory(leftProfile);
        followerRight.setTrajectory(rightProfile);
        this.direction = direction;
        this.heading = heading;
    }

    public void loadProfileNoReset(Trajectory leftProfile,
                                   Trajectory rightProfile) {
        followerLeft.setTrajectory(leftProfile);
        followerRight.setTrajectory(rightProfile);
    }

    public void reset() {
        followerLeft.reset();
        followerRight.reset();
    }

    public int getFollowerCurrentSegmentNumber() {
        return followerLeft.getCurrentSegmentNumber();
    }

    public int getNumSegments() {
        return followerLeft.getNumSegments();
    }

    public void setTrajectory(Trajectory t) {
        this.trajectory = t;
    }

    public double getGoal() {
        return 0;
    }

    @Override
    public DriveSignal update(Pose pose) {
        if (onTarget()) {
            return new DriveSignal(0, 0);
        } else {
            double distanceL = direction * pose.getLeftDistance();
            double distanceR = direction * pose.getRightDistance();

            double speedLeft = direction * followerLeft.calculate(distanceL);
            double speedRight = direction * followerRight.calculate(distanceR);

            double goalHeading = followerLeft.getHeading();
            double observedHeading = -pose.getHeading();

            double angleDiffRads = ChezyMath.getDifferenceInAngleRadians(
                    observedHeading, goalHeading);
            double angleDiff = Math.toDegrees(angleDiffRads);

            double turn = kTurn * angleDiff;
            return new DriveSignal(speedLeft + turn, speedRight - turn);
        }
    }

    @Override
    public Pose getCurrentSetpoint() {
        return new Pose(followerLeft.getCurrentSegment().pos, 0, 0, 0, -followerLeft.getHeading(), 0);
    }
}
