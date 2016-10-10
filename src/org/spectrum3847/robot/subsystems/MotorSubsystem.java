package org.spectrum3847.robot.subsystems;

import org.spectrum3847.lib.drivers.SpectrumSpeedController;
import org.spectrum3847.lib.util.Debugger;
import org.spectrum3847.lib.util.Util;
import org.spectrum3847.robot.Robot;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class MotorSubsystem extends Subsystem {

	private SpectrumSpeedController speedController;
	private double max = 1;
	private double min = -1;
	private double maxCurrentFwd = 10000;
	private double maxCurrentRev = -10000;
	private boolean currentLimit = false;
	
	public MotorSubsystem(String n, SpectrumSpeedController sc, double maxVal, double minVal){
		super(n);
		speedController = sc;
		max = maxVal;
		min = minVal;
	}
	
	public MotorSubsystem(String n, int scPWM, int scPDP, double maxVal, double minVal){
		this(n, new SpectrumSpeedController(new Talon(scPWM), scPDP), maxVal, minVal);
	}
	
	public MotorSubsystem(String n, int scPWM, int scPDP){
		this(n, new SpectrumSpeedController(new Talon(scPWM), scPDP), 1, -1);
	}
	
	public MotorSubsystem(String n, SpectrumSpeedController sc){
		this(n, sc, 1, -1);
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
	}
	
	
	public void set(double value){
		value = Util.limit(value, max, min);
		
		//Check the current limit if it is enabled
		if (currentLimit){
			double current = getCurrent();
			if (current > maxCurrentFwd && value > 0){
				value = 0;
			} else if (current < maxCurrentRev && value < 0){
				value = 0;
			}
		}
		
		speedController.set(value);
		Debugger.println("MOTOR - " + getName() + ": " + value, Robot.output, Debugger.debug2);
	}
	
	public void setMax(double m){
		max = m;
	}
	
	public void setMin(double m){
		min = m;
	}
	
	//Set the max fwd current
	public void setMaxCurrentFwd(double c){
		maxCurrentFwd = c;
		currentLimit = true;
	}
	
	//Set the max rev current, SHOULD BE NEGATIVE
	public void setMaxCurrentRev(double c){
		maxCurrentRev = c;
		currentLimit = true;
	}
	
	public void disableCurrentLimit(){
		currentLimit = false;
	}
	
	public void enableCurrentLimit (){
		currentLimit = true;
	}
	
	public double getSpeed(){
		return speedController.get();
	}
	
	public double getCurrent(){
		return speedController.getCurrent();
	}
	
	public void setInverted(boolean value){
		speedController.setInverted(value);
	}
	
	public void disable(){
		speedController.disable();
	}

}
