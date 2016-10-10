
package org.spectrum3847.robot;

import org.spectrum3847.lib.drivers.SpectrumSolenoid;
import org.spectrum3847.lib.drivers.SpectrumSpeedController;
import org.spectrum3847.lib.drivers.SpectrumSpeedControllerCAN;
import org.spectrum3847.lib.util.Debugger;
import org.spectrum3847.robot.commands.ArcadeDrive;
import org.spectrum3847.robot.commands.KickerManualControl;
import org.spectrum3847.robot.subsystems.Drive;
import org.spectrum3847.robot.subsystems.KickerSystem;
import org.spectrum3847.robot.subsystems.MotorWithLimits;
import org.spectrum3847.robot.subsystems.PhotonCannon;
import org.spectrum3847.robot.subsystems.ShooterCarriage;
import org.spectrum3847.robot.subsystems.ShooterWheel;
import org.spectrum3847.robot.subsystems.SolenoidSubsystem;
import org.spectrum3847.robot.subsystems.SpeedCANSubsystem;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	
    //Add Debug flags
	//You can have a flag for each subsystem, etc
	public static final String output = "OUT";
	public static final String input = "IN";
	public static final String controls = "CONTROL";
	public static final String general = "GENERAL";
	public static final String auton = "AUTON";
	public static final String commands = "COMMAND";
	
	// Create a single static instance of all of your subsystems
    // This MUST be here. If the OI creates Commands (which it very likely
    // will), constructing it during the construction of CommandBase (from
    // which commands extend), subsystems are not guaranteed to be
    // yet. Thus, their requires() statements may grab null pointers. Bad
    // news. Don't move it.
	
	public static SpectrumSpeedController rightDrive;
	public static SpectrumSpeedController leftDrive;
	public static SpectrumSpeedControllerCAN shooterFrontTilt;
	public static SpectrumSpeedControllerCAN shooterFrontFlat;
	public static SpectrumSpeedControllerCAN shooterMiddle;
	public static SpectrumSpeedControllerCAN shooterRear;
	public static SpectrumSpeedControllerCAN kickerController;
	public static Drive drive; 
	public static Compressor compressor;
	public static ShooterWheel shooterWheelFrontTilt;
	public static ShooterWheel shooterWheelFrontFlat;
	public static ShooterWheel shooterWheelMiddle;
	public static ShooterWheel shooterWheelRear;
	public static KickerSystem kicker;
	public static SpectrumSolenoid shooterCarriageExtendSolenoid;
	public static SpectrumSolenoid shooterCarriageRetractSolenoid;
	public static ShooterCarriage shooterCarriage;
	public static SpectrumSolenoid photonCannonSolenoid;
	public static PhotonCannon photonCannon;
	
    public static void setupSubsystems(){
    	compressor = new Compressor(0);
    	
    	
    	
    	//Setup a Solenoid Subsystem and give it an initial state
   
	    //Setup Drive Motor Speed Controllers
	    Spark LR_CIM = new Spark(HW.LEFT_REAR_DRIVE_MOTOR);
	    Spark LF_CIM = new Spark(HW.LEFT_FRONT_DRIVE_MOTOR);
	    Spark RR_CIM = new Spark(HW.RIGHT_REAR_DRIVE_MOTOR);
	    Spark RF_CIM = new Spark(HW.RIGHT_FRONT_DRIVE_MOTOR);
	
	    	
	    rightDrive = new SpectrumSpeedController(
					new SpeedController[] {LR_CIM,LF_CIM}, 
					new int[] {HW.LEFT_REAR_DRIVE_MOTOR_PDP, HW.LEFT_FRONT_DRIVE_MOTOR_PDP}
				);
		rightDrive.setInverted(true);
		
		leftDrive = new SpectrumSpeedController(
				new SpeedController[] {RR_CIM, RF_CIM}, 
				new int[] {HW.RIGHT_REAR_DRIVE_MOTOR_PDP, HW.RIGHT_FRONT_DRIVE_MOTOR_PDP}
				);
		leftDrive.setInverted(true);
		
		drive = new Drive("defaultDrive",
							leftDrive, 
							rightDrive,  
							new Encoder(8,9, true), new Encoder(10,11, false)
							);
	    
		//Shooter Talons
		CANTalon shooter_talon_front_tilted = new CANTalon(HW.SHOOTER_MOTOR_FRONT_TILT);
		CANTalon shooter_talon_front_flat = new CANTalon(HW.SHOOTER_MOTOR_FRONT_FLAT);
		CANTalon shooter_talon_middle_left = new CANTalon(HW.SHOOTER_MOTOR_MIDDLE_LEFT);
		CANTalon shooter_talon_middle_right = new CANTalon(HW.SHOOTER_MOTOR_MIDDLE_RIGHT);
		CANTalon shooter_talon_rear_left = new CANTalon(HW.SHOOTER_MOTOR_REAR_LEFT);
		CANTalon shooter_talon_rear_right = new CANTalon(HW.SHOOTER_MOTOR_REAR_RIGHT);
		
		shooter_talon_front_tilted.changeControlMode(CANTalon.TalonControlMode.Speed);
		shooter_talon_front_tilted.reverseSensor(false);
		
		shooter_talon_front_flat.changeControlMode(CANTalon.TalonControlMode.Speed);
		shooter_talon_front_flat.reverseSensor(false);
		
		shooter_talon_middle_left.changeControlMode(CANTalon.TalonControlMode.Speed);
		shooter_talon_middle_left.reverseSensor(false);
		
		shooter_talon_rear_left.changeControlMode(CANTalon.TalonControlMode.Speed);
		shooter_talon_rear_left.reverseSensor(false);
		
		//ShooterWheels
		shooterFrontTilt = new SpectrumSpeedControllerCAN(
				shooter_talon_front_tilted, 
				HW.SHOOTER_MOTOR_FRONT_TILT_PDP);
	    
		shooterWheelFrontTilt = new ShooterWheel("Front Tilt",
				shooterFrontTilt);
		
		
		shooterFrontFlat = new SpectrumSpeedControllerCAN(
				shooter_talon_front_flat, 
				HW.SHOOTER_MOTOR_FRONT_FLAT_PDP);
	
		shooterWheelFrontFlat = new ShooterWheel("Front Flat",
				shooterFrontFlat);
		
		
		shooterMiddle = new SpectrumSpeedControllerCAN(
				new CANTalon[] {shooter_talon_middle_left, shooter_talon_middle_right},
				new int[] {HW.SHOOTER_MOTOR_MIDDLE_LEFT_PDP, HW.SHOOTER_MOTOR_MIDDLE_RIGHT_PDP}
				);
		
		shooterMiddle.setInverted(true);
		
		shooterWheelMiddle = new ShooterWheel("Middle",
				shooterMiddle);
		
		shooter_talon_middle_right.changeControlMode(CANTalon.TalonControlMode.Follower);
		shooter_talon_middle_right.set(shooter_talon_middle_left.getDeviceID());
		//shooter_talon_middle_right.ConfigFwdLimitSwitchNormallyOpen(true);
		//shooter_talon_middle_right.ConfigRevLimitSwitchNormallyOpen(true);
		shooter_talon_middle_right.enableReverseSoftLimit(false);
		shooter_talon_middle_right.enableForwardSoftLimit(false);
		
		shooterRear = new SpectrumSpeedControllerCAN(
				new CANTalon[] {shooter_talon_rear_left, shooter_talon_rear_right},
				new int[] {HW.SHOOTER_MOTOR_REAR_LEFT_PDP, HW.SHOOTER_MOTOR_REAR_RIGHT_PDP}
				);
	    
		
		shooterWheelRear = new ShooterWheel("Rear",
				shooterRear);
		
		shooter_talon_rear_right.changeControlMode(CANTalon.TalonControlMode.Follower);
		shooter_talon_rear_right.set(shooter_talon_rear_left.getDeviceID());
		//shooter_talon_rear_right.ConfigFwdLimitSwitchNormallyOpen(true);
		//shooter_talon_rear_right.ConfigRevLimitSwitchNormallyOpen(true);
		shooter_talon_rear_right.enableReverseSoftLimit(false);
		shooter_talon_rear_right.enableForwardSoftLimit(false);
	
    
		CANTalon kickerOne_talon = new CANTalon(HW.KICKER_MOTOR_ONE);
		CANTalon kickerTwo_talon = new CANTalon(HW.KICKER_MOTOR_TWO);
		
		kickerOne_talon.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
		kickerTwo_talon.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
		
		SpectrumSpeedControllerCAN kickerController = new SpectrumSpeedControllerCAN(
				new CANTalon[] {kickerOne_talon, kickerTwo_talon},
				new int[] {HW.KICKER_MOTOR_ONE_PDP, HW.KICKER_MOTOR_TWO_PDP}
				);
				
		kicker = new KickerSystem("kicker", kickerController);
		
		//DOUBLE SOLENOID
		//shooterCarriageExtendSolenoid = new SpectrumSolenoid(HW.CARRIAGE_EXTEND);
		//shooterCarriageRetractSolenoid = new SpectrumSolenoid(HW.CARRIAGE_RETRACT);
		//shooterCarriage = new ShooterCarriage("Carriage", shooterCarriageExtendSolenoid, shooterCarriageRetractSolenoid);
		
		//SINGLE
		shooterCarriageRetractSolenoid = new SpectrumSolenoid(HW.CARRIAGE_SINGLE);
		shooterCarriage = new ShooterCarriage("Carriage", shooterCarriageRetractSolenoid);
		
		photonCannonSolenoid = new SpectrumSolenoid(HW.PHOTON_CANNON_SOLENOID);
		photonCannon = new PhotonCannon("Photon Cannon", photonCannonSolenoid);
    }
    //Used to keep track of the robot current state easily
    public enum RobotState {
        DISABLED, AUTONOMOUS, TELEOP
    }

    public static RobotState s_robot_state = RobotState.DISABLED;

    public static RobotState getState() {
        return s_robot_state;
    }

    public static void setState(RobotState state) {
        s_robot_state = state;
    }

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	initDebugger();
    	printGeneralInfo("Start robotInit()");
    	setupSubsystems(); //This has to be before the OI is created on the next line
		HW.oi = new OI();
        Dashboard.intializeDashboard();
    }
    
    private static void initDebugger(){
    	Debugger.setLevel(Debugger.info3); //Set the initial Debugger Level
    	Debugger.flagOn(general); //Set all the flags on, comment out ones you want off
    	Debugger.flagOn(controls);
    	Debugger.flagOn(input);
    	Debugger.flagOn(output);
    	Debugger.flagOn(auton);
    	Debugger.flagOn(commands);
    }
    /**
     * Initialization code for test mode should go here.
     *
     * Users should override this method for initialization code which will be called each time
     * the robot enters test mode.
     */
    public void testInit() {
    	compressor.startLiveWindowMode();
    	compressor.setClosedLoopControl(false);
    }
    
    /**
     * This function is called when the disabled button is hit.
     * You can use it to reset subsystems before shutting down.
     */
    public void disabledInit(){
        setState(RobotState.DISABLED);
        printGeneralInfo("Start disabledInit()");
        Disabled.init();
        printGeneralInfo("End disableInit()");
    }
    /**
     * This function is called while in disabled mode.
     */    
    public void disabledPeriodic(){
    	Disabled.periodic();
    }


    public void autonomousInit() {
    	setState(RobotState.AUTONOMOUS);
    	printGeneralInfo("Start autonomousInit()");
        Autonomous.init();
        printGeneralInfo("End autonomousInit()");
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Autonomous.periodic();
    }

    public void teleopInit() {
    	setState(RobotState.TELEOP);
    	printGeneralInfo("Start teleopInit()");
        Teleop.init();
        printGeneralInfo("End teleopInit()");
        
       
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Teleop.periodic();
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
    
    public static void printGeneralInfo(String msg){
    	Debugger.println(msg, general, Debugger.info3);
    }
}