package org.usfirst.frc.team5026.util.motionprofile;

public class LongitudalFollower {
	public static final int LOOK_AHEAD = 0; // Looks at current point
	public static void follow(MotionPath path, KinematicModel robot) {
		int t = path.getClosestIndex(robot);
		MotionPathPoint target = path.points[t + LOOK_AHEAD];
		
		// Need to decode the (x,y) coordinates into encoder values (avg)
		double distAlongPath = path.decode(target);
		double velAtPoint = target.v; // potential conversion needed
		
	}
}
