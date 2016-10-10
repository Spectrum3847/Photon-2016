package org.spectrum3847.robot.subsystems;

import org.spectrum3847.lib.drivers.SpectrumSolenoid;
import org.spectrum3847.lib.util.Debugger;
import org.spectrum3847.robot.Robot;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Allows us to easily create a class that is used to control a solenoid
 * @author JAG
 *
 */
public class SolenoidSubsystem extends Subsystem {

	private SpectrumSolenoid extendSol;
	private SpectrumSolenoid retractSol;
	private Boolean isExtended = false;
	private Boolean isDouble = false;
	private String name;
	
	public SolenoidSubsystem(String n, SpectrumSolenoid e, SpectrumSolenoid r) {
		super(n);
		name = n; 
		extendSol = e;
		retractSol = r;
		isDouble = true;
	}
	
	public SolenoidSubsystem(String n, SpectrumSolenoid e) {
		super(n);
		name = n;
		extendSol = e;
		isDouble = false;
	}
	
	public SolenoidSubsystem(String n, int e, int r){
		this(n, new SpectrumSolenoid(e), new SpectrumSolenoid(r));
	}
	
	public SolenoidSubsystem(String n, int e){
		this(n, new SpectrumSolenoid(e));
	}

	@Override
	protected void initDefaultCommand() {

	}
	
	public void extend(){
		extendSol.set(true);
		if (isDouble){
			retractSol.set(false);
		}
		isExtended = true;
		Debugger.println(name +": Extend", Robot.output, Debugger.info3);
		
	}

	public void retract(){
		extendSol.set(false);
		if (isDouble){
			retractSol.set(true);
		}
		isExtended = false;
		Debugger.println(name +": Retract", Robot.output, Debugger.info3);
	}
	
	public boolean isExtened(){
		return isExtended;
	}
}
