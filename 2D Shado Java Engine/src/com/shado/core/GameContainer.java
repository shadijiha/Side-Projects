/**
 *
 */

package com.shado.core;

public class GameContainer implements Runnable	{

	private Thread thread;
	private Window window;

	private boolean running = false;
	private final double UPDATE_CAP = 1.0 / 60.0;	// Cap at 60 FPS

	private int width = 320, height = 240;
	private float scale = 4.0f;
	private String title = "Shado Engine v1.0\n";

	public GameContainer()	{


	}

	public void start()	{
		window = new Window(this);

		thread = new Thread(this);
		thread.run();
	}

	public void stop()	{

	}

	public void run()	{

		running = true;

		boolean render = false;
		double firstTime = 0.0;
		double lastTime = System.nanoTime() / 1000000000.0;
		double passedTime = 0.0;
		double unprocessedTime = 0.0;

		double frameTime = 0.0;
		int frames = 0;
		int fps = 0;

		while (running)	{

			render = false;

			// Calculate FPS
			firstTime = System.nanoTime() / 1000000000.0;
			passedTime = firstTime - lastTime;
			lastTime = firstTime;

			unprocessedTime += passedTime;
			frameTime += passedTime;

			while (unprocessedTime >= UPDATE_CAP)	{
				unprocessedTime -= UPDATE_CAP;
				render = true;

				// TODO: Update game

				if (frameTime >= 1.0)	{
					frameTime = 0.0;
					fps = frames;
					frames = 0;
					System.out.println(fps);
				}
			}

			if (render)	{
				//TODO: Render game
				window.update();

				frames++;
			} else	{
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		dispose();
	}

	private void dispose()	{

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
}
