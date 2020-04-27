/**
 *
 */

package com.shado.shapes;

import com.shado.core.Renderer2D;

public class Rectangle extends Shape {

	public Rectangle(int x, int y, int w, int h) {
		super(x, y, w, h);
	}

	@Override
	public void draw(Renderer2D r) {
		if (!no_stroke)
			r.drawRect((int) position.x, (int) position.y, dimensions.width, dimensions.height, stroke_color);
		if (!no_fill)
			r.fillRect((int) position.x, (int) position.y, dimensions.width, dimensions.height, fill_color, light_block);
	}
}
