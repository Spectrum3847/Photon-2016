package org.spectrum3847.robot.commands;

import org.spectrum3847.lib.util.Debugger;
import org.spectrum3847.robot.Robot;
import org.spectrum3847.robot.subsystems.MotorSubsystem;

public class MotorSetCommand extends CommandBase {

	private MotorSubsystem subsystem;
	private double speed;
	
	public MotorSetCommand(String name, MotorSubsystem ms, double s) {
		super(name);
		subsystem = ms;
		speed = s;
	}

	@Override
	protected void initialize() {
		subsystem.set(speed);
		Debugger.println("COMMAND: " + getName() + " = SPEED: " + speed, Robot.commands, Debugger.info3);
	}

	@Override
	protected void execute() {

	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		subsystem.disable();
		Debugger.println("COMMAND: " + getName() + " = STOPPED", Robot.commands, Debugger.info3);
	}

	@Override
	protected void interrupted() {
		end();
	}

}
