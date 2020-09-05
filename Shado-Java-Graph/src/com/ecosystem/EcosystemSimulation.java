package com.ecosystem;

import com.engin.*;
import com.engin.logger.*;

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

		renderer.getWindow().setTitle(1 / dt + " FPS");

		for (Rabbit rabbit : Rabbit.getAllRabbits())
			rabbit.update(dt);

//		if (Rabbit.maleCollector.size() + Rabbit.femaleCollector.size() >= 500) {
//			Rabbit.maleCollector.exportToCSV();
//			Rabbit.femaleCollector.exportToCSV();
//			System.exit(0);
//		}
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
