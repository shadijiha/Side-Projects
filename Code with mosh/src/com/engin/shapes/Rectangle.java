/**
 *
 */
package com.engin.shapes;

import java.awt.*;

public class Rectangle extends Shape {

	public Rectangle(int x, int y, int w, int h) {
		super(x, y, w, h);
	}

	@Override
	public void draw(Graphics g) {

		if (texture != null) {
			texture.setPosition((int) position.x, (int) position.y);
			texture.draw(g);
			return;
		}

		var __color = g.getColor();

		g.setColor(fill);
		g.fillRect((int) position.x, (int) position.y, dimension.w, dimension.h);

		g.setColor(stroke);
		g.drawRect((int) position.x, (int) position.y, dimension.w, dimension.h);

		g.setColor(__color);
	}
}
