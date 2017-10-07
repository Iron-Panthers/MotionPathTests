package org.usfirst.frc.team5026.robot.commands;

import org.usfirst.frc.team5026.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class FieldPositionReset extends Command {

    public FieldPositionReset() {
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.drive.x = 0;
    	Robot.drive.y = 0;
    	Robot.hardware.gyro.calibrate();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
