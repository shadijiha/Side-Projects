/**
 *
 */
package com.engin.components;

import com.engin.GameObject;
import com.engin.math.Vector2f;

public final class Transform extends EntityComponent {

	public Vector2f position;
	public Vector2f scale;
	public float rotation;

	public Transform(GameObject obj, int x, int y, int w, int h, float angle) {
		super(obj);
		position = new Vector2f(x, y);
		scale = new Vector2f(w, h);
		rotation = angle;
	}

	public Transform(GameObject obj) {
		this(obj, 0, 0, 1, 1, 0.0f);
	}

	/**
	 * Returns a string representation of the object. In general, the
	 * {@code toString} method returns a string that "textually represents" this
	 * object. The result should be a concise but informative representation that is
	 * easy for a person to read. It is recommended that all subclasses override
	 * this method.
	 * <p>
	 * The {@code toString} method for class {@code Object} returns a string
	 * consisting of the name of the class of which the object is an instance, the
	 * at-sign character `{@code @}', and the unsigned hexadecimal representation of
	 * the hash code of the object. In other words, this method returns a string
	 * equal to the value of: <blockquote>
	 *
	 * <pre>
	 * getClass().getName() + '@' + Integer.toHexString(hashCode())
	 * </pre>
	 *
	 * </blockquote>
	 *
	 * @return a string representation of the object.
	 */
	@Override
	public String toString() {
		return "Transform: " + position.toString() + ", scale " + scale.toString() + ", rotation " + rotation;
	}
}
