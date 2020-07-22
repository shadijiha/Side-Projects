/**
 *
 */
package com.graph;

import java.awt.*;

public final class Tile {

	public static final byte DIMENSION = 100;

	public final int i;
	public final int j;
	public final int value;
	public boolean reveal;

	public Tile(int i, int j) {
		this.i = i;
		this.j = j;
		this.value = (int) (Math.random() * 10);
	}

	public final void draw(Graphics g) {
		var status = g.getColor();
		var font = g.getFont();

		if (reveal) {
			g.setColor(Color.BLACK);
			g.fillRect(i * DIMENSION, j * DIMENSION, DIMENSION, DIMENSION);

			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", Font.PLAIN, DIMENSION / 3));
			g.drawString(value + "", i * DIMENSION + DIMENSION / 3, j * DIMENSION + DIMENSION / 2);

			g.setColor(status);
			g.setFont(font);

		} else {
			g.fillRect(i * DIMENSION, j * DIMENSION, DIMENSION, DIMENSION);

			g.setColor(Color.BLACK);
			g.drawRect(i * DIMENSION, j * DIMENSION, DIMENSION, DIMENSION);

			g.setColor(status);
		}
	}

	public final void reveal() {
		reveal = true;
	}

	public final boolean isRevealed() {
		return reveal;
	}
}
