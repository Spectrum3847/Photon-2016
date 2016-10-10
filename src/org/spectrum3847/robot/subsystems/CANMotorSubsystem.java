package org.spectrum3847.robot.subsystems;

import org.spectrum3847.lib.drivers.SpectrumSpeedControllerCAN;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class CANMotorSubsystem extends Subsystem{

	private SpectrumSpeedControllerCAN specSpeedControllerCAN;
	
	public CANMotorSubsystem(String n, CANTalon t, int pdp_slot) {
		super(n);
		specSpeedControllerCAN = new SpectrumSpeedControllerCAN(new CANTalon[]{t}, new int[]{pdp_slot});
	}
	
	public CANMotorSubsystem(String n, int t, int pdp_slot) {
		this(n, new CANTalon(t), pdp_slot);
	}
	
	public CANMotorSubsystem(String n, CANTalon[] t, int[] pdp_slots) {
		super(n);
		specSpeedControllerCAN = new SpectrumSpeedControllerCAN(t, pdp_slots);	
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}
	
	public CANTalon getTalon(){
		return this.specSpeedControllerCAN.getTalon();
	}
	
	public SpectrumSpeedControllerCAN getSpecSpeedControllerCAN(){
		return specSpeedControllerCAN;
	}
	
	public double getPDPCurrent(){
		return specSpeedControllerCAN.getCurrent();
	}
}
