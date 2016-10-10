package org.spectrum3847.robot.commands;

import org.spectrum3847.lib.drivers.Gamepad;
import org.spectrum3847.robot.subsystems.SpeedCANSubsystem;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CANManualControl extends Command {

	private SpeedCANSubsystem subsystem;
	private Gamepad m_gamepad;
	private int m_axis;
    public CANManualControl(SpeedCANSubsystem s, Gamepad gamepad, int axis) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(s);
    	subsystem = s;
    	m_gamepad = gamepad;
    	m_axis = axis;    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if (subsystem.isSpeedMode()){
    		subsystem.setupForManual();;
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	subsystem.setSetpoint(m_gamepad.getRawAxis(m_axis));
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	subsystem.disable();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
