package org.spectrum3847.robot.commands;

import org.spectrum3847.lib.drivers.Gamepad;
import org.spectrum3847.lib.util.Util;


/**
 *
 */
public class VibrateController extends CommandBase {

	private Gamepad gp;
	private double duration;
	private float intensity;
	private double startTime;
	
	/**
	 * Vibrate the gamepad for a number of seconds at a certain intensity 0-1.
	 * @param name
	 * @param g
	 * @param d
	 * @param i
	 */
    public VibrateController(String name, Gamepad g, double d, float i) {
    	super(name);
    	gp = g;
    	duration = d;
    	intensity = i;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	gp.rumble(intensity);
    	startTime = Util.getTime();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        double elapsed = Util.getTime() - startTime;
        if (duration <= elapsed){
        	return true;
        } else {
        	return false;
        }
    }

    // Called once after isFinished returns true
    protected void end() {
    	gp.rumble(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
