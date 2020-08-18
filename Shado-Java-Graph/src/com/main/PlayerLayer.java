/**
 *
 */
package com.main;

import com.engin.*;
import com.engin.shapes.Rectangle;

import java.awt.*;
import java.awt.event.*;

public class PlayerLayer extends Scene {

	private final Rectangle player;

	private float velocity = 0f;
	private float gravity = 0.1f;
	private float lift = -5f;

	private float h;
	private float w;

	private Renderer renderer;

	public PlayerLayer() {
		super("PlayerLayer");
		player = new Rectangle(0, 0, 100, 100);
		player.setFill(Color.PINK);

		this.w = player.getDimension().w;
		this.h = player.getDimension().h;
	}

	/**
	 * Initializes the scene and its variables
	 *
	 * @param renderer The renderer that will handle the scene
	 */
	@Override
	public void init(Renderer renderer) {
		this.renderer = renderer;

		player.moveBy(renderer.getWidth() / 4.0f, 0);
	}

	/**
	 * Updates the state of the scene
	 *
	 * @param dt The time between 2 frames in ms
	 */
	@Override
	public void update(float dt) {
		this.force();
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
			Enviroment.singleton.moveObjects(-10);
		if (e.getKeyCode() == KeyEvent.VK_A)
			Enviroment.singleton.moveObjects(10);
		if (e.getKeyCode() == KeyEvent.VK_W)
			jump();
	}

	private void force() {
		this.velocity += this.gravity;

		player.moveBy(0f, this.velocity);

		if (player.getPosition().y > Enviroment.GROUND_LEVEL - this.h) {
			player.moveTo(player.getPosition().x, Enviroment.GROUND_LEVEL - this.h);
			this.velocity = 0;
		}

		if (player.getPosition().y < 0) {
			player.moveTo(player.getPosition().x, 0);
			this.velocity = 0;
		}
	}

	private void jump() {
//		if (
//				this.energy > this.jumpCost &&
//						this.hp > 0 &&
//						!this.stunned &&
//						!this.rooted
//		) {
		this.velocity += this.lift;
		//this.energy -= this.jumpCost;
		//}
	}
}
