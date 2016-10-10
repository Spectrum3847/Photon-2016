package org.spectrum3847.robot.commands;

import org.spectrum3847.lib.util.Debugger;
import org.spectrum3847.robot.HW;
import org.spectrum3847.robot.Robot;
import org.spectrum3847.robot.subsystems.MotorSubsystem;
import org.spectrum3847.robot.subsystems.KickerSystem;

public class KickerManualControl extends CommandBase {

	private KickerSystem subsystem;
	private double speed;
	
	public KickerManualControl(String name, KickerSystem ms, double s) {
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
		if(Math.abs(HW.Operator_Gamepad.getLeftY()) > .15){
			subsystem.enable();
			subsystem.set(HW.Operator_Gamepad.getLeftY());
		}
		else{
				subsystem.set(0);
		}
		//System.out.println("EXECUTING MOTORSETCOMMANDCAN");
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
