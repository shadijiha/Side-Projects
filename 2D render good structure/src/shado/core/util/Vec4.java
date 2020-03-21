/**
 * 4D vector
 */

package shado.core.util;

public class Vec4 {

	public float x;
	public float y;
	public float z;
	public float w;

	public Vec4(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}

	public Vec4(float xyz, float w) {
		this(xyz, xyz, xyz, w);
	}

	public Vec4(Vec3 v, float w) {
		this((float) v.x, (float) v.y, (float) v.z, w);
	}

	public Vec4(final Vec4 other) {
		this(other.x, other.y, other.z, other.w);
	}

	public Vec4(double data, double data1, double data2, double data3) {
		this((float) data, (float) data1, (float) data2, (float) data3);
	}

	public Vec3 to3D() {
		if (w != 0)
			return new Vec3(x / w, y / w, z / w);
		else
			return new Vec3(x, y, z);
	}
}
