package org.usfirst.frc.team5026.util;

public enum JoystickType {
	RED(Constants.DEADZONE_X_RED, Constants.DEADZONE_Y_RED, Constants.X_SCALING_RED, Constants.Y_SCALING_RED, Constants.DEADZONE_Y_MAX_RED, Constants.DEADZONE_BOWTIE_SCALING_RED),
	BLUE(Constants.DEADZONE_X_BLUE, Constants.DEADZONE_Y_BLUE, Constants.X_SCALING_BLUE, Constants.Y_SCALING_BLUE, Constants.DEADZONE_Y_MAX_BLUE, Constants.DEADZONE_BOWTIE_SCALING_BLUE),
	SPINNY(Constants.DEADZONE_X_SPINNY, Constants.DEADZONE_Y_SPINNY, Constants.X_SCALING_SPINNY, Constants.Y_SCALING_SPINNY, Constants.DEADZONE_Y_MAX_SPINNY, Constants.DEADZONE_BOWTIE_SCALING_SPINNY);
	
	public float deadzoneX;
	public float deadzoneY;
	public float scalingX;
	public float scalingY;
	public float scalingYMax;
	public float bowtieScaling;
	
	JoystickType(float deadzoneX, float deadzoneY, float scalingX, float scalingY, float scalingYMax, float bowtieScaling) {
		this.deadzoneX = deadzoneX;
		this.deadzoneY = deadzoneY;
		this.scalingX = scalingX;
		this.scalingY = scalingY;	
		this.scalingYMax = scalingYMax;
		this.bowtieScaling = bowtieScaling;
	}
}

//JoystickType.RED;