package org.usfirst.frc.team5026.util.motionprofile;

public abstract class Follower {
	
	double p;
	double i;
	double d;
	double f;
	
	public Follower(double[] pidf) {
		p = pidf[0];
		i = pidf[1];
		d = pidf[2];
		f = pidf[3];
	}
	
	public MotionPathPoint getPoint(MotionPath path, KinematicModel robot, int LOOK_AHEAD) {
		int t = path.getClosestIndex(robot);
		MotionPathPoint p;
		if (t + LOOK_AHEAD < path.points.length) {
			p = path.points[t + LOOK_AHEAD];
		} else {
			p = path.points[path.points.length-1];
		}
		return p;
	}
	public abstract double getOut(MotionPath path, KinematicModel robot); // This needs to return a velocity in RPM or rot/100ms
}
