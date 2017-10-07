package org.usfirst.frc.team5026.util.motionprofile;

public class LongitudalFollower extends Follower {
	public  final int LOOK_AHEAD = 0; // Looks at current point
	public  final double DELTA_TIME = 0.02;
	
	double posError = 0;
	double totalPosError = 0;
	double lastPosError = Double.NaN;
	
	public LongitudalFollower(double[] pidf) {
		super(pidf);
		startFollow();
	}
	
	public void startFollow() {
		posError = 0;
		totalPosError = 0;
		lastPosError = Double.NaN;
	}
	public double getOut(MotionPath path, KinematicModel robot) {
		MotionPathPoint target = super.getPoint(path, robot, LOOK_AHEAD);
		
		// Need to decode the (x,y) coordinates into encoder values (avg)
		double targetDistAlongPath = path.decode(target);
		double targetVelAtPoint = target.v; // potential conversion needed
		double actualDistAlongPath = path.decode(path.getCurrentAsPoint(robot));
		double actualVel = path.getCurrentAsPoint(robot).v;
		
		// Should probably follow the lines of: target-actual = error (or backwards), and should it simply be: targetVel * f?
		// If so, the algorithm will look as follows:
		double velHat = f * targetVelAtPoint;
		posError = actualDistAlongPath - targetDistAlongPath; // Conversion, again, might be wanted here
		if (Double.isNaN(lastPosError)) {
			lastPosError = posError;
		}
		totalPosError += posError;
		double pidOut = p * posError + i * totalPosError + d * (posError - lastPosError) / DELTA_TIME;
		lastPosError = posError;
		// pidOut should be in rot/100ms
		
		double overallOut = pidOut + velHat;
		return overallOut;
	}
}
