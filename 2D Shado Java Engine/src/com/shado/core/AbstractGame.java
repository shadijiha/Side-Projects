/**
 *
 */

package com.shado.core;

public abstract class AbstractGame {

	int width;
	int height;
	float scale;
	String title;

	/**
	 * @param window_title The title of the Game window
	 * @param width The desired window width
	 * @param height The desired window height
	 * @param scale The desired window scale
	 */
	protected AbstractGame(String window_title, int width, int height, float scale) {
		this.title = window_title;
		this.width = width;
		this.height = height;
		this.scale = scale;
	}

	protected AbstractGame() {
		title = "Shado Engine v1.0";
		width = 320;
		height = 240;
		scale = 4.0f;
	}

	/**
	 * This code is execute whenever possible
	 * @param gc The GameContainer
	 * @param dt The time between frames in SECONDS
	 */
	public abstract void update(GameContainer gc, float dt);

	/**
	 * This code is only executed at the capped Framerate
	 * @param gc The GameContainer
	 * @param r The Renderer
	 */
	public abstract void render(GameContainer gc, Renderer2D r);
}
