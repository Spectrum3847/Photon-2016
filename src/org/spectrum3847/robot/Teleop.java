package org.spectrum3847.robot;

import org.spectrum3847.robot.commands.ArcadeDrive;
import org.spectrum3847.robot.commands.KickerManualControl;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.command.Scheduler;


/**
 * The Driver Control period of the competition
 */
public class Teleop {
	
    public static void init() {
        Scheduler.getInstance().removeAll();
        
        KickerManualControl kicker = new KickerManualControl("kicker", Robot.kicker, HW.Operator_Gamepad.getLeftY());
        kicker.start();
        System.out.println("TELEOP INIT");
        
        ArcadeDrive arcadeDrive = new ArcadeDrive();
        arcadeDrive.start();
        
        Robot.compressor.start();
        Robot.compressor.setClosedLoopControl(true);
        
        Robot.shooterCarriage.extend();
    }

    public static void periodic() {
    	Dashboard.updateDashboard();
        Scheduler.getInstance().run();
        
        
        
        //Tank Drive
        //Robot.drive.setOpenLoop(new DriveSignal(HW.Driver_Gamepad.getLeftY(), HW.Driver_Gamepad.getRightY()));
        
    }

    public static void cancel() {
        Scheduler.getInstance().removeAll();
    }
}
