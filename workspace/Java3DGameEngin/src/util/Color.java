/**
 * 
 */
package util;

import org.lwjgl.util.vector.Vector4f;

/**
 * @author shadi
 *
 */
public final class Color {

	public static final Color RED = new Color(1, 0, 0);
	public static final Color GREEN = new Color(0, 1, 0);
	public static final Color BLUE = new Color(0, 0, 1);
	public static final Color WHITE = new Color(1, 1, 1);
	public static final Color BLACK = new Color(0, 0, 0);

	private final float red;
	private final float green;
	private final float blue;
	private final float alpha;

	public Color(float r, float g, float b, float a) {

		validateValue(r, 0f, 1.0f);
		validateValue(g, 0f, 1.0f);
		validateValue(b, 0f, 1.0f);
		validateValue(a, 0f, 1.0f);

		this.red = r;
		this.green = g;
		this.blue = b;
		this.alpha = a;
	}

	public Color(float r, float g, float b) {
		this(r, g, b, 1.0f);
	}

	public Vector4f toVector4f() {
		return new Vector4f(red, green, blue, alpha);
	}

	public static Color from255(int r, int g, int b, int a) {
		validateValue(r, 0, 255);
		validateValue(g, 0, 255);
		validateValue(b, 0, 255);
		validateValue(a, 0, 255);

		return new Color(r / 255f, g / 255f, b / 255f, a / 255f);
	}

	public static Color from255(int r, int g, int b) {
		validateValue(r, 0, 255);
		validateValue(g, 0, 255);
		validateValue(b, 0, 255);

		return new Color(r / 255f, g / 255f, b / 255f);
	}

	private static void validateValue(float value, float min, float max) {
		if (value < min || value > max)
			throw new IllegalArgumentException(
					"Color value must be between " + min + " and " + max + "(Received " + value + ")");
	}

}
