package com.main;

import com.engin.*;

import java.awt.*;
import java.awt.event.*;

public class Main {

	public static void main(String[] args) {
		// write your code here


	}
}

class DebugScene extends Scene {

	private Color color = Color.WHITE;
	private int green;

	public DebugScene() {
		super("Debug Scene");
	}

	@Override
	public void init(Renderer renderer) {

	}

	@Override
	public void update(float dt) {
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(color);
		g.fillRect(10, 10, 500, 500);

		g.setColor(Color.BLACK);
		g.drawRect(10, 10, 500, 500);
	}

	/**
	 * Invoked when the mouse cursor has been moved onto a component
	 * but no buttons have been pushed.
	 *
	 * @param e the event to be processed
	 */
	@Override
	public void mouseMoved(MouseEvent e) {
		color = new Color(e.getX() % 255, Math.abs(green % 255), e.getY() % 255);
	}

	/**
	 * Invoked when the mouse wheel is rotated.
	 *
	 * @param e the event to be processed
	 * @see MouseWheelEvent
	 */
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		green += e.getWheelRotation() * e.getScrollAmount();
		color = new Color(e.getX() % 255, Math.abs(green % 255), e.getY() % 255);
	}
}
