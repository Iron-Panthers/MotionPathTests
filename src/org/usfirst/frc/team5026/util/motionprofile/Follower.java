package org.usfirst.frc.team5026.util.motionprofile;

public abstract class Follower {
	public static MotionPathPoint getPoint(MotionPath path, KinematicModel robot, int LOOK_AHEAD) {
		int t = path.getClosestIndex(robot);
		MotionPathPoint p = path.points[path.getClosestIndex(robot) + LOOK_AHEAD];
		return p;
	}
	// TODO Make Longitudal and Gyro followers NON static, make them subclass Follower.
}
