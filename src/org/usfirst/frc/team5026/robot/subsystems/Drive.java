package org.usfirst.frc.team5026.robot.subsystems;

import org.usfirst.frc.team5026.robot.Robot;
import org.usfirst.frc.team5026.robot.commands.drive.DriveWithJoystick;
import org.usfirst.frc.team5026.util.Constants;
import org.usfirst.frc.team5026.util.DriveMotorGroup;
import org.usfirst.frc.team5026.util.GearPosition;
import org.usfirst.frc.team5026.util.Hardware;
import org.usfirst.frc.team5026.util.LEDDisplay;
import org.usfirst.frc.team5026.util.PantherJoystick;
import org.usfirst.frc.team5026.util.motionprofile.KinematicModel;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drive extends Subsystem implements KinematicModel {
	private RobotDrive drive;
	
	private PantherJoystick joystick;
	private DoubleSolenoid shifter;
	private LEDDisplay led;
	private GearPosition pos = GearPosition.LOW;
	public Gyro gyro;
	Hardware hardware;
	
	public DriveMotorGroup left;
	public DriveMotorGroup right;
	
	public double targetAngle;
	private boolean turningRight = true;
	
	public double startingLeftEncoderPos;
	public double startingRightEncoderPos;
	public double targetLeftEncoderPos;
	public double targetRightEncoderPos;
	
	public double x = 0; // REAL WORLD X
	public double y = 0; // REAL WORLD Y
	
	private boolean backwards;
	
	public Drive() {
		joystick = Robot.oi.driveJoystick;
		hardware = Robot.hardware;
		drive = new RobotDrive(hardware.leftMotor, hardware.rightMotor);
		gyro = hardware.gyro;
		shifter = Robot.hardware.shifter;
		drive.setSafetyEnabled(false);
		left = hardware.leftMotor;
		right = hardware.rightMotor;
		led = hardware.led;
	}
	public void drive(double left, double right) {
		drive.drive(left, right);
	}
	public void setLeftRightMotors(double left, double right) {
		drive.setLeftRightMotorOutputs(left, right);
	}
	
	public void setBrakeMode(boolean brake)
	{
		left.setBrakeMode(brake);
		right.setBrakeMode(brake);
	}
	
	public void setGear() {
		if (pos== GearPosition.LOW) {
			setGear(GearPosition.HIGH);
		} else {
			setGear(GearPosition.LOW);
		}
	}
	
	public void setGear(GearPosition p) {
		switch (p) {
		case LOW:
			shifter.set(Value.kReverse);
//			led.writeRegister(Constants.LED_SHIFT_INDEX, Constants.LED_SHIFT_HIGH);
			break;
		case HIGH:
			shifter.set(Value.kForward);
//			led.writeRegister(Constants.LED_SHIFT_INDEX, Constants.LED_SHIFT_LOW);
			break;
		}
		pos = p;
	}
	
	public void useArcadeDrive(double yAxis, double xAxis) {
		drive.arcadeDrive(yAxis, xAxis);
	}
	
	public void stopMotors() {
		this.endPositionDrive();
		this.setLeftRightMotors(0, 0);
	}
	
	//one spark controller is backwards, testing if values need to be opposite
	public void rotateRobot(double speed) {
		if (turningRight) {
    		this.setLeftRightMotors(-speed, speed); 
    	} else {
    		this.setLeftRightMotors(speed, -speed);
    	}
	}
	
	public void setRotate(double angle) {
		targetAngle = angle;
		stopMotors();
		try {
			gyro.reset();
		} catch (NullPointerException e) {
			System.out.println("No Gyro!");
		}
		if(targetAngle > 0) {
			turningRight = true;
		} else {
			turningRight = false;
		}
//		gyro.calibrate();
	}
	
	public void startDriveDistance(double inches) {
		backwards = false;
		if(inches < 0) {
			backwards = true; 
		}
		startingLeftEncoderPos = left.getEncPosition(); //"leftMotor" cringe
		targetLeftEncoderPos = (startingLeftEncoderPos + (Constants.GEAR_RATIO * (inches / Constants.WHEEL_CIRCUMFERENCE) * Constants.ENCODER_TICKS_PER_ROTATION));
		
		startingRightEncoderPos = right.getEncPosition();
		targetRightEncoderPos = (startingRightEncoderPos + (Constants.GEAR_RATIO * (inches / Constants.WHEEL_CIRCUMFERENCE) * Constants.ENCODER_TICKS_PER_ROTATION));
	}
	public double getLeftEnc() {
		return left.getEncPosition();
	}
	public double getRightEnc() {
		return right.getEncPosition();
	}
	
	public double getDistanceError() {
		// In inches
		// Make sure to use the correct ratio
		return ((left.getEncPosition() - targetLeftEncoderPos) * Constants.WHEEL_CIRCUMFERENCE) / (Constants.GEAR_RATIO * Constants.ENCODER_TICKS_PER_ROTATION);
	}
	public double getGyroError() {
		// In degrees
		return gyro.getAngle() - targetAngle;
	}
	public double getGyro()
	{
		return gyro.getAngle();
	}
	public void driveStraightWithTicks(double speed) {
		/*while(encMotor.get() <= 57344){
			
		}*/
	}
	
	public void driveStraight(double speed) {
		//try using different motors, or just add a getEncPosition method in motorgroup
		this.left.set(backwards ? -1: 1 * speed);
	}
	
	public boolean isFinishedDrivingDistance(DriveMotorGroup encMotor) {	//i'm sure there's a better way to do this
		if(backwards) {
			return Math.abs(encMotor.getEncPosition() - startingLeftEncoderPos) < targetLeftEncoderPos; 
		}
		return Math.abs(encMotor.getEncPosition() - startingRightEncoderPos) > targetLeftEncoderPos;
	} 
	
	public void autoDriveDistance() {
		if(!isFinishedDrivingDistance(left)) {
			this.left.set((backwards ? -1 : 1) * Constants.STRAIGHT_DRIVE_SPEED);
		} else {
			this.left.stopMotor();
		}
		if(!isFinishedDrivingDistance(right)) {
			this.right.set((backwards ? -1 : 1) * Constants.STRAIGHT_DRIVE_SPEED);
		} else {
			this.right.stopMotor();
		}
	}

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new DriveWithJoystick(joystick));
	}
	
	public void positionDrive(double targetLeft, double targetRight)
	{
		left.positionControl(targetLeft);
		right.positionControl(targetRight);
	}
	
	public void endPositionDrive()
	{
		left.stopPositionControl();
		right.stopPositionControl();
	}
	public void profileDrive(double targetLeft, double targetRight) {
		left.profileControl(targetLeft);
		right.profileControl(targetRight);
	}
	public void setupProfileMode() {
		left.setupProfileMode();
		right.setupProfileMode();
	}
	public void profileDriveInches(double targetLeft, double targetRight) {
		left.profileControl((targetLeft * Constants.ENCODER_TICKS_PER_INCH) / (Constants.ENCODER_TICKS_PER_ROTATION * 4.0)); //4x because quadature enc
		right.profileControl((targetRight * Constants.ENCODER_TICKS_PER_INCH) / (Constants.ENCODER_TICKS_PER_ROTATION * 4.0));
	}
	public void updatePosition(double deltaTime) {
		// Runs every DELTA_TIME
		if (left.getEncPosition() == 0 && right.getEncPosition() == 0) {
			// Reset x and y position on reset
			x = 0;
			y = 0;
		}
		x += getVelocity() * Math.sin(getRotationInRadians()) * deltaTime;
		y += getVelocity() * Math.cos(getRotationInRadians()) * deltaTime;
		SmartDashboard.putNumber("X Value of Robot", x);
		SmartDashboard.putNumber("Y Value of Robot", y);
	}
	@Override
	public double getWidth() {
		return Constants.ROBOT_WIDTH;
	}
	@Override
	public double[] getCenter() {
		// Needs an accumulator function 
		double[] out = new double[2];
		out[0] = x;
		out[1] = y;
		return out;
	}
	@Override
	public double getRotationInRadians() {
		if (hardware.gyro != null) 
			return hardware.gyro.getAngle() / 180.0 * Math.PI;
		return 0;
	}
	@Override
	public double getVelocity() {
		// Convert velocity to inches/sec or whatever value is in Constants.
		double avg = (left.getEncMotor().getSpeed() + right.getEncMotor().getSpeed()) / 2;
		// TODO CONVERSION
		avg *= Constants.INCHES_PER_ENCODER_REV * 10; // 10 is for conversion from 100ms to 1s
		return avg;
	}
}