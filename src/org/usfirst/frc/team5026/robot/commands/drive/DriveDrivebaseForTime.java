package org.usfirst.frc.team5026.robot.commands.drive;

import org.usfirst.frc.team5026.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class DriveDrivebaseForTime extends Command {

	private double left, right;
	
	public DriveDrivebaseForTime(double left, double right, double time) {
		requires(Robot.drive);
		this.left = left;
		this.right = right;
	}
	
	// Called just before this Command runs the first time
    protected void initialize() {
    	Robot.drive.setLeftRightMotors(left, right);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.drive.setLeftRightMotors(left, right);
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.drive.stopMotors();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}
	

}
