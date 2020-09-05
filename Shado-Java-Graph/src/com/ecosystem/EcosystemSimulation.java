package com.ecosystem;

import com.engin.Renderer;
import com.engin.Scene;
import com.engin.Util;
import com.engin.logger.Debug;

import java.awt.*;

public class EcosystemSimulation extends Scene {

	private Renderer renderer;
	private Rabbit test;

	public EcosystemSimulation() {
		super("Ecosystem Simulation");
	}

	/**
	 * Initializes the scene and its variables
	 *
	 * @param renderer The renderer that will handle the scene
	 */
	@Override
	public void init(Renderer renderer) {

		this.renderer = renderer;

		for (int i = 0; i < 5; i++) {
			new Rabbit(renderer);
		}

		test = new Rabbit(renderer);

		// Log stuff
		final StringBuilder block = new StringBuilder();
		Util.setInterval(() -> {
			block.append("\n======================\n");
			for (Rabbit r : Rabbit.getAllRabbits()) {
				block.append(r).append("\n");
			}

			Debug.push(block.toString());
			Debug.flush();

		}, 2000);
	}

	/**
	 * Updates the state of the scene
	 *
	 * @param dt The time between 2 frames in ms
	 */
	@Override
	public void update(float dt) {
		for (Rabbit rabbit : Rabbit.getAllRabbits())
			rabbit.update(dt);
	}

	/**
	 * Draws the scene content to the screen
	 *
	 * @param g The graphics that will handle the drawing
	 */
	@Override
	public void draw(Graphics g) {
		for (Rabbit rabbit : Rabbit.getAllRabbits())
			rabbit.draw(g);
	}
}
