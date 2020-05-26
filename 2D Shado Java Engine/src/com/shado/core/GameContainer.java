/**
 *
 */

package com.shado.core;

import com.shado.events.*;

import java.awt.*;

public class GameContainer implements Runnable {

	private Thread thread;
	private Window window;
	private Renderer2D renderer;
	private Input input;
	private AbstractGame game;

	private boolean running = false;
	private final double UPDATE_CAP;    // Cap at the MAIN monitor refresh rate
	private int lastFPS;

	private int width, height;
	private float scale;
	private String title;

	public GameContainer(AbstractGame game) {
		this.game = game;
		this.width = game.width;
		this.height = game.height;
		this.scale = game.scale;
		this.title = game.title;

		UPDATE_CAP = 1.0 / getMonitorsRefreshRate()[0];
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

		game.init(this);

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

				// TODO: Replace the update_cap with actual delta time
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

				// Render game
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

			this.lastFPS = fps;    // Register the last framerate
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

	public int getFramerate() {
		return lastFPS;
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

	public int[] getMonitorsRefreshRate() {

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] gs = ge.getScreenDevices();

		int[] values = new int[gs.length];

		for (int i = 0; i < gs.length; i++) {
			DisplayMode dm = gs[i].getDisplayMode();

			int refreshRate = dm.getRefreshRate();
			if (refreshRate == DisplayMode.REFRESH_RATE_UNKNOWN) {
				refreshRate = 60;
			}

			values[i] = refreshRate;

			//int bitDepth = dm.getBitDepth();
			//int numColors = (int) Math.pow(2, bitDepth);
		}
		return values;
	}

	/**
	 * @return Returns the input of game manager
	 */
	public Input getInput() {
		return input;
	}

	/**
	 * @return Returns the rendering context
	 */
	public Renderer2D getRenderer() {
		return renderer;
	}
}
