package org.usfirst.frc.team5026.robot.commands.drive;

import org.usfirst.frc.team5026.robot.Robot;
import org.usfirst.frc.team5026.util.PantherJoystick;

import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveWithJoystick extends Command {
	
	private PantherJoystick joystick;
	
	public DriveWithJoystick(PantherJoystick joystick) {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.drive);
		this.joystick = joystick;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		Robot.drive.stopMotors();
		Robot.drive.left.setupVoltageMode();
		Robot.drive.right.setupVoltageMode();
	}

	@Override
	protected void execute() {
		Robot.drive.useArcadeDrive(joystick.getScaledDeadzoneY(), joystick.getScaledDeadzoneX());
		SmartDashboard.putNumber("JoyY", joystick.getScaledDeadzoneY());
		SmartDashboard.putNumber("JoyX", joystick.getScaledDeadzoneX());
		SmartDashboard.putBoolean("Joystick is Forward?", joystick.goingForward);
		SmartDashboard.putBoolean("GEAR?", Robot.hardware.gearClampSensor.get());
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.drive.stopMotors();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		Robot.drive.stopMotors();
	}
}
