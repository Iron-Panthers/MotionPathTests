package org.usfirst.frc.team5026.util.motionprofile;

import java.util.ArrayList;

public class CombinationFollower {
	static ArrayList<Follower> followers = new ArrayList<Follower>();
	public static void setup(double[] lPIDF, double[] gPIDF) {
		followers.add(new LongitudalFollower(lPIDF));
		followers.add(new GyroFollower(gPIDF));
//		LongitudalFollower.startFollow(lPIDF);
//		GyroFollower.startFollow(gPIDF);
	}
	public static void follow(MotionPath path, KinematicModel model) {
//		double gFollow = GyroFollower.getOut(path, model);
//		double lFollow = LongitudalFollower.getOut(path, model);
		double lFollow = followers.get(0).getOut(path, model);
		double gFollow = followers.get(1).getOut(path, model);
		// Both of the above methods return velocities.
		// Velocity inputted is: KinematicModel.set(left, right)
		// These should be velocities (in rot/100ms)
		model.set(lFollow - gFollow, lFollow + gFollow);
	}
	public static boolean isFinished(MotionPath path, KinematicModel model) {
		return path.getClosestPoint(model) == path.points[path.points.length-1];
	}
	// TODO add a way of using subclassed Followers
}
