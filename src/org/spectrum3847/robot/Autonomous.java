package org.spectrum3847.robot;

import edu.wpi.first.wpilibj.command.Scheduler;


public class Autonomous {

    public static void init() {
    }

    //Periodic method called roughly once every 20ms
    public static void periodic() {
        Scheduler.getInstance().run();
        Dashboard.updateDashboard();
    }

    public static void cancel() {
        Scheduler.getInstance().removeAll();
    }
}
