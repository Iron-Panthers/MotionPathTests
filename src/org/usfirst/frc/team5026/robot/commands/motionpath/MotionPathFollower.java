package org.usfirst.frc.team5026.robot.commands.motionpath;

import org.usfirst.frc.team5026.robot.Robot;
import org.usfirst.frc.team5026.util.Constants;
import org.usfirst.frc.team5026.util.motionprofile.CombinationFollower;
import org.usfirst.frc.team5026.util.motionprofile.MotionPath;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MotionPathFollower extends Command {

	MotionPath path;
	
    public MotionPathFollower(MotionPath p) {
        requires(Robot.drive);
        path = p;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	CombinationFollower.setup(Constants.LONGITUDAL_PIDF, Constants.GYRO_PIDF);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	CombinationFollower.follow(path, Robot.drive);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	// If last point has been executed
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	// stop!
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	// stop anyways!
    }
}
