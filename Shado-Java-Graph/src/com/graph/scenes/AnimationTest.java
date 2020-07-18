/**
 *
 */

package com.graph.scenes;

import com.engin.Scene;

import java.awt.*;

public class AnimationTest extends Scene {

	private volatile int x;
	private volatile int y;
	private int w;
	private int h;

	public AnimationTest() {
		super("Animation test");

		x = 300;
		y = 200;
		w = 500;
		h = 100;
	}

	@Override
	public void init() {

	}

	@Override
	public void update(long dt) {
	}

	@Override
	public void draw(Graphics g2) {

		Graphics2D g = (Graphics2D) g2;

		// Draw car
		g.setColor(Color.PINK);
		g.fillRect(x, y, w, h);
		g.setColor(Color.BLACK);
		g.drawRect(x, y, w, h);

		// Draw upper car
		g.setColor(Color.PINK);
		g.fillRect(x + w / 4, y - h + 50, w / 2, h - 50);
		g.setColor(Color.BLACK);
		g.drawRect(x + w / 4, y - h + 50, w / 2, h - 50);

		// Draw wheels

		var d = 75;
		var weel1X = x + w / 4 - d / 2;
		var weel1Y = y + h - d / 2;
		g.setColor(Color.GRAY);
		g.fillOval(weel1X, weel1Y, d, d);
		g.setColor(Color.BLACK);
		g.drawOval(weel1X, weel1Y, d, d);

		g.setColor(Color.GRAY);
		g.fillOval(weel1X + w / 2, weel1Y, d, d);
		g.setColor(Color.BLACK);
		g.drawOval(weel1X + w / 2, weel1Y, d, d);

		g.drawLine(weel1X, weel1Y + d / 2, weel1X + d / 2, weel1Y + d / 2);
		g.drawLine(weel1X + w / 2, weel1Y + d / 2, weel1X + d / 2 + w / 2, weel1Y + d / 2);
	}

}
