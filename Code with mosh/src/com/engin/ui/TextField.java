/**
 *
 */

package com.engin.ui;

import java.awt.*;
import java.awt.event.*;

public class TextField extends UIComponent implements MouseListener {

	public TextField(float x, float y, float w, float h) {
		super(x, y, w, h);
	}

	public TextField() {
		super();
	}

	/**
	 * Renders the UI element to the screen
	 *
	 * @param g The graphics context
	 */
	@Override
	public void render(Graphics g) {

		// Draw background
		g.setColor(backgroundColor);
		g.fillRect((int) position.x, (int) position.y, dimension.w, dimension.h);

		// Draw border
		g.setColor(borderColor);
		g.drawRect((int) position.x, (int) position.y, dimension.w, dimension.h);


	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		System.out.println(x + "," + y);//these co-ords are relative to the component
	}

	/**
	 * Invoked when a mouse button has been pressed on a component.
	 *
	 * @param e the event to be processed
	 */
	@Override
	public void mousePressed(MouseEvent e) {

	}

	/**
	 * Invoked when a mouse button has been released on a component.
	 *
	 * @param e the event to be processed
	 */
	@Override
	public void mouseReleased(MouseEvent e) {

	}

	/**
	 * Invoked when the mouse enters a component.
	 *
	 * @param e the event to be processed
	 */
	@Override
	public void mouseEntered(MouseEvent e) {

	}

	/**
	 * Invoked when the mouse exits a component.
	 *
	 * @param e the event to be processed
	 */
	@Override
	public void mouseExited(MouseEvent e) {

	}
}
