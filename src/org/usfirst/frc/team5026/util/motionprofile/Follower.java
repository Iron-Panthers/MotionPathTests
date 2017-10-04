package org.usfirst.frc.team5026.util.motionprofile;

public abstract class Follower {
	public static int LOOK_AHEAD;
	public static void follow(MotionPath path, KinematicModel robot) {
		int t = path.getClosestIndex(robot);
		MotionPathPoint p = path.points[];
	}
}
