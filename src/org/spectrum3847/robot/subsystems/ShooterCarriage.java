package org.spectrum3847.robot.subsystems;

import org.spectrum3847.lib.drivers.SpectrumSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class ShooterCarriage extends Subsystem{

	private SpectrumSolenoid extendSol;
	private SpectrumSolenoid retractSol;
	private Boolean isExtended = false;
	private Boolean isDouble = false;
	private String name;
	
	public ShooterCarriage(String n, SpectrumSolenoid e, SpectrumSolenoid r) {
		super(n);
		name = n;
		extendSol = e;
		retractSol = r;
		isDouble = true;

		retractSol.set(false);
		extendSol.set(true);

	}

	public ShooterCarriage(String n, SpectrumSolenoid r) {
		super(n);
		name = n;
		retractSol = r;
		isDouble = false;

		retractSol.set(true);

	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
	
	public void extend(){
		System.out.println("CARRIAGE EXTEND");
		retractSol.set(false);
		if(isDouble){
			extendSol.set(true);
	}
		isExtended = true;
	}
	
	public void retract(){
		System.out.println("CARRIAGE RETRACT");
		if(isDouble){
			extendSol.set(false);
		}
		retractSol.set(true);
		isExtended = false;
	}
	
	public boolean isExtended(){
		return isExtended;
	}

}
