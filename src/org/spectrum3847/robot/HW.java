package org.spectrum3847.robot;

import org.spectrum3847.lib.drivers.Gamepad;

import edu.wpi.first.wpilibj.PowerDistributionPanel;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class HW {
    // For example to map the left and right motors, you could define the
    // following variables to use with your drivetrain subsystem.
    // public static int leftMotor = 1;
    // public static int rightMotor = 2;
	//Use ecplise refacotr tool to rename values for your specific robot
	
	//OI
	public static OI oi;
	public static final Gamepad Driver_Gamepad = new Gamepad("Driver", HW.USBPORT_0);
    public static final Gamepad Operator_Gamepad = new Gamepad("Operator", HW.USBPORT_1);
	
	//PDP Panel
	public static PowerDistributionPanel PDP = new PowerDistributionPanel();
	
    /**PDP Slots**/
	public static final int LEFT_REAR_DRIVE_MOTOR_PDP = 0;
	public static final int LEFT_FRONT_DRIVE_MOTOR_PDP = 1;
	public static final int PWM_2_PDP = 2;
	public static final int PWM_3_PDP = 3;
	public static final int KICKER_MOTOR_ONE_PDP = 4;
	public static final int KICKER_MOTOR_TWO_PDP = 5;
	public static final int PWM_6_PDP = 6;
	public static final int PWM_7_PDP = 7;
	public static final int RIGHT_REAR_DRIVE_MOTOR_PDP = 8;
	public static final int RIGHT_FRONT_DRIVE_MOTOR_PDP = 9;
	public static final int SHOOTER_CAN_1_PDP = 1;
	public static final int CAN_2_PDP = 11;
	
	
	/**MOTOR ASSIGNMENTS (PWM)**/
    public static final int LEFT_REAR_DRIVE_MOTOR = 0;
    public static final int LEFT_FRONT_DRIVE_MOTOR = 1;
    public static final int PWM_2 = 2;
    public static final int PWM_3 = 3;
    public static final int PWM_4 = 4;
    public static final int PWM_5 = 5;
    public static final int PWM_6 = 6;
    public static final int PWM_7 = 7;
    public static final int RIGHT_REAR_DRIVE_MOTOR = 8;
    public static final int RIGHT_FRONT_DRIVE_MOTOR = 9;
    public static final int PWM_10 = 10;
    public static final int PWM_11 = 11;
    public static final int PWM_12 = 12;
    public static final int PWM_13 = 13;
    public static final int SHOOTER_MOTOR_FRONT_TILT_PDP= 14;
    public static final int SHOOTER_MOTOR_FRONT_FLAT_PDP = 15;
    public static final int SHOOTER_MOTOR_MIDDLE_LEFT_PDP = 16;
    public static final int SHOOTER_MOTOR_MIDDLE_RIGHT_PDP = 17;
    public static final int SHOOTER_MOTOR_REAR_LEFT_PDP = 18;
    public static final int SHOOTER_MOTOR_REAR_RIGHT_PDP = 19;


    /**NON-DRIVEBASE MOTOR ASSIGNMENTS (CAN)**/
    public static final int SHOOTER_MOTOR_FRONT_TILT= 1;
    public static final int SHOOTER_MOTOR_FRONT_FLAT = 2;
    public static final int SHOOTER_MOTOR_MIDDLE_LEFT = 3;
    public static final int SHOOTER_MOTOR_MIDDLE_RIGHT = 4;
    public static final int SHOOTER_MOTOR_REAR_LEFT = 5;
    public static final int SHOOTER_MOTOR_REAR_RIGHT = 6;
    public static final int KICKER_MOTOR_ONE = 20;
    public static final int KICKER_MOTOR_TWO = 21;
    
    /**DIGITAL SENSOR ALLOCATIONS**/
    public static final int DIGITAL_IO_1 = 1; 
    public static final int DIGITAL_IO_2 = 2;
    public static final int DIGITAL_IO_3 = 3;
    public static final int DIGITAL_IO_4 = 4; 
    public static final int DIGITAL_IO_5 = 5;
    public static final int DIGITAL_IO_6 = 6; 
    public static final int DIGITAL_IO_7 = 7; 
    public static final int DIGITAL_IO_8 = 8;
    public static final int DIGITAL_IO_9 = 9;  
    
    /**Pneumatics**/
    public static final int CARRIAGE_EXTEND = 0;
    public static final int CARRIAGE_RETRACT = 7;
    public static final int PHOTON_CANNON_SOLENOID = 2;
    public static final int SOL_3 = 3;
    public static final int SOL_4 = 4;
    public static final int CARRIAGE_SINGLE = 5;
    public static final int SOL_6 = 6;
    public static final int SOL_7 = 7;
    
    /**ANALOG SENSOR ALLOCATIONS**/
    public static final int PRESSURE_TRANSDUCER = 0;
    public static final int ANALOG_IN_1 = 1;
    public static final int ANALOG_IN_2 = 2;
    public static final int ANALOG_IN_3 = 3;
    
    /**RELAY ALLOCATIONS**/
    public static final int RELAY_ZERO = 0;
    public static final int RELAY_ONE = 1;
    public static final int RELAY_TWO = 2;
    public static final int RELAY_THREE = 3;

    /**JOYSTICKS/GAMEPAD ASSIGNMENTS**/
    public static final int USBPORT_0 = 0;
    public static final int USBPORT_1 = 1;
    public static final int USBPORT_2 = 2;
    public static final int USBPORT_3 = 3;
    public static final int USBPORT_4 = 4;
    public static final int DSControllerPort = 5;
}