/**
 *
 */

package com.shado.gfx;

import java.awt.Font;
import java.awt.*;

public class Text {

	public static final Font DEFAULT = new Font("Arial", Font.PLAIN, 16);

	private String text;
	private Font font;
	private Color color;

	private int x;
	private int y;

	public Text(String text, int x, int y, Font font, Color color) {
		this.text = text;
		this.font = font;
		this.x = x;
		this.y = y;
		this.color = color;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
}
