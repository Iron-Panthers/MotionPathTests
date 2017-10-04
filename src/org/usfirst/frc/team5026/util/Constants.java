package org.usfirst.frc.team5026.util;

public class Constants { 
	// Joystick Constants 
	//RED 
	public static final float X_SCALING_RED = 0.4f;  //.4
	public static final float Y_SCALING_RED = 0.8f; //.85
	public static final float DEADZONE_X_RED = 0.0563f; 
	public static final float DEADZONE_Y_RED = 0.0512f; 
	public static final float DEADZONE_Y_MAX_RED = 0.829f; 
	public static final float DEADZONE_BOWTIE_SCALING_RED = 0.03f; 

	//BLUE 
	public static final float X_SCALING_BLUE = X_SCALING_RED; 
	public static final float Y_SCALING_BLUE = Y_SCALING_RED;  
	public static final float DEADZONE_X_BLUE = DEADZONE_X_RED; 
	public static final float DEADZONE_Y_BLUE = DEADZONE_Y_RED; 
	public static final float DEADZONE_Y_MAX_BLUE = DEADZONE_Y_MAX_RED; 
	public static final float DEADZONE_BOWTIE_SCALING_BLUE = DEADZONE_BOWTIE_SCALING_RED; 

	//SPINNY 
	public static final float X_SCALING_SPINNY = 0.7f; 
	public static final float Y_SCALING_SPINNY = 0.85f;  
	public static final float DEADZONE_X_SPINNY = 0.0563f; 
	public static final float DEADZONE_Y_SPINNY = 0.0512f; 
	public static final float DEADZONE_Y_MAX_SPINNY = 0.829f;
	public static final float DEADZONE_BOWTIE_SCALING_SPINNY = 0.03f; 

	//Gyro Constants 
	public static final double PERCENTAGE = 0.05;  
	public static final double SPEED = 0.5; 
	   
	//Climber Constants
	public static final double CLIMBER_SPEED_RAPPEL = -0.8;	//quality of unloader's life
	public static final double CLIMBER_SPEED_LATCH = 0.3;
	public static final double CLIMBER_SPEED_WRAP = 0.81; //0.71
	public static final double CLIMBER_SLOPE_SWAP = 0.0;	//The value in which the joystick switches from linear to quadratic
	public static final double CLIMBER_SLOPE_WRAP = 0.0152;	//caps motors at 83.33% V (10/12 V), previously set at 0.0841 (capped at 100% V)
	public static final double CLIMBER_SLOPE_LATCH = 0.04;	//allows for slower climbing speeds
	
	public static final double CLIMBER_STALL_LIMIT = 5.0;	//=[NEED TESTING]= The current(A) at which the climber motors stall.
	
	// GROUND GEAR
	public static final int GROUND_GEAR_LEGAL_TICKS = 0;
	public static final int GROUND_GEAR_SCORING_TICKS = -76600;
	public static final int GROUND_GEAR_DRIVEMODE_TICKS = -130000;
	public static final int GROUND_GEAR_LOWERED_TICKS = -140000;
	
	public static final int GROUND_GEAR_STABILISATION_MAX_COUNT = 10;
	public static final int GROUND_GEAR_TICK_TOLERANCE = 120;
	public static final double GROUND_GEAR_OUTPUT_TOLERANCE = 0.05;
	
	public static final double GROUND_GEAR_INTAKE_SPEED = -0.4;
	public static final double GROUND_GEAR_OUTTAKE_SPEED = 0.3;
	public static final boolean GROUND_GEAR_MOTOR_INVERTED = false;
	public static final boolean GROUND_GEAR_SENSOR_INVERTED = false;
	public static final int GROUND_GEAR_PID_PROFILE = 0;
	public static final double GROUND_GEAR_P = 0.65;
	public static final double GROUND_GEAR_I = 0;
	public static final double GROUND_GEAR_D = 0;
	public static final double GROUND_GEAR_F = 0.01152806; // Calculated by doing 100% of full speed is equivalent to 100% PWM
	public static final double GROUND_GEAR_R = 0;
	public static final double GROUND_GEAR_N = 0;
	public static final double GROUND_GEAR_A = 1000; // acceleration
	public static final double GROUND_GEAR_V = 2000; // velocity
	public static final double[] GROUND_GEAR_PIDFRNAV = {GROUND_GEAR_P, GROUND_GEAR_I, GROUND_GEAR_D, GROUND_GEAR_F, GROUND_GEAR_R, GROUND_GEAR_N, GROUND_GEAR_A, GROUND_GEAR_V};
	
	public static final double GROUND_GEAR_N_SCORING = 0;
	public static final double GROUND_GEAR_A_SCORING = 500; // Rotations / min * sec; acceleration
	public static final double GROUND_GEAR_V_SCORING = 1000; // Rotations / min; velocity
	public static final double[] GROUND_GEAR_SCORING_PIDFRNAV = {GROUND_GEAR_P, GROUND_GEAR_I, GROUND_GEAR_D, GROUND_GEAR_F, GROUND_GEAR_R, GROUND_GEAR_N_SCORING, GROUND_GEAR_A_SCORING, GROUND_GEAR_V_SCORING};
	
	//Intake Constants
	public static final double INTAKE_INTAKE_SPEED = 1;
	public static final double INTAKE_OUTTAKE_SPEED = -1;
	
	//LED Constants
	public static final double LED_TIME_DEFAULT = 0.25;
	public static final int LED_SHIFT_INDEX = 0;
	public static final int LED_DRIVE_INDEX = 1;
	public static final Color LED_SHIFT_HIGH = Color.RED;
	public static final Color LED_SHIFT_LOW = Color.BLUE;
	public static final Color LED_DRIVE_FORWARD = Color.DEEP_MAGENTA;
	public static final Color LED_DRIVE_REVERSE = Color.GOLD;
	public static final Color LED_GEAR_RELEASE = Color.GREEN;
	
	// Gyro Constants
	public static final double AUTO_TURN_ANGLE_TOLERANCE = 1;
	public static final double AUTO_TURN_SPEED = 0.5;
	public static final int AUTO_TURN_COUNT = 5;
	public static final double AUTO_TURN_P = 0.1;
	
	// Encoder constants
	public static final double WHEEL_DIAMETER = 4;	//in inches
	public static final double WHEEL_CIRCUMFERENCE = Math.PI * WHEEL_DIAMETER;
	public static final int ENCODER_TICKS_PER_ROTATION = 1024;
	
	// Auto Constants
	public static final double STRAIGHT_DRIVE_SPEED = 0.5;
	public static final double GEAR_RATIO = 5.36;
	public static final double INCHES_PER_ENCODER_REV = 9; //+- 2.5% WRONG
	public static final double ENCODER_TICKS_PER_INCH = 417.75; //+- 2.7%
	
	/*
	 * For auto distances:
	 * Line left laser (gear is front) with the far edge of the tape for both the loading zone and the boiler
	 * The angle for the banner sensor is the same.
	 * Line right laser with bottom right of airship
	 * 
	 * After doing this, and being sure the angles are correct, optimize auto modes to work from those positions.
	 */
	// Auto Constants
	public static int AUTO_BANNER_BUFFER = 20;
	
	// Auto Distances
	public static double AUTO_MIDDLE_TARGET_LEFT = -87; // Move 5 1/8 to the left when setting up, laser moves by 9 inches. 9.1
	public static double AUTO_MIDDLE_TARGET_RIGHT = -87; // Move 5 /8 to the left when setting up, laser moves by 9 inches. 9.1
	public static int AUTO_MIDDLE_TARGET_COUNT = 25;
	public static int AUTO_DROP_GEAR_LEFT = -400; // Approx travel distance between wall and the far side of neutral zonee
	public static int AUTO_DROP_GEAR_RIGHT = -400;
	
	//DriveCarveToPegFromBoilerRed
	public static double AUTO_BOILER_TARGET_FAR_RED = -72.5; //12.9, 112.7", 86.5"
	public static double AUTO_BOILER_TARGET_CLOSE_RED = -72.5; //9.1, 112.7", 86.5"
	public static double AUTO_BOILER_AFTER_TURN_TO_PEG_RED = -59.6; //4.5, 45.6", 52.6"
	public static int AUTO_BOILER_CARVE_COUNT_RED = 25;
	public static int AUTO_BOILER_STRAIGHT_COUNT_RED = 25;
	public static double AUTO_BOILER_ANGLE_RED = -60; 
	
	//DriveCarveToPegFromLoadingZoneRed
	public static double AUTO_LOADING_TARGET_FAR_RED = -78.5; //12.8
	public static double AUTO_LOADING_TARGET_CLOSE_RED = -78.5; //9.1
	public static double AUTO_LOADING_AFTER_TURN_TO_PEG_RED = -76; //4.5
	public static int AUTO_LOADING_CARVE_COUNT_RED = 25;
	public static int AUTO_LOADING_STRAIGHT_COUNT_RED = 25;
	public static double AUTO_LOADING_ANGLE_RED = 60; 
	/*
	 * Actual start: 
	 * Red loading zone actual straight drive distance (relative to turning point): 80.25"
	 * Red loading zone actual angle (relative to turning point): 60
	 * Red loading zone actual second straight drive distance (relative to turning point): 56.75"
	 * 
	 * Middle actual: 113.8"
	 * 
	 * Dumb start: Gear side forward, left bumper corner on inside of tape
	 * Red loading zone dumb start straight drive distance (relative to turning point): 94.75"
	 * Red loading zone dumb start angle (relative to turning point): 60
	 * Red loading zone dumb start second straight drive distance (relative to turning point): 28.25"
	 */
	
	//DriveCarveToPegFromBoilerBlue
	public static double AUTO_BOILER_TARGET_FAR_BLUE = -76.5; //12.9
	public static double AUTO_BOILER_TARGET_CLOSE_BLUE = -76.5; //9.1
	public static double AUTO_BOILER_AFTER_TURN_TO_PEG_BLUE = -82.6; //4.5
	public static int AUTO_BOILER_CARVE_COUNT_BLUE = 25;
	public static int AUTO_BOILER_STRAIGHT_COUNT_BLUE = 25;
	public static double AUTO_BOILER_ANGLE_BLUE = 60;	
	
	//DriveCarveToPegFromLoadingZoneBlue
	public static double AUTO_LOADING_TARGET_FAR_BLUE = -75; //12.8
	public static double AUTO_LOADING_TARGET_CLOSE_BLUE = -75; //9.1
	public static double AUTO_LOADING_AFTER_TURN_TO_PEG_BLUE = -75; //4.5
	public static int AUTO_LOADING_CARVE_COUNT_BLUE = 25;
	public static int AUTO_LOADING_STRAIGHT_COUNT_BLUE = 25;
	public static double AUTO_LOADING_ANGLE_BLUE = -60;
	
	// Drive motion profile
	public static double DRIVE_STABILIZATION_TOLERANCE = 60;
	
	public static int PID_PROFILE_LEFT = 0;
	// TODO VALUES BELOW ARE FROM PRACTICE BOT, tested target of -10 (4/12)
	// FOR TUNING THESE VALUES, USE THE MotionMagicTuning PROJECT
	public static double P_LEFT = 1.35;
	public static double I_LEFT = 0;
	public static double D_LEFT = 0;
	public static double F_LEFT = 0.6;
	public static double RAMP_LEFT = 0;
	public static double NOMINAL_LEFT = 0;
	public static double ACCEL_LEFT = 250;
	public static double VEL_LEFT = 500;
	public static double[] PIDFR_LEFT = {P_LEFT,I_LEFT,D_LEFT,F_LEFT,RAMP_LEFT,NOMINAL_LEFT,ACCEL_LEFT,VEL_LEFT};
	
	public static int PID_PROFILE_RIGHT = 0;
	// TODO VALUES BELOW ARE FROM PRACTICE BOT, tested target of -10 (4/12)
	// FOR TUNING THESE VALUES, USE THE MotionMagicTuning PROJECT
	public static double P_RIGHT = 1.35;
	public static double I_RIGHT = 0;
	public static double D_RIGHT = 0;
	public static double F_RIGHT = 0.6;
	public static double RAMP_RIGHT = 0;
	public static double NOMINAL_RIGHT = 0;
	public static double ACCEL_RIGHT = 250;
	public static double VEL_RIGHT = 500;
	public static double[] PIDFR_RIGHT = {P_RIGHT,I_RIGHT,D_RIGHT,F_RIGHT,RAMP_RIGHT,NOMINAL_RIGHT,ACCEL_RIGHT,VEL_RIGHT};
	
	public static double TELEOP_RAMP_RIGHT = 0;
	public static double TELEOP_RAMP_LEFT = 0;
	
	public static final double MAX_ACCELERATION = 250;
	/*
	 * Unit is encoder rotations/sec/sec.
	 * measured (unloaded): 4
	 * measured (loaded, on field): 300
	 */
	public static final double MAX_VELOCITY = 1000; 
	/*
	 * Unit is encoder rotations/sec.
	 * measured (unloaded): 1834
	 * measured (loaded, on field): 1200
	 */
	public static final double DELTA_TIME = 0.01; // Seconds
	public static final double MOTION_PROFILE_VELOCITY = 20; // Inches/seconds
	public static final double ROBOT_WIDTH = 30; // Inches
	public static final double MOTION_PROFILE_LOOK_DISTANCE = 10; // Inches
	
}