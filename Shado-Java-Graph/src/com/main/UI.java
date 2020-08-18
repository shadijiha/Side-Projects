/**
 *
 */

package com.main;

import com.engin.*;

import java.awt.*;

public class UI extends Scene {

	private Renderer renderer;
	private volatile float dt;

	public UI() {
		super("UI", 10);
	}

	/**
	 * Initializes the scene and its variables
	 *
	 * @param renderer The renderer that will handle the scene
	 */
	@Override
	public void init(Renderer renderer) {
		this.renderer = renderer;
	}

	/**
	 * Updates the state of the scene
	 *
	 * @param dt The time between 2 frames in ms
	 */
	@Override
	public void update(float dt) {
		this.dt = dt;
	}

	/**
	 * Draws the scene content to the screen
	 *
	 * @param g The graphics that will handle the drawing
	 */
	@Override
	public void draw(Graphics g) {

		var font = g.getFont();

		g.setFont(new Font("arial", Font.PLAIN, 20));
		g.drawString(1 / dt + "", renderer.getWidth() - 50, 20);


		g.setFont(font);
	}
}
