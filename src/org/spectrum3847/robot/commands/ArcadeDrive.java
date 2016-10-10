package org.spectrum3847.robot.commands;

import org.spectrum3847.robot.HW;
import org.spectrum3847.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ArcadeDrive extends Command{
	
	public ArcadeDrive(){
		
	}

	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub
		Robot.drive.arcadeDrive(-1*HW.Driver_Gamepad.getLeftY(), HW.Driver_Gamepad.getRightX(), SmartDashboard.getBoolean("DriveBase Squared Inputs"));
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		
	}

}
