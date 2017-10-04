package org.usfirst.frc.team5026.util.motionprofile;

public class GyroFollower extends Follower {
	public static final int LOOK_AHEAD = 5; // Looks 5 points ahead
	public static final double DELTA_TIME = 0.02;
	
	static double p;
	static double i;
	static double d;
	static double f;
	
	static double gyroError = 0;
	static double totalGyroError = 0;
	static double lastGyroError = Double.NaN;
	
	public static void startFollow(double[] pidf) {
		p = pidf[0];
		i = pidf[1];
		d = pidf[2];
		f = pidf[3];

		gyroError = 0;
		totalGyroError = 0;
		lastGyroError = Double.NaN;
	}
	public static double getOut(MotionPath path, KinematicModel robot) {
		MotionPathPoint target = Follower.getPoint(path, robot, LOOK_AHEAD);
		
		// Need to look at the target's theta and use that as target
		double targetAngle = target.theta;
		double actualAngle = path.getCurrentAsPoint(robot).theta;
		
		// No need for velhat here, just do simple pid.
		gyroError = actualAngle - targetAngle;
		if (Double.isNaN(lastGyroError)) {
			lastGyroError = gyroError;
		}
		totalGyroError += gyroError;
		double pidOut = p * gyroError + i * totalGyroError + (gyroError - lastGyroError) / DELTA_TIME;
		return pidOut;
	}
}
