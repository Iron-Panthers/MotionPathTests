package org.usfirst.frc.team5026.util;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;
import com.ctre.CANTalon.TrajectoryPoint;

import edu.wpi.first.wpilibj.SpeedController;

public class DriveMotorGroup implements SpeedController {

	private CANTalon[] motors;
	private CANTalon encoderMotor;
	
	private double teleopRampRate;
	double[] pidfrnav;
	
	public DriveMotorGroup(boolean talonInverted, boolean encoderInverted, double[] pidfrnav, double teleopRampRate, CANTalon... side) {
		/* First motor is encoder motor
		 * pidfrnav:
		 * P, I, D, F is PIDF for the CANTalon
		 * R is the ramp rate for the CANTalon
		 */
		this.teleopRampRate = teleopRampRate;
		motors = side;
		encoderMotor = motors[0];
		encoderMotor.setEncPosition(0);
        
        /* choose the sensor and sensor direction */
        encoderMotor.setFeedbackDevice(FeedbackDevice.QuadEncoder);
        encoderMotor.configEncoderCodesPerRev(Constants.ENCODER_TICKS_PER_ROTATION); // if using FeedbackDevice.QuadEncoder

        /* set the peak and nominal outputs, 12V means full */
        encoderMotor.configNominalOutputVoltage(+0f, -0f);
        encoderMotor.configPeakOutputVoltage(+12f, -12f);
        /* set the allowable closed-loop error,
         * Closed-Loop output will be neutral within this range.
         * See Table in Section 17.2.1 for native units per rotation. 
         */
        encoderMotor.setAllowableClosedLoopErr(0);
        this.pidfrnav = pidfrnav;
        encoderMotor.setProfile(0);
        setupPositionMode();
       
        //Talon 4 is reverse output and reverse sensor
        encoderMotor.reverseOutput(talonInverted);
        encoderMotor.reverseSensor(encoderInverted);
        for (int i = 1; i < motors.length; i++) {
        	motors[i].changeControlMode(TalonControlMode.Follower);
        	motors[i].set(encoderMotor.getDeviceID());
        }
	}
	public void setupVoltageMode () {
		encoderMotor.changeControlMode(TalonControlMode.PercentVbus);
		encoderMotor.setF(pidfrnav[3]);
        encoderMotor.setP(pidfrnav[0]);
        encoderMotor.setI(pidfrnav[1]); 
        encoderMotor.setD(pidfrnav[2]);
        configNominalOutputVoltage(0, 0);
        encoderMotor.setMotionMagicAcceleration(0);
        encoderMotor.setMotionMagicCruiseVelocity(0);
        encoderMotor.setVoltageRampRate(teleopRampRate); // VOLTAGE RAMP RATE SET TO 0, WILL SET TO DRIVE RAMP.
        // AUTO RAMP IS VERY CONSERVATIVE
	}
	public void setupPositionMode () {
		encoderMotor.changeControlMode(TalonControlMode.Position);
		encoderMotor.setF(pidfrnav[3]);
	    encoderMotor.setP(pidfrnav[0]);
	    encoderMotor.setI(pidfrnav[1]); 
	    encoderMotor.setD(pidfrnav[2]);   
	    encoderMotor.setVoltageRampRate(pidfrnav[4]);
	}
	public void setupProfileMode () {
		encoderMotor.changeControlMode(TalonControlMode.MotionMagic);
		encoderMotor.setF(pidfrnav[3]);
	    encoderMotor.setP(pidfrnav[0]);
	    encoderMotor.setI(pidfrnav[1]);
	    encoderMotor.setD(pidfrnav[2]);
	    encoderMotor.setVoltageRampRate(pidfrnav[4]);
	    configNominalOutputVoltage(pidfrnav[5], -pidfrnav[5]);
	    encoderMotor.setMotionMagicAcceleration(pidfrnav[6]);
	    encoderMotor.setMotionMagicCruiseVelocity(pidfrnav[7]);
	}
	public void setupMotionProfileMode() {
		encoderMotor.changeControlMode(TalonControlMode.MotionProfile);
	}
	public void positionControl(double target) {
    	encoderMotor.changeControlMode(TalonControlMode.Position);
    	encoderMotor.set(target);
	}
	public void profileControl(double target) {
		encoderMotor.changeControlMode(TalonControlMode.MotionMagic);
		encoderMotor.set(target);
	}
	public void voltageControl(double value) {
		encoderMotor.changeControlMode(TalonControlMode.PercentVbus);
		encoderMotor.set(value);
	}
	public void velocityControl(double target) {
		encoderMotor.changeControlMode(TalonControlMode.Speed);
		encoderMotor.set(target);
	}
	public void motionProfileControl() {
		// The motor is already fed the values!!! no target!
		encoderMotor.enable();
		encoderMotor.set(CANTalon.SetValueMotionProfile.Enable.value);
//		encoderMotor.changeMotionControlFramePeriod(10);
		encoderMotor.processMotionProfileBuffer();
	}
	public void setpidfrnav(double[] pidfrnav) {
		this.pidfrnav = pidfrnav;
	}
	
	@Override
	public void pidWrite(double output) {		
	}

	@Override
	public double get() {
		return 0;
	}

	@Override
	public void set(double speed) {
		encoderMotor.enable();
		encoderMotor.set(speed);
	}

	@Override
	public void setInverted(boolean isInverted) {
		
	}

	@Override
	public boolean getInverted() {
		return false;
	}

	@Override
	public void disable() {
		encoderMotor.disable();
	}

	@Override
	public void stopMotor() {
		disable();
	}
	
	public double getEncPosition() {
		return encoderMotor.getEncPosition();
	}
	
	public void stopPositionControl() {
		encoderMotor.changeControlMode(TalonControlMode.PercentVbus);
		encoderMotor.set(0);
	}
	
	public CANTalon getEncMotor() {
		return encoderMotor;
	}
	public void resetPosition() {
		encoderMotor.setPosition(0);
	}
	public void setBrakeMode(boolean brake)
	{
		for(CANTalon ct : motors)
		{
			ct.enableBrakeMode(brake);
		}
	}
	public void configPeakOutputVoltage(double max, double min)
	{
		encoderMotor.configPeakOutputVoltage(max, min);
	}
	public void configNominalOutputVoltage(double max, double min) {
		encoderMotor.configNominalOutputVoltage(max, min);
	}
	public void speedControl(double d) {
		// Rot / 100ms
		encoderMotor.changeControlMode(TalonControlMode.Speed);
		encoderMotor.set(d);
	}
}