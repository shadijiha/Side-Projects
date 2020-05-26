/**
 *
 */

package com.shado.gfx;

import com.shado.math.*;

public final class Color {

	public static final int BLACK = 0xff000000;
	public static final int BLUE = 0xff0000ff;
	public static final int CYAN = 0xff00ffff;
	public static final int GREEN = 0xff00ff00;
	public static final int MAGENTA = 0xffff00ff;
	public static final int RED = 0xffff0000;
	public static final int WHITE = 0xffffffff;
	public static final int YELLOW = 0xffffff00;

	// Keep constructor private to avoid trash initialization
	private Color() {
	}

	private Color(final Color other) {
	}

	/**
	 * Generates a random color
	 * @return Returns a random color with an alpha of 255
	 */
	public static int random() {
		return fromRGB(Util.random(0, 255), Util.random(0, 255), Util.random(0, 255));
	}

	/**
	 * @param r Red value
	 * @param g Green value
	 * @param b Blue value
	 * @param a Alpha value
	 * @return Returns an integer representing the desired color
	 */
	public static int fromRGBA(int r, int g, int b, int a) {

		if (r < 0 || r > 255 || g < 0 || g > 255 || b < 0 || b > 255 || a < 0 || a > 255)
			throw new IllegalArgumentException("Color value must be between 0 and 255");

		String hex = String.format("%02x%02x%02x%02x", a, r, g, b);
		return (int) Long.parseLong(hex, 16);
	}

	/**
	 * @param r Red value
	 * @param g Green value
	 * @param b Blue value
	 * @return Returns an integer representing the desired color
	 */
	public static int fromRGB(int r, int g, int b) {
		return fromRGBA(r, g, b, 255);
	}
}
