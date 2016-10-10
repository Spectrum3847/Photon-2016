package org.spectrum3847.robot.commands;

import org.spectrum3847.lib.drivers.Gamepad;
import org.spectrum3847.lib.util.Debugger;
import org.spectrum3847.robot.subsystems.MotorSubsystem;

public class AxisToMotor extends CommandBase {

	private MotorSubsystem subsystem;
	private Gamepad gamepad;
	private int axis;
	
	public AxisToMotor(String name, MotorSubsystem ms, Gamepad gp, int a) {
		super(name);
		subsystem = ms;
		gamepad = gp;
		axis = a;
		requires(ms);
	}


	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		double value = gamepad.getRawAxis(axis);
		subsystem.set(value);
		Debugger.println("COMMAND: " + getName() + "= " + value);
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {

	}

	@Override
	protected void interrupted() {
	}

}
