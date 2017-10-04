package org.usfirst.frc.team5026.util;

import org.usfirst.frc.team5026.robot.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.interfaces.Gyro;

public class Hardware {
	public CANTalon leftMotor_1;
	public CANTalon leftMotor_2; 
	public CANTalon leftMotor_3;
	public CANTalon rightMotor_1;
	public CANTalon rightMotor_2;
	public CANTalon rightMotor_3;
	public DriveMotorGroup leftMotor;
	public DriveMotorGroup rightMotor;
	private boolean invertMotorLeft = true;
	private boolean invertSensorLeft = true;
	private boolean invertMotorRight = false;
	private boolean invertSensorRight = false;
	
	public Talon climberRightMotor;
	public Talon climberLeftMotor;
	
	public boolean climberLeftInverted = true;
	public boolean climberRightInverted = true;
	
	public Talon intake;
	
	public PowerDistributionPanel pdp = new PowerDistributionPanel();
	
	public DoubleSolenoid shifter;
	
	public Gyro gyro;
	
	public DoubleSolenoid gearClampPiston;
	public DigitalInput gearClampSensor;
	
	public DigitalInput driveLeftBanner;
	public DigitalInput driveRightBanner;
	
	public Talon groundGearIntake;
	public CANTalon groundGearLift;
	public DriveMotorGroup groundGearLiftgroup; // This is because there is no PIDMotorGroup, it is just DriveMotorGroup for now
	public DigitalInput groundGearBanner;
	
	public LEDDisplay led;

	public Hardware() {
		// Drive Motors
		leftMotor_1 = new CANTalon(RobotMap.DRIVE_MOTOR_LEFT_ENCODER);
		leftMotor_2 = new CANTalon(RobotMap.DRIVE_MOTOR_LEFT_2);
		leftMotor_3 = new CANTalon(RobotMap.DRIVE_MOTOR_LEFT_3);
		rightMotor_1 = new CANTalon(RobotMap.DRIVE_MOTOR_RIGHT_ENCODER);
		rightMotor_2 = new CANTalon(RobotMap.DRIVE_MOTOR_RIGHT_2);
		rightMotor_3 = new CANTalon(RobotMap.DRIVE_MOTOR_RIGHT_3);
		leftMotor = new DriveMotorGroup(invertMotorLeft, invertSensorLeft, Constants.PIDFR_LEFT, Constants.TELEOP_RAMP_LEFT, leftMotor_1, leftMotor_2, leftMotor_3);
		rightMotor = new DriveMotorGroup(invertMotorRight, invertSensorRight, Constants.PIDFR_RIGHT, Constants.TELEOP_RAMP_RIGHT, rightMotor_1, rightMotor_2, rightMotor_3);
		
		// Drive Sensors
		buildGyro(0);
		
		driveLeftBanner = new DigitalInput(RobotMap.DRIVE_LEFT_BANNER);
		driveRightBanner = new DigitalInput(RobotMap.DRIVE_RIGHT_BANNER);
		
		// Shifter
		shifter = new DoubleSolenoid(1,RobotMap.SOLENOID_SHIFTER_FORWARD,RobotMap.SOLENOID_SHIFTER_REVERSE);
		
		// Climber
		climberRightMotor = new Talon(RobotMap.CLIMBER_MOTOR_RIGHT);
		climberLeftMotor = new Talon(RobotMap.CLIMBER_MOTOR_LEFT);
		
		// Gear
		gearClampPiston = new DoubleSolenoid(1, RobotMap.GEAR_PISTON_FORWARD, RobotMap.GEAR_PISTON_REVERSE);
		gearClampSensor = new DigitalInput(RobotMap.GEAR_CLAMP_SENSOR);
		
		// Intake
		//intake = new Talon(RobotMap.INTAKE_MOTOR);
		
		// LEDs
		//led = new LEDDisplay(RobotMap.CAN_LED_PORT);
		
		// Ground Gear
		groundGearIntake = new Talon(RobotMap.GROUND_GEAR_MOTOR_INTAKE);
		groundGearLift = new CANTalon(RobotMap.GROUND_GEAR_MOTOR_LIFT);
		groundGearLiftgroup = new DriveMotorGroup(Constants.GROUND_GEAR_MOTOR_INVERTED, Constants.GROUND_GEAR_SENSOR_INVERTED, Constants.GROUND_GEAR_PIDFRNAV, 0, groundGearLift);
		groundGearBanner = new DigitalInput(RobotMap.GROUND_GEAR_SENSOR);
	}
	public void buildGyro(int tries) {
		System.out.println("RECONSTRUCTING TRY: "+tries);
		if (tries == 10) return;
		try {
			gyro = new ADXRS450_Gyro(Port.kOnboardCS0);
			gyro.calibrate();
			return;
		} catch (Exception e) {
			// Didn't work!
			buildGyro(tries+1);
		}
	}
}