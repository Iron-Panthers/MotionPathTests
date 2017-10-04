package org.usfirst.frc.team5026.util.motionprofile;
// Provides an interface for a KinematicMode of a robot. Forces the robot to provide implementation of getCenter() and getRotation()
public interface KinematicModel {
	public double getWidth(); // Method for getting the width of the robot, width is distance from wheel side to wheel side
	public double[] getCenter(); // Method for the center of the robot
	public double getRotationInRadians(); // Method for the rotation of the robot
	public double getRotation(); // Method for rotation in degrees instead
	public double getRotationalVelocity(); // Method for rotational speed in deg/s
	public double getRotationalVelocityInRadians(); // Method for rotational speed in rad/s
	public double getVelocity(); // Method for the velocity of the robot
	//TODO remove unnescessary radians and degrees. Just have one
	public void set(double left, double right); // Sets the motor speeds to a given velocity (left and right)
}
