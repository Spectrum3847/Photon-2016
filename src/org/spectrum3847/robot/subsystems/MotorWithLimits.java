package org.spectrum3847.robot.subsystems;

import org.spectrum3847.lib.drivers.SpectrumDigitalInput;
import org.spectrum3847.lib.drivers.SpectrumSpeedController;
import org.spectrum3847.lib.util.Debugger;
import org.spectrum3847.robot.Robot;

import edu.wpi.first.wpilibj.Talon;

public class MotorWithLimits extends MotorSubsystem {

	private SpectrumDigitalInput fwdLimit;
	private SpectrumDigitalInput revLimit;
	private String name = "";
	
	public MotorWithLimits(String n, SpectrumSpeedController sc, SpectrumDigitalInput fwd, SpectrumDigitalInput rev){
		super(n, sc);
		name = n;
		fwdLimit = fwd;
		revLimit = rev;
	}
	
	public MotorWithLimits(String n, int scPWM, int scPDP, int fwd, int rev){
		this(n, new SpectrumSpeedController(new Talon(scPWM), scPDP), new SpectrumDigitalInput(fwd), new SpectrumDigitalInput(rev));
	}
	
	
	public void set(double value){
		if (value > 0 && fwdLimit.get()){
			super.set(0);
			Debugger.println("LIMIT FWD: " + name, Robot.output, Debugger.info3);
		} else if (value < 0 && revLimit.get()){
			super.set(0);
			Debugger.println("LIMIT REV: " + name, Robot.output, Debugger.info3);
		} else {
			super.set(value);
		}
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
	}

}
