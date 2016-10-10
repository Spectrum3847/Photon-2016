package org.spectrum3847.lib.drivers;

import edu.wpi.first.wpilibj.Encoder;

public class SpectrumEncoder extends Encoder{
	
	private double wheelDiameter = 6;
	private int pulsesPerRevolution = 0;

	private SpectrumEncoder(int aChannel, int bChannel) {
		// TODO Auto-generated constructor stub
		super(aChannel, bChannel);
		this.setDistancePerPulse((Math.PI*(wheelDiameter))/pulsesPerRevolution);
	}
	
	public SpectrumEncoder (int aChannel, int bChannel, int pulses, double wheel){
		super(aChannel, bChannel);
		pulsesPerRevolution = pulses;
		wheelDiameter = wheel;
		this.setDistancePerPulse((Math.PI*(wheelDiameter))/pulsesPerRevolution);
	}
	
	public SpectrumEncoder (int aChannel, int bChannel, int pulses){
		super(aChannel, bChannel);
		pulsesPerRevolution = pulses;
		this.setDistancePerPulse((Math.PI*(wheelDiameter))/pulsesPerRevolution);
	}

	public void setPulsesPerRev(int p){
		pulsesPerRevolution = p;
		this.setDistancePerPulse((Math.PI*(wheelDiameter))/pulsesPerRevolution);
	}
	
	public int getPulsesPerRev(){
		return pulsesPerRevolution;
	}
	
	/**
	 * Set the Wheel Diameter Setting
	 * @param diameter - The New Wheel Diameter
	 */
	public void setWheelDiameter(double diameter){
		wheelDiameter = Math.abs(diameter);
		this.setDistancePerPulse((Math.PI*(wheelDiameter))/pulsesPerRevolution);
	}
	
	/**
	 * Get the Wheel Diameter Setting
	 * @return Wheel Diameter
	 */
	public double getWheelDiameter(){
		return wheelDiameter;
	}
	
	public double getRawDistance(){
		return this.getDistance();
	}
	
	public double getRevolutions(){
		return getRawDistance()/pulsesPerRevolution;
	}
	
	public double getDistanceInches(){
		return this.getDistance();
	}
	
	public double getDistanceFeet(){
		return this.getDistanceInches()/12.0;
	}
}
