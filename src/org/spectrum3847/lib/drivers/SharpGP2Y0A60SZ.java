package org.spectrum3847.lib.drivers;

import edu.wpi.first.wpilibj.AnalogInput;

/**
 * Code used to get distance from the Sharp GP2Y0A60SZ sensor
 * 
 * Sensor source: https://www.pololu.com/product/2474
 * 
 * @author Team254
 *
 */
public class SharpGP2Y0A60SZ extends AnalogInput {

    public SharpGP2Y0A60SZ(int channel) {
        super(channel);
    }

    public double getDistance() {
        return 19.739 * Math.pow(this.getVoltage(), -1.519);
    }

}
