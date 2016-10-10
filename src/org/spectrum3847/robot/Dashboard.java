package org.spectrum3847.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/*
 * @author matthew, JAG
 */
public class Dashboard {
	
	public static final boolean ENABLE_DASHBOARD = true;
	
	
	static final double SHORT_DELAY = .015;
    static final double LONG_DELAY = 1;
    
    static double shortOldTime = 0.0;
    static double longOldTime = 0.0;


    public static void intializeDashboard() {
    	if(ENABLE_DASHBOARD){
    		//SmartDashboard.putBoolean("Relay", false);
    		//SmartDashboard.putNumber("Motor 1", 0);
        	
    		SmartDashboard.putBoolean("Compressor", true);
    		SmartDashboard.putNumber("Shooter PID Front Tilt Speed", 5800);
    		SmartDashboard.putNumber("Shooter PID Front Flat Speed", 5800);
    		SmartDashboard.putNumber("Shooter PID Middle Speed", 3500);
    		SmartDashboard.putNumber("Shooter PID Rear Speed", 2400);
    		
    		SmartDashboard.putNumber("Shooter P_tilt", 1.0);
    		SmartDashboard.putNumber("Shooter I_tilt", 0);
    		SmartDashboard.putNumber("Shooter D_tilt", 0.5);
    		SmartDashboard.putNumber("Shooter F_tilt", 0.023);
    		SmartDashboard.putNumber("Shooter P_flat", 1.0);
    		SmartDashboard.putNumber("Shooter I_flat", 0);
    		SmartDashboard.putNumber("Shooter D_flat", 0.5);
    		SmartDashboard.putNumber("Shooter F_flat", 0.023);
    		SmartDashboard.putNumber("Shooter P_middle", 0.1);
    		SmartDashboard.putNumber("Shooter I_middle", 0);
    		SmartDashboard.putNumber("Shooter D_middle", 0.1);
    		SmartDashboard.putNumber("Shooter F_middle", .04);
    		SmartDashboard.putNumber("Shooter P_rear", 0.09);
    		SmartDashboard.putNumber("Shooter I_rear", 0);
    		SmartDashboard.putNumber("Shooter D_rear", 0.2);
    		SmartDashboard.putNumber("Shooter F_rear", .055);
    		SmartDashboard.putNumber("On Target Percentage", 10);
    		
    		SmartDashboard.putBoolean("DriveBase Squared Inputs", true);
    	}
    }

    private static void updatePutShort() {
    	//SmartDashboard.putNumber("Motor 1", Motor_1.get());
    	SmartDashboard.putNumber("Drive LeftY: ", HW.Driver_Gamepad.getLeftY());
    	SmartDashboard.putNumber("Drive RightX: ", HW.Driver_Gamepad.getRightX());
    	SmartDashboard.putNumber("Drive Trigger Left: ", HW.Driver_Gamepad.getLeftTrigger());
    	SmartDashboard.putNumber("Drive Trigger Right: ", HW.Driver_Gamepad.getRightTrigger());
    	SmartDashboard.putNumber("Drive Left:", Robot.leftDrive.get());
    	SmartDashboard.putNumber("Drive Right:", Robot.rightDrive.get());

    	SmartDashboard.putNumber("Tilt Speed", Robot.shooterWheelFrontTilt.getSpeed());
    	SmartDashboard.putNumber("Tilt Current Setpoint", Robot.shooterFrontTilt.getTalon().getSetpoint());
        SmartDashboard.putNumber("Tilt Error", Robot.shooterWheelFrontTilt.getTalon().getError());
    	SmartDashboard.putBoolean("Tilt on Target", Robot.shooterWheelFrontTilt.onTarget());
    	
        SmartDashboard.putNumber("Flat Speed", Robot.shooterWheelFrontFlat.getSpeed());
    	SmartDashboard.putNumber("Flat Current Setpoint", Robot.shooterFrontFlat.getTalon().getSetpoint());
    	SmartDashboard.putNumber("Flat Error", Robot.shooterWheelFrontFlat.getTalon().getError());
    	SmartDashboard.putBoolean("Flat on Target", Robot.shooterWheelFrontFlat.onTarget());
    	
    	SmartDashboard.putNumber("Middle Speed", Robot.shooterWheelMiddle.getSpeed());
    	SmartDashboard.putNumber("Middle Current Setpoint", Robot.shooterMiddle.getTalon().getSetpoint());
    	SmartDashboard.putNumber("Middle Error", Robot.shooterWheelMiddle.getTalon().getError());
    	SmartDashboard.putBoolean("Middle on Target", Robot.shooterWheelMiddle.onTarget());
    	
    	SmartDashboard.putNumber("Rear Speed", Robot.shooterWheelRear.getTalon().get());
    	SmartDashboard.putNumber("Rear Current Setpoint", Robot.shooterRear.getTalon().getSetpoint());
    	SmartDashboard.putNumber("Rear Error", Robot.shooterWheelRear.getTalon().getError());
    	SmartDashboard.putBoolean("Rear on Target", Robot.shooterWheelRear.onTarget());
    	
    }
    
    private static void updatePutLong(){
    	SmartDashboard.putBoolean("Compressor state", Robot.compressor.enabled());
    	SmartDashboard.putNumber("Compressor Current", Robot.compressor.getCompressorCurrent());
    	SmartDashboard.putNumber("Drive Left Current:", Robot.leftDrive.getSignedCurrent());
    	SmartDashboard.putNumber("Drive Right Current:", Robot.rightDrive.getSignedCurrent());
    	
    }

    
    public static void updateDashboard() {
    	if (ENABLE_DASHBOARD) {
            if ((Timer.getFPGATimestamp() - shortOldTime) > SHORT_DELAY) {
                shortOldTime = Timer.getFPGATimestamp();
                updatePutShort();
            }
            if ((Timer.getFPGATimestamp() - longOldTime) > LONG_DELAY) {
                //Thing that should be updated every LONG_DELAY
                longOldTime = Timer.getFPGATimestamp();
                updatePutLong();
            }
        }
    }
}
