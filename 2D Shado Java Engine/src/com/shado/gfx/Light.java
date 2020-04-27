/**
 *
 */

package com.shado.gfx;

public class Light {

	public static final int OUT_OF_BOUNDS = -1;
	public static final int NO_BLOCK = 0;
	public static final int FULL_BLOCK = 1;

	private int radius;
	private int diameter;
	private int color;
	private int[] light_map;

	public Light(int radius, int color) {
		this.radius = radius;
		this.color = color;
		this.diameter = radius * 2;
		this.light_map = new int[diameter * diameter];

		this.init();
	}

	/**
	 * Initializes the Light
	 */
	private void init() {
		for (int y = 0; y < diameter; y++) {
			for (int x = 0; x < diameter; x++) {
				double distance = Math.sqrt((x - radius) * (x - radius) + (y - radius) * (y - radius));

				if (distance < radius) {
					double power = 1 - (distance / radius);

					light_map[x + y * diameter] = (int) (((color >> 16) & 0xff) * power) << 16 | (int) (((color >> 8) & 0xff) * power) << 8 | (int) ((color & 0xff) * power);
				} else {
					light_map[x + y * diameter] = 0;
				}
			}
		}
	}

	/**
	 *
	 * @param x The x position
	 * @param y The y position
	 * @return Returns the light value at that position
	 */
	public int getLightValue(int x, int y) {
		if (x < 0 || x >= diameter || y < 0 || y >= diameter)
			return OUT_OF_BOUNDS;
		return light_map[x + y * diameter];
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
		this.diameter = radius * 2;
		init();
	}

	public int getDiameter() {
		return diameter;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
		init();
	}

	public int[] getLight_map() {
		return light_map;
	}

	public void setLight_map(int[] light_map) {
		this.light_map = light_map;
	}
}
