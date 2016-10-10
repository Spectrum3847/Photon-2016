package org.spectrum3847.robot.subsystems.controllers;

import org.spectrum3847.robot.Constants;
import org.spectrum3847.robot.subsystems.Drive;
import org.spectrum3847.lib.drivers.DriveSignal;
import org.spectrum3847.lib.trajectory.TrajectoryFollower;
import org.spectrum3847.lib.util.Pose;

/**
 * Controls the robot to turn in place
 */
public class TurnInPlaceController implements Drive.DriveController {
    private final TrajectoryFollowingPositionController mController;
    private final Pose mSetpointRelativePose;

    public TurnInPlaceController(Pose poseToContinueFrom, double destHeading, double velocity) {
        TrajectoryFollower.TrajectoryConfig config = new TrajectoryFollower.TrajectoryConfig();
        config.dt = Constants.kControlLoopsDt;
        config.max_acc = Constants.kTurnMaxAccelRadsPerSec2;
        config.max_vel = velocity;
        mController = new TrajectoryFollowingPositionController(
                Constants.kTurnKp,
                Constants.kTurnKi,
                Constants.kTurnKd,
                Constants.kTurnKv,
                Constants.kTurnKa,
                Constants.kTurnOnTargetError,
                config);
        TrajectoryFollower.TrajectorySetpoint initialSetpoint = new TrajectoryFollower.TrajectorySetpoint();
        initialSetpoint.pos = poseToContinueFrom.getHeading();
        initialSetpoint.vel = poseToContinueFrom.getHeadingVelocity();
        mController.setGoal(initialSetpoint, destHeading);

        mSetpointRelativePose = poseToContinueFrom;
    }

    @Override
    public DriveSignal update(Pose pose) {
        mController.update(pose.getHeading(), pose.getHeadingVelocity());
        double turn = mController.get();
        return new DriveSignal(turn, -turn);
    }

    @Override
    public Pose getCurrentSetpoint() {
        TrajectoryFollower.TrajectorySetpoint setpoint = mController.getSetpoint();
        // TODO: these encoder values are wrong, but this isn't a controller I want to use anyways
        return new Pose(
                mSetpointRelativePose.getLeftDistance(),
                mSetpointRelativePose.getRightDistance(),
                mSetpointRelativePose.getLeftVelocity(),
                mSetpointRelativePose.getRightVelocity(),
                setpoint.pos,
                setpoint.vel);
    }

    @Override
    public boolean onTarget() {
        return mController.isOnTarget();
    }

    public double getHeadingGoal() {
        return mController.getGoal();
    }
}

