/**
 *
 */

package com.shado.shapes;

import com.shado.core.Renderer2D;
import com.shado.math.Dimension;

public class Circle extends Shape {

	private int radius;

	public Circle(int x, int y, int r) {
		super(x, y, r, r);
		radius = r;
	}

	@Override
	public void draw(Renderer2D r) {

		if (!no_stroke)
			r.drawEllipse((int) position.x, (int) position.y, radius, radius, stroke_color);
		if (!no_fill)
			r.fillEllipse((int) position.x, (int) position.y, radius, radius, fill_color, light_block);

	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	/**
	 * Do not use this function for CIRCLE. Use getRadius() instead
	 * @return Returns the dimensions of the shape
	 */
	@Deprecated
	public Dimension<Integer> getDimensions() {
		return dimensions;
	}

	/**
	 * Do not use this function for CIRCLE
	 */
	@Deprecated
	public void setDimensions(Dimension<Integer> dimensions) {
		this.dimensions = new Dimension<>(dimensions);
	}
}
