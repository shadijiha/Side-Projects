/**
 *
 */

package com.shado.core;

import com.shado.events.Input;

public class GameContainer implements Runnable {

	private Thread thread;
	private Window window;
	private Renderer2D renderer;
	private Input input;
	private AbstractGame game;

	private boolean running = false;
	private final double UPDATE_CAP = 1.0 / 60.0;    // Cap at 60 FPS

	private int width, height;
	private float scale;
	private String title;

	public GameContainer(AbstractGame game) {
		this.game = game;
		this.width = game.width;
		this.height = game.height;
		this.scale = game.scale;
		this.title = game.title;
	}

	public void start() {
		window = new Window(this);
		renderer = new Renderer2D(this);
		input = new Input(this);

		thread = new Thread(this);
		thread.run();
	}

	public void stop() {

	}

	public void run() {

		running = true;

		boolean render = false;
		double firstTime = 0.0;
		double lastTime = System.nanoTime() / 1000000000.0;
		double passedTime = 0.0;
		double unprocessedTime = 0.0;

		double frameTime = 0.0;
		int frames = 0;
		int fps = 0;

		while (running) {

			render = false;

			// Calculate FPS
			firstTime = System.nanoTime() / 1000000000.0;
			passedTime = firstTime - lastTime;
			lastTime = firstTime;

			unprocessedTime += passedTime;
			frameTime += passedTime;

			while (unprocessedTime >= UPDATE_CAP) {
				unprocessedTime -= UPDATE_CAP;
				render = true;

				// TODO: Replace the update_cap with accual delta time
				game.update(this, (float) UPDATE_CAP);

				// Update Input (Should be last thing)
				input.update();

				if (frameTime >= 1.0) {
					frameTime = 0.0;
					fps = frames;
					frames = 0;
				}
			}

			if (render) {
				renderer.clear();    // Clear screen

				//TODO: Render game
				game.render(this, renderer);
				renderer.process();    // Render all alpha images

				// This method also renders all The texts to the screen!
				window.update();

				frames++;
			} else {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		dispose();
	}

	private void dispose() {

	}

	// Getters and setters
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Window getWindow() {
		return window;
	}

	public Input getInput() {
		return input;
	}
}
