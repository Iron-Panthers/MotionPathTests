package org.usfirst.frc.team5026.robot.commands.drive;

import org.usfirst.frc.team5026.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveShiftGear extends Command {

    public DriveShiftGear() {
        requires(Robot.drive);
    }

    protected void initialize() {
    	Robot.drive.setGear();
    	//Robot.hardware.led.cycleStates();
    }

    protected boolean isFinished() {
        return true;
    }
    
}
