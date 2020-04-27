/**
 *
 */

package com.shado.math;

import java.util.Objects;

public class Dimension<T> {

	public T width;
	public T height;

	public Dimension(T w, T h) {
		width = w;
		height = h;
	}

	public Dimension() {
		width = null;
		height = null;
	}

	public Dimension(final Dimension<T> other) {
		width = other.width;
		height = other.height;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || o.getClass() != this.getClass()) {
			return false;
		} else {
			Dimension<?> temp = (Dimension<?>) o;
			return temp.width == width && temp.height == height;
		}
	}

	@Override
	public String toString() {
		return "Dimension{" +
				"width=" + width +
				", height=" + height +
				'}';
	}

	@Override
	public int hashCode() {
		return Objects.hash(width, height);
	}
}
