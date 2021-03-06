package org.usfirst.frc.team5026.util.motionprofile;

import org.usfirst.frc.team5026.util.Constants;

public class GenerateTrajectory {
	enum PathStage {
		ACCEL,
		SLEW1,
		TURN,
		SLEW2,
		DECEL,
		STOP
	}
	public PathStage phase = PathStage.ACCEL;
	public double s;
	public double last_s;
	public double v;
	public double theta;
	public double omega;
	
	public double X, Y = 0;
	public Path p = new Path();
	
	private double V_MAX, A_MAX, OMEGA_MAX, S_START_TURN, THETA_FINAL, S_FINAL;
	
	public static final double dt = Constants.DELTA_TIME;
	public static final double WIDTH = Constants.ROBOT_WIDTH;
	public static final double RADIUS_OF_WHEEL = Constants.WHEEL_DIAMETER / 2;
	private static final int ENCODER_TICKS_PER_REVOLUTION = Constants.ENCODER_TICKS_PER_ROTATION;
	private static final double ENCODER_TICKS_PER_INCH = Constants.ENCODER_TICKS_PER_INCH;
	
	public void mutateTrajectory(double V_MAX, double A_MAX, double OMEGA_MAX, double S_START_TURN, double THETA_FINAL, double S_FINAL) {
		switch(phase) {
	    case ACCEL: 
	        v = v + A_MAX * dt; 
	        s = s + v * dt;
	        omega = 0;
	        theta = 0;
	        if (v >= V_MAX) phase = PathStage.SLEW1; // reached the slew velocity
	        break;
	        
	    case SLEW1:
	        v = V_MAX;
	        s = s + v * dt;
	        omega = 0;
	        theta = 0;
	        if (s >= S_START_TURN) phase = PathStage.TURN; // reached the start of turn
	        break;
	        
	    case TURN:
	        v = V_MAX;
	        s = s + v * dt;
	        omega = OMEGA_MAX;
	        theta = theta + omega * dt;
	        if (theta >= THETA_FINAL) phase = PathStage.SLEW2; // completed the turn
	        break;
	        
	    case SLEW2:
	        v = V_MAX;
	        s = s + v * dt;
	        omega = 0;
	        theta = THETA_FINAL;
	        if (s >= S_FINAL - (V_MAX * V_MAX) / (2 * A_MAX)) phase = PathStage.DECEL; // start decel to land on X_FINAL  
	        break;
	        
	    case DECEL:
	        v = v - A_MAX * dt;  // With computational error, v could go negative
	                             // before reaching X_FINAL, so you probably want to
	                             // impose a minimum velocity (>0). 
	        s = s + v * dt;
	        omega = 0;
	        theta = THETA_FINAL;
	        if (s >= S_FINAL) phase = PathStage.STOP;
	        break;
	    }
	}
	public GenerateTrajectory(double v_max, double a_max, double omega_max, double s_start_turn, double theta_target, double s_final) {
		V_MAX = v_max;
		A_MAX = a_max;
		OMEGA_MAX = omega_max;
		S_START_TURN = s_start_turn;
		THETA_FINAL = theta_target;
		S_FINAL = s_final;
	}
	public boolean execute() {
		if (phase == PathStage.STOP) {
			Double[] temp = {0.0, 0.0};
			p.points.add(temp);
			return true;
		}
		mutateTrajectory(V_MAX, A_MAX, OMEGA_MAX, S_START_TURN, THETA_FINAL, S_FINAL);
		double ds = s - last_s;
		last_s = s;
		X = X + ds * Math.sin(theta);
		Y = Y + ds * Math.cos(theta);
		double vL = (v + omega * WIDTH / 2) / RADIUS_OF_WHEEL;
		double vR = (v - omega * WIDTH / 2) / RADIUS_OF_WHEEL;
		double rot100msL = vL / 10 / (2 * Math.PI * RADIUS_OF_WHEEL); // also need to include encoder rev per wheel rev, or inches per encoder rev
		double rot100msR = vR / 10 / (2 * Math.PI * RADIUS_OF_WHEEL);
		
		double realRotsL = vL * ENCODER_TICKS_PER_INCH / (ENCODER_TICKS_PER_REVOLUTION) * 60; // IT IS IN RPM
		double realRotsR = vR * ENCODER_TICKS_PER_INCH / (ENCODER_TICKS_PER_REVOLUTION) * 60; // still wrong, for some reason
		
		// Need to test for velocity rot100ms to in/s
		
		// TODO HERE!
		System.out.println("(X, Y, Theta; VL, VR): ("+X+", "+Y+", "+theta+"; "+vL+", "+vR+") (L: "+realRotsL+", R: "+realRotsR+")");
		Double[] temp = {realRotsL, realRotsR};
		p.points.add(temp);
		return false;
	}
	public Path getPath() {
		return p;
	}
}
