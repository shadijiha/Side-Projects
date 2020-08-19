/**
 *
 */
package com.main;

import com.engin.Renderer;
import com.engin.Scene;
import com.engin.shapes.Rectangle;

import java.awt.*;
import java.awt.event.KeyEvent;

public class PlayerLayer extends Scene {

	private final Rectangle player;

	public PlayerLayer() {
		super("PlayerLayer");
		player = new Rectangle(0, 0, 100, 100);
		player.setFill(Color.PINK);
	}

	/**
	 * Initializes the scene and its variables
	 *
	 * @param renderer The renderer that will handle the scene
	 */
	@Override
	public void init(Renderer renderer) {

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
		player.draw(g);
	}

	/**
	 * Invoked when a key has been pressed. See the class description for
	 * {@link KeyEvent} for a definition of a key pressed event.
	 *
	 * @param e the event to be processed
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_D)
			player.moveBy(5, 0);
	}
}
