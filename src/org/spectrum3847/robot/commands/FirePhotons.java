package org.spectrum3847.robot.commands;

import org.spectrum3847.robot.Robot;
import org.spectrum3847.robot.subsystems.ShooterCarriage;
import edu.wpi.first.wpilibj.command.Command;

public class FirePhotons extends Command{
	
	public FirePhotons() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		Robot.photonCannon.photons();
	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub
		Robot.photonCannon.photons();
		System.out.println("FIREPHOTONS EXECUTE");
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub
		Robot.photonCannon.noPhotons();
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		end();
	}

}
