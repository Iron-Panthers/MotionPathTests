package org.usfirst.frc.team5026.util;

public enum Color {
	RED(255, 0, 0),
	GREEN(0, 255, 0),
	BLUE(0, 0, 255),
	YELLOW(255, 255, 0),
	GOLD(255, 215, 0),
	SILVER(172, 172, 172),
	MALACHITE(0, 204, 102), //like teal but better
	PERSIAN_GREEN(0, 153, 153), //blue
	MEDIUM_SPRING_GREEN(51, 255, 153),
	ELECTRIC_INDIGO(127, 0, 255),
	DEEP_MAGENTA(204, 0, 204),
	GREEN_YELLOW(153, 255, 51),
	ISLAMIC_GREEN(0, 153, 0),
	PAKISTAN_GREEN(0, 102, 0),
	
	BLACK(0, 0, 0),
	WHITE(255, 255, 255);
	
	public int red;
	public int green;
	public int blue;
	
	private Color(int r, int g, int b)
	{
		this.red = r;
		this.green = g;
		this.blue = b;
	}
}
