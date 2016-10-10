package org.spectrum3847.lib.drivers;

import org.spectrum3847.lib.util.Debugger;
import org.spectrum3847.robot.HW;
import org.spectrum3847.robot.Robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.SpeedController;

public class SpectrumSpeedControllerCAN implements SpeedController {
	
    protected CANTalon[] m_controllers;
    protected int[] m_pdp_slots;
    protected String name = "NAME UNASSIGNED";
    protected boolean m_invert = false; //Allows to invert open loop control as well

	public SpectrumSpeedControllerCAN(CANTalon controller, int pdp_slot) {
        m_controllers = new CANTalon[]{controller};
        m_pdp_slots = new int[]{pdp_slot};
        name = "first";
	}

	public SpectrumSpeedControllerCAN(int controller, int pdp_slot) {
        this(new CANTalon(controller), pdp_slot);
	}
	
	public SpectrumSpeedControllerCAN(CANTalon[] controllers, int[] pdp_slots) {
		m_controllers = controllers;
        m_pdp_slots = pdp_slots;
        for (int i = 1; i < m_controllers.length; i++) {
            m_controllers[i].changeControlMode(CANTalon.TalonControlMode.Follower);
            m_controllers[i].set(m_controllers[0].getDeviceID());
        }
        name = "second";
	}
	
	public void setName(String n){
		name = n;
	}
	
	public String getName(){
		return name;
	}
	
    protected double sign() {
        return (m_invert ? -1.0 : 1.0);
    }
	
    public void setInverted(boolean inverted) {
        m_invert = inverted;
    }

    public boolean getInverted() {
        return m_invert;
    }
    
    public double getCurrent() {
        double current = 0.0;
        for (int slot : m_pdp_slots) {
            current += HW.PDP.getCurrent(slot);
        }
        return current;
    }
    
    public double getSignedCurrent() {
        return getCurrent() * Math.signum(get());
    }
    
    public double get() {
        return m_controllers[0].get() * sign();
    }

	@Override
	public void pidWrite(double output) {
            m_controllers[0].pidWrite(output * sign());
	}

	@Override
	public void set(double speed, byte DoesntDoAnything) {
		m_controllers[0].set(speed * sign());
	}

	@Override
	public void set(double speed) {
		m_controllers[0].set(speed * sign());
	}

	@Override
	public void disable() {
		m_controllers[0].disableControl();
	    Debugger.println("CAN Talon: " + getTalon().getDeviceID() + ": Disabled Control", Robot.output, Debugger.info3  );
	}
	
	public void enable(){
		m_controllers[0].enableControl();
	    Debugger.println("CAN Talon: " + getTalon().getDeviceID() + ": Enabled Control", Robot.output, Debugger.info3  );
	}
	
	public CANTalon getTalon(){
		return m_controllers[0];
	}

	@Override
	public void stopMotor() {
		// TODO Auto-generated method stub
		
	}

}
