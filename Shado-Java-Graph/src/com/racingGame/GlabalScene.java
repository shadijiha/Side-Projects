/**
 *
 */

package com.racingGame;

import com.engin.Renderer;
import com.engin.Scene;

import java.awt.*;

public class GlabalScene extends Scene {

	private Renderer renderer;

	private final float carPos;

	public GlabalScene() {
		super("racing game");

		carPos = 0.0f;
	}

	/**
	 * Initializes the scene and its variables
	 *
	 * @param renderer The renderer that will handle the scene
	 */
	@Override
	public void init(Renderer renderer) {
		this.renderer = renderer;
	}

	/**
	 * Updates the state of the scene
	 *
	 * @param dt The time between 2 frames in ms
	 */
	@Override
	public void update(float dt) {
		renderer.getWindow().setTitle((int) (1 / dt) + "");
	}

	/**
	 * Draws the scene content to the screen
	 *
	 * @param g The graphics that will handle the drawing
	 */
	@Override
	public void draw(Graphics g) {

		for (int y = 0; y < renderer.getHeight() / 2; y++) {
			for (int x = 0; x < renderer.getWidth(); x++) {

				float perspective = (float) y / (renderer.getWidth() / 2.0f);

				float middlePoint = 0.5f;
				float roadWidth = 0.1f + perspective * 0.8f; // 60% of the screen is a road
				float clipWidth = roadWidth * 0.15f;

				roadWidth *= 0.5f;

				int leftGrass = (int) ((middlePoint - roadWidth - clipWidth) * renderer.getWidth());
				int leftClip = (int) ((middlePoint - roadWidth) * renderer.getWidth());
				int rightGrass = (int) ((middlePoint + roadWidth + clipWidth) * renderer.getWidth());
				int rightClip = (int) ((middlePoint + roadWidth) * renderer.getWidth());

				int row = renderer.getHeight() / 2 + y;

				if (x >= 0 && x < leftGrass) {
					g.setColor(Color.GREEN);
					g.drawRect(x, row, 1, 1);
				}

				if (x >= leftGrass && x < leftClip) {
					g.setColor(Color.RED);
					g.drawRect(x, row, 1, 1);
				}

				if (x >= leftClip && x < rightClip) {
					g.setColor(Color.GRAY);
					g.drawRect(x, row, 1, 1);
				}

				if (x >= rightClip && x < rightGrass) {
					g.setColor(Color.RED);
					g.drawRect(x, row, 1, 1);
				}

				if (x >= rightGrass && x < renderer.getWidth()) {
					g.setColor(Color.GREEN);
					g.drawRect(x, row, 1, 1);
				}

			}
		}

		// Draw car
		int carPos = (int) (renderer.getWidth() / 2 + (int) (renderer.getWidth() * this.carPos) / 2.0f) - 7;

		int offsetYCar = 400;

		g.setColor(Color.BLACK);
		g.drawString("   ||####||   ", carPos, offsetYCar + 80);
		g.drawString("      ##      ", carPos, offsetYCar + 84);
		g.drawString("     ####     ", carPos, offsetYCar + 88);
		g.drawString("     ####     ", carPos, offsetYCar + 92);
		g.drawString("|||  ####  |||", carPos, offsetYCar + 96);
		g.drawString("|||########|||", carPos, offsetYCar + 100);
		g.drawString("|||  ####  |||", carPos, offsetYCar + 104);
	}
}
