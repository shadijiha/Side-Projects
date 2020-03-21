/**
 *
 */

package shado.core.util;

public class Dimension {

	public float width;
	public float height;

	public Dimension(float w, float h) {
		width = w;
		height = h;
	}

	public Dimension(Dimension other) {
		this(other.width, other.height);
	}

	public Dimension() {
		this(0.0f, 0.0f);
	}

	public Vec3 toVector() {
		return new Vec3(width, height, 0.0f);
	}
}
