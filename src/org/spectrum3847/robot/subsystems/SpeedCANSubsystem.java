package org.spectrum3847.robot.subsystems;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SpeedCANSubsystem extends CANMotorSubsystem {

	private boolean m_speedMode = true;
	
	public SpeedCANSubsystem(String n, CANTalon t, int pdp_slot) {
		super(n, t, pdp_slot);
		setupForSpeed();
		putPIDonDashboard();
	}

	public SpeedCANSubsystem(String n, int t, int pdp_slot) {
		super(n, t, pdp_slot);
		setupForSpeed();
		putPIDonDashboard();
	}

	public SpeedCANSubsystem(String n, CANTalon[] t, int[] pdp_slots) {
		super(n, t, pdp_slots);
		setupForSpeed();
		putPIDonDashboard();
	}

	
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
	}	
	
	public void defaultCommand(Command c){
		setDefaultCommand(c);
	}
	
	public void setupForSpeed(){
		this.getTalon().setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		this.getTalon().changeControlMode(CANTalon.TalonControlMode.Speed);
		m_speedMode = true;
	}
	
	public void setupForManual(){
		this.getTalon().changeControlMode(CANTalon.TalonControlMode.PercentVbus);
		m_speedMode = false;
		this.enable();
	}
	
	public boolean isSpeedMode(){
		return m_speedMode;
	}
	
	public void putPIDonDashboard(){
		SmartDashboard.putNumber(this.getName() + " P:", this.getTalon().getP());
		SmartDashboard.putNumber(this.getName() + " I:", this.getTalon().getI());
		SmartDashboard.putNumber(this.getName() + " D:", this.getTalon().getD());
		SmartDashboard.putNumber(this.getName() + " F:", this.getTalon().getF());
		SmartDashboard.putNumber(this.getName() + " Setpoint:", this.getTalon().getSetpoint());		
	}
	
	/**
	 * Full initializes all the values for the Dashboard
	 */
	public void putCompleteOnDashboard(){
		putPIDonDashboard();
		updateValuesToDashboard();
	}
	
	/**
	 * Call this when you want to update the values to the Dashboard, can be called in the Dashboard class
	 */
	public void updateValuesToDashboard(){
		SmartDashboard.putNumber(this.getName() + " Veloity:", this.getTalon().getEncVelocity());
		SmartDashboard.putNumber(this.getName() + " Error:", this.getTalon().getClosedLoopError());
		SmartDashboard.putNumber(this.getName() + " OutputVoltage:", this.getTalon().getOutputVoltage());
		SmartDashboard.putNumber(this.getName() + " OutputCurrent:", this.getTalon().getOutputCurrent());
		SmartDashboard.putNumber(this.getName() + " Position:", this.getTalon().getPosition());
	}
	
	/**
	 * Call this when you want to get the PID values from the Dashboard
	 */
	public void updatePIDFromDashboard(){
		setP(SmartDashboard.getNumber(this.getName() + " P:"));
		setI(SmartDashboard.getNumber(this.getName() + " I:"));
		setD(SmartDashboard.getNumber(this.getName() + " D:"));
		setF(SmartDashboard.getNumber(this.getName() + " F:"));
	}
	
	/**
	 * Call this when you want to get the full parameter list from the Dashboard including new setpoints
	 */
	public void updateCompleteDashboard(){
		updatePIDFromDashboard();
		setSetpoint(SmartDashboard.getNumber(this.getName() + " Setpoint:"));
	}
	
	public void setSetpoint(double s){
		this.getTalon().set(s);
	}
	
	public void setP(double p){
		this.getTalon().setP(p);
		SmartDashboard.putNumber(this.getName() + " P:", this.getTalon().getP());
	}
	
	public void setI(double i){
		this.getTalon().setI(i);
		SmartDashboard.putNumber(this.getName() + " I:", this.getTalon().getI());
	}
	
	public void setD(double d){
		this.getTalon().setD(d);
		SmartDashboard.putNumber(this.getName() + " D:", this.getTalon().getD());
	}
	
	public void setF(double f){
		this.getTalon().setF(f);
		SmartDashboard.putNumber(this.getName() + " F:", this.getTalon().getF());
	}	
	
	public void setPID(double p, double i, double d, double f){
		setP(p);
		setI(i);
		setD(d);
		setF(f);
	}
	
	public void enable(){
		this.getTalon().enableControl();
	}

	public void disable(){
		this.getTalon().disable();
	}
	
	
}
