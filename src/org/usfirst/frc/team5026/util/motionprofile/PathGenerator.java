package org.usfirst.frc.team5026.util.motionprofile;
/*
public class PathGenerator {
	enum PathPhase {
			ACCEL, SLEW1, TURN, SLEW2, DECEL, STOP
	};
	PathPhase phase = PathPhase.ACCEL;
	public PathGenerator() {
		
	}
	public void start() {
		switch(phase) {
	    case ACCEL: 
	        v = v + A_MAX * dt; 
	        x = x + v * dt;
	        omega = 0;
	        theta = 0;
	        if (v >= V_MAX) phase = SLEW1; // reached the slew velocity
	        break;
	        
	    case SLEW1:
	        v = V_MAX;
	        x = x + v * dt;
	        omega = 0;
	        theta = 0;
	        if (x >= X_START_TURN) phase = TURN; // reached the start of turn
	        break;
	        
	    case TURN:
	        v = V_MAX;
	        x = x + v * dt;
	        omega = OMEGA_MAX;
	        theta = theta + omega * dt;
	        if (theta >= THETA_FINAL) phase = SLEW2; // completed the turn
	        break;
	        
	    case SLEW2:
	        v = V_MAX;
	        x = x + v * dt;
	        omega = 0;
	        theta = THETA_FINAL;
	        if (x >= X_FINAL - (V_MAX * V_MAX) / (2 * A_MAX)) phase = DECEL; // start decel to land on X_FINAL  
	        break;
	        
	    case DECEL:
	        v = v - A_MAX * dt;  // With computational error, v could go negative
	                             // before reaching X_FINAL, so you probably want to
	                             // impose a minimum velocity (>0). 
	        x = x + v * dt;
	        omega = 0;
	        theta = THETA_FINAL;
	        if (x >= X_FINAL) phase = STOP;
	        break;
		case STOP:
			break;
		}
		
	}
}
*/