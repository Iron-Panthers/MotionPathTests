package org.usfirst.frc.team5026.util.motionprofile;

public class MotionPathPoint {
	public double x;
	public double y;
	public double v;
	public double theta;
	public MotionPathPoint(double x, double y, double v, double t) {
		this.x = x;
		this.y = y;
		this.v = v;
		theta = t;
	}
	public MotionPathPoint(double[] pos, double v, double t) {
		x = pos[0];
		y = pos[1];
		this.v = v;
		theta = t;
	}
	public static double distance(MotionPathPoint p1, MotionPathPoint p2) {
		return Math.sqrt(Math.pow(p2.y - p1.y, 2) + Math.pow(p2.x - p1.x, 2));
	}
}
