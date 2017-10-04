package org.usfirst.frc.team5026.util.motionprofile;

public class MotionPath {
	public MotionPathPoint[] points;
	public double arcLength;
	public MotionPath(MotionPathPoint... ps) {
		points = ps;
	}
	public MotionPathPoint getClosestPoint(KinematicModel model) {
		return points[getClosestIndex(model)];
	}
	public int getClosestIndex(KinematicModel model) {
		MotionPathPoint current = getCurrentAsPoint(model);
		double minDistance = MotionPathPoint.distance(points[0], current);
		int out = 0;
		for (int i = 1; i < points.length; i++) {
			// Already did 0th case
			double dist = MotionPathPoint.distance(points[i], current);
			if (dist < minDistance) {
				out = i;
				minDistance = dist;
			}
		}
		return out;
	}
	public MotionPathPoint getCurrentAsPoint(KinematicModel model) {
		return new MotionPathPoint(model.getCenter(),model.getVelocity(),model.getRotationInRadians());
	}
	public int find(MotionPathPoint search) {
		for (int i = 0; i < points.length; i++) {
			if (search == points[i]) {
				return i;
			}
		}
		return -1;
	}
	public double decode(MotionPathPoint target) {
		// Returns the distance that has been travelled along the path at this point.
		int index = find(target);
		if (index != -1) {
			// distance along path = arclength * index / numSamples
			return getArcLength() * (double) index / (double) points.length;
		} else {
			// The point wasn't on the path!
			return 0;
		}
	}
	public double getArcLength() {
		if (arcLength != 0) {
			return arcLength;
		} else {
			calculateArcLength();
			return arcLength;
		}
	}
	private void calculateArcLength() {
		double total = 0;
		for (int i = 1; i < points.length; i++) {
			total += MotionPathPoint.distance(points[i-1], points[i]); // Distance along the straight line- that's how points are connected here
		}
		arcLength = total;
	}
}
