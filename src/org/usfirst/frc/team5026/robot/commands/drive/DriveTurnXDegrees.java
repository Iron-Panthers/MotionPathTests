package org.usfirst.frc.team5026.robot.commands.drive;

import org.usfirst.frc.team5026.robot.Robot;
import org.usfirst.frc.team5026.util.Constants;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveTurnXDegrees extends Command {

	private double degrees;
	private int count;
	double p;
	double tol;
	boolean reset;

	public DriveTurnXDegrees() {
		requires(Robot.drive);
	}
	
    public DriveTurnXDegrees(double degrees, boolean reset) {
    	this.degrees = degrees;
    	this.reset = reset;
    }


    protected void initialize() {
    	p = SmartDashboard.getNumber("Auto Rotation P", 0);
    	tol = SmartDashboard.getNumber("Auto Angle Rotation Tolerance", 0);
    	count = 0;
    	Robot.drive.left.setupVoltageMode();
    	Robot.drive.right.setupVoltageMode();
    	if (reset)
    	Robot.hardware.gyro.reset();
    }

    
    protected void execute() {
    	double angle = Robot.hardware.gyro.getAngle();
    	Robot.drive.setLeftRightMotors(Constants.AUTO_TURN_SPEED * -(degrees - angle) * p, Constants.AUTO_TURN_SPEED * (degrees -  angle) * p);
    	if (Math.abs(Robot.hardware.gyro.getAngle() - degrees) <= tol) {
    		count++;
    	}
    	SmartDashboard.putNumber("Gyro angle", Robot.hardware.gyro.getAngle());
    }

    
    protected boolean isFinished() {
//    	return false;
        return count >= Constants.AUTO_TURN_COUNT;
    }


    protected void end() {
    	Robot.drive.stopMotors();
    }


    protected void interrupted() {
    	Robot.drive.stopMotors();
    }
}
