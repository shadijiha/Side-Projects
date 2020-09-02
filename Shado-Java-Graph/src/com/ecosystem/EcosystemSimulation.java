package com.ecosystem;

import com.engin.Renderer;
import com.engin.Scene;

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

		test = new Rabbit(renderer);
	}

	/**
	 * Updates the state of the scene
	 *
	 * @param dt The time between 2 frames in ms
	 */
	@Override
	public void update(float dt) {
		test.update();
	}

	/**
	 * Draws the scene content to the screen
	 *
	 * @param g The graphics that will handle the drawing
	 */
	@Override
	public void draw(Graphics g) {
		test.draw(g);
		test.headTo(1, 1);
	}
}
