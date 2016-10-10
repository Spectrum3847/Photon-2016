package org.spectrum3847.robot.commands;

import org.spectrum3847.lib.util.Debugger;
import org.spectrum3847.robot.subsystems.SolenoidSubsystem;

public class SolenoidCommand extends CommandBase {

	protected SolenoidSubsystem subsystem;
	protected boolean extend = true;
	
	public SolenoidCommand(String name, SolenoidSubsystem ss, boolean isExtendCommand) {
		super(name);
		subsystem = ss;
		extend = isExtendCommand;
		requires(ss);
	}

	public SolenoidCommand(String name, SolenoidSubsystem ss) {
		super(name);
		subsystem = ss;
		extend = true;
		requires(ss);
	}
	
	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		if (extend){
			subsystem.extend();
		} else {
			subsystem.retract();
		}
		Debugger.println("COMMAND: " + getName());
	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub

	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * When the command ends return it to the other position
	 */
	protected void end() {
		if (!extend){
			subsystem.extend();
		} else {
			subsystem.retract();
		}
	}

	@Override
	protected void interrupted() {
		end();
	}

}
