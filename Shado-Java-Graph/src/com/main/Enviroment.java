/**
 *
 */

package com.main;

import com.engin.Renderer;
import com.engin.Scene;
import com.engin.shapes.Rectangle;
import com.engin.shapes.Texture;

import java.awt.*;


public class Enviroment extends Scene {

	private Rectangle background;

	public Enviroment() {
		super("Enviroment", -1);
	}

	/**
	 * Initializes the scene and its variables
	 *
	 * @param renderer The renderer that will handle the scene
	 */
	@Override
	public void init(Renderer renderer) {
		background = new Rectangle(0, 0, renderer.getWidth(), renderer.getHeight());

		var texture = new Texture("hehexd.png");

		background.setTexture(texture);

	}

	/**
	 * Updates the state of the scene
	 *
	 * @param dt The time between 2 frames in ms
	 */
	@Override
	public void update(float dt) {

	}

	/**
	 * Draws the scene content to the screen
	 *
	 * @param g The graphics that will handle the drawing
	 */
	@Override
	public void draw(Graphics g) {
		background.draw(g);
	}
}
