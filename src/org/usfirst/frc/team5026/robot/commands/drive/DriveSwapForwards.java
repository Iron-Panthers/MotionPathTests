package org.usfirst.frc.team5026.robot.commands.drive;

import org.usfirst.frc.team5026.robot.Robot;
import org.usfirst.frc.team5026.util.Constants;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveSwapForwards extends Command {

    protected void initialize() {
    	Robot.oi.driveJoystick.goingForward = false;
//    	Robot.hardware.led.writeRegister(Constants.LED_DRIVE_INDEX, Constants.LED_TIME_DEFAULT, Constants.LED_DRIVE_REVERSE);
//    	Robot.hardware.led.cycleStates();
    }

    protected boolean isFinished() {
        return false;
    }
    protected void end() {
    	Robot.oi.driveJoystick.goingForward = true;
//    	Robot.hardware.led.writeRegister(Constants.LED_DRIVE_INDEX,Constants.LED_TIME_DEFAULT,Constants.LED_DRIVE_FORWARD);
//    	Robot.hardware.led.cycleStates();
    }
}
