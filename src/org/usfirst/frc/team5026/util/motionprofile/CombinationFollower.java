package org.usfirst.frc.team5026.util.motionprofile;

public class CombinationFollower {
	public static void setup(double[] lPIDF, double[] gPIDF) {
		LongitudalFollower.startFollow(lPIDF);
		GyroFollower.startFollow(gPIDF);
	}
	public static void follow(MotionPath path, KinematicModel model) {
		double gFollow = GyroFollower.getOut(path, model);
		double lFollow = LongitudalFollower.getOut(path, model);
		// Both of the above methods return velocities.
		// Velocity inputted is: KinematicModel.set(left, right)
		
		model.set(lFollow + gFollow, lFollow - gFollow);
	}
	// TODO add a way of using subclassed Followers
}
