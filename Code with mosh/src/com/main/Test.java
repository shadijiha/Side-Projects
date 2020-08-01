/**
 *
 */
package com.main;

import com.engin.Renderer;
import com.engin.Scene;

import java.awt.*;
import java.util.function.Supplier;

public class Test extends Scene {

	private double[] percentages;
	private Color colors[];
	private int winner = -1;
	private int windowWidth;

	public Test() {
		super("Test debug scene");
	}

	/**
	 * Initializes the scene and its variables
	 *
	 * @param renderer The renderer that will handle the scene
	 */
	@Override
	public void init(Renderer renderer) {
		this.percentages = Test.experiment();
		this.colors = new Color[percentages.length];

		final Supplier<Color> randomColor = () -> {
			return new Color(random(0, 255), random(0, 255), random(0, 255));
		};

		for (int i = 0; i < colors.length; i++)
			colors[i] = randomColor.get();

		this.windowWidth = renderer.getWidth();
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

		final int WIDTH = 40;
		final int HEIGHT = 40;

		final int initX = 50;
		final int initY = 100;

		int x = initX;
		int y = initY;


		int count = 0;
		for (double d : percentages) {

			// If thread has finished
			if (winner < 0 && d >= 1.0D)
				winner = count;

			// Draw main rectangle
			g.setColor(Color.BLACK);
			g.drawRect(x, y, WIDTH, HEIGHT);

			g.setColor(colors[count]);

			int newHeight = (int) (HEIGHT * d);
			int newY = (int) (HEIGHT * (1 - d)) + y;
			g.fillRect(x, newY, WIDTH, newHeight);


			x += WIDTH + WIDTH / 4;
			if (x + WIDTH > windowWidth) {
				y += HEIGHT + HEIGHT / 4;
				x = initX;
			}

			count++;
		}

		// Draw winner
		if (winner > 0) {
			g.setColor(Color.BLACK);
			g.setFont(new Font("Arial", Font.PLAIN, 20));
			g.drawString("Winner: Thread " + (winner + 1), 20, 20);
		}
	}

	public static int random(int min, int max) {
		return (int) (Math.random() * (max - min) + min);
	}

	public static double[] experiment() {

		final int NUMBER_OF_THREADS = 1;

		final double[] percentag_work_done = new double[NUMBER_OF_THREADS];
		final Thread threads[] = new Thread[NUMBER_OF_THREADS];

		for (int i = 0; i < threads.length; i++) {
			int finalI = i;
			threads[i] = new Thread(new Runnable() {

				private final int index = finalI;

				@Override
				public void run() {

					double sum = 0.0;
					var num = 1_000_000.00;
					while (sum < num) {
						sum += Math.sqrt(Math.random());
						percentag_work_done[index] = sum / num;
					}
				}
			});
		}

		for (Thread thread : threads)
			thread.start();


		return percentag_work_done;
	}
}
