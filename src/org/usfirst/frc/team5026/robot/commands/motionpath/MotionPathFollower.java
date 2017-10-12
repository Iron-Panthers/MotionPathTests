package org.usfirst.frc.team5026.robot.commands.motionpath;

import java.util.ArrayList;

import org.usfirst.frc.team5026.robot.Robot;
import org.usfirst.frc.team5026.util.Constants;
import org.usfirst.frc.team5026.util.GearPosition;
import org.usfirst.frc.team5026.util.motionprofile.Path;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class MotionPathFollower extends Command {
	
	double time = 0;
	int index = 0;
	ArrayList<Double[]> points;

    public MotionPathFollower(Path p) {
    	requires(Robot.drive);
    	points = p.points;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	time = 0;
    	index = 0;
    	Robot.drive.setGear(GearPosition.HIGH);
    	Robot.drive.stopMotors();
    	Robot.drive.left.resetPosition();
    	Robot.drive.right.resetPosition();
    	SmartDashboard.putNumber("Total path duration", points.size() * Constants.DELTA_TIME);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.drive.speedControl(points.get(index));
    	time += Constants.DELTA_TIME;
    	SmartDashboard.putNumber("Current Time on Path", time);
    	SmartDashboard.putNumber("Target Vel Left:", points.get(index)[0]);
    	SmartDashboard.putNumber("Target Vel Right:", points.get(index)[1]);
    	index++;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return index + 1 >= points.size();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.drive.stopMotors();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.drive.stopMotors();
    }
}
