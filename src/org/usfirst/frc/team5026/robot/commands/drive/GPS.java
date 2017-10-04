package org.usfirst.frc.team5026.robot.commands.drive;

import org.usfirst.frc.team5026.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GPS extends Command {

    public GPS() {
//        requires(Robot.drive); // Actually, MAKE SURE NOT TO REQUIRE DRIVE (interrupts will break)
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double time = 0.001; // Time this loop takes in seconds
    	Robot.drive.updatePosition(time); // Update the position based off of gyro and left/right pos
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false; // Run forever
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
