package org.spectrum3847.lib.drivers;

import org.spectrum3847.robot.HW;
import edu.wpi.first.wpilibj.SpeedController;

/**
 * Based on the team254 frc2015 code.
 * 
 * @author JAG
 *
 */

public class SpectrumSpeedController implements SpeedController {
    protected SpeedController[] m_controllers;
    protected int[] m_pdp_slots;
    protected boolean m_invert = false;

    public SpectrumSpeedController(SpeedController controller, int pdp_slot) {
        m_controllers = new SpeedController[]{controller};
        m_pdp_slots = new int[]{pdp_slot};
    }

    public SpectrumSpeedController(SpeedController controller, int[] pdp_slots) {
        m_controllers = new SpeedController[]{controller};
        m_pdp_slots = pdp_slots;  
    }

    public SpectrumSpeedController(SpeedController[] controllers, int[] pdp_slots) {
        assert (controllers.length == pdp_slots.length);
        m_controllers = controllers;
        m_pdp_slots = pdp_slots;
    }

    public void setInverted(boolean inverted) {
        m_invert = inverted;
    }

    public boolean getInverted() {
        return m_invert;
    }

    protected double sign() {
        return (m_invert ? -1.0 : 1.0);
    }

    public double getCurrent() {
        double current = 0.0;
        for (int slot : m_pdp_slots) {
            current += HW.PDP.getCurrent(slot);
        }
        return current;
    }

    public double getSignedCurrent() {
        return getCurrent() * Math.signum(get()) * sign();
    }

    @Override
    public void pidWrite(double output) {
        for (SpeedController controller : m_controllers) {
            controller.pidWrite(sign() * output);
        }
    }

    @Override
    public double get() {
        return m_controllers[0].get() * sign();
    }

    @Override
    public void set(double speed, byte syncGroup) {
        for (SpeedController controller : m_controllers) {
            controller.set(speed * sign(), syncGroup);
        }
    }

    @Override
    public void set(double speed) {
        for (SpeedController controller : m_controllers) {
            controller.set(speed * sign());
        }
    }

    @Override
    public void disable() {
        for (SpeedController controller : m_controllers) {
            controller.disable();
        }
    }

	@Override
	public void stopMotor() {
		// TODO Auto-generated method stub
		
	}
}