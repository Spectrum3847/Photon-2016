package org.spectrum3847.robot.commands;

import org.spectrum3847.robot.subsystems.SpeedCANSubsystem;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CANRunAtSetpoint extends Command{

	private SpeedCANSubsystem subsystem;
	private String m_name;
	
    public CANRunAtSetpoint(String name, SpeedCANSubsystem s) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	subsystem = s;
    	m_name = name;
    }
    
    public String getName(){
    	return m_name;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if (!subsystem.isSpeedMode()){
    		subsystem.setupForSpeed();
    	}
    	subsystem.updatePIDFromDashboard();
    	subsystem.enable();
    	subsystem.getTalon().setPosition(0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	subsystem.updatePIDFromDashboard();
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
