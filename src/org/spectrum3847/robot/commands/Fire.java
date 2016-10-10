package org.spectrum3847.robot.commands;

import org.spectrum3847.robot.Robot;
import org.spectrum3847.robot.subsystems.ShooterCarriage;
import edu.wpi.first.wpilibj.command.Command;

public class Fire extends Command{
	
	public Fire() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		Robot.shooterCarriage.retract();
	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub
		Robot.shooterCarriage.retract();
		System.out.println("FIRE EXECUTE");
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub
		Robot.shooterCarriage.extend();
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		end();
	}

}
