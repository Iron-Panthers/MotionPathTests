package org.usfirst.frc.team5026.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	// CLIMBER MOTORS
	public static final int CLIMBER_MOTOR_RIGHT = 1;
	public static final int CLIMBER_MOTOR_LEFT = 2;
	public static final int CLIMBER_PDPMOTOR_RIGHT = 14;
	public static final int CLIMBER_PDPMOTOR_LEFT = 15;
	
	// INTAKE MOTORS
	//public static final int INTAKE_MOTOR = 0;
	
	// DRIVE MOTORS
	public static final int DRIVE_MOTOR_LEFT_ENCODER = 4;
	public static final int DRIVE_MOTOR_LEFT_2 = 5;
	public static final int DRIVE_MOTOR_LEFT_3 = 6;
	public static final int DRIVE_MOTOR_RIGHT_ENCODER = 1;
	public static final int DRIVE_MOTOR_RIGHT_2 = 2;
	public static final int DRIVE_MOTOR_RIGHT_3 = 3;
	
	// DRIVE SENSORS
	public static final int DRIVE_LEFT_BANNER = 1;
	public static final int DRIVE_RIGHT_BANNER = 2;
	
	//GROUND GEAR
	public static final int GROUND_GEAR_MOTOR_INTAKE = 0; // Using old Intake Talon 
	public static final int GROUND_GEAR_MOTOR_LIFT = 7;
	public static final int GROUND_GEAR_SENSOR = 4;
	
	// SOLENOIDS
	public static final int SOLENOID_SHIFTER_FORWARD = 2;
	public static final int SOLENOID_SHIFTER_REVERSE = 3;
	
	// JOYSTICK BUTTONS
	public static final int DRIVE_JOYSTICK = 0;
	public static final int BUTTON_JOYSTICK = 1;
	
	public static final int BOARD_BUTTON_1 = 1;
	public static final int BOARD_BUTTON_2 = 2;
	public static final int BOARD_BUTTON_3 = 3;
	public static final int BOARD_BUTTON_4 = 4;
	public static final int BOARD_BUTTON_5 = 5;
	public static final int BOARD_BUTTON_6 = 6;
	public static final int BOARD_BUTTON_7 = 7;
	public static final int BOARD_BUTTON_8 = 8;
	public static final int BOARD_BUTTON_9 = 9;
	public static final int BOARD_BUTTON_10 = 10;
	public static final int BOARD_BUTTON_11 = 11;
	
	public static final int DRIVE_BUTTON_1 = 1;
	public static final int DRIVE_BUTTON_2 = 2;
	public static final int DRIVE_BUTTON_3 = 3;
	public static final int DRIVE_BUTTON_4 = 4;
	public static final int DRIVE_BUTTON_5 = 5;
	
	// GEAR
	public static final int GEAR_PISTON_FORWARD = 0;
	public static final int GEAR_PISTON_REVERSE = 1;
	public static final int GEAR_CLAMP_SENSOR = 3;
	
	// LED
	public static final int CAN_LED_PORT = 3;
}
	

