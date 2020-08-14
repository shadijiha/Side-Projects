/**
 *
 */

package com.img;

import com.engin.Renderer;
import com.engin.Scene;
import com.engin.Util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class RandomImage extends Scene {

	Renderer renderer;
	BufferedImage image;

	public RandomImage() {
		super("Random Image");
	}

	/**
	 * Initializes the scene and its variables
	 *
	 * @param renderer The renderer that will handle the scene
	 */
	@Override
	public void init(Renderer renderer) {

		this.renderer = renderer;

		BufferedImage image = new BufferedImage(600, 600, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();

		int currentX = 0;
		int currentY = 0;
		int scale = 1;

		Color color = Util.randomColor();
		for (int i = 0; i < 600 * 600; i += scale) {
			var c = Util.randomColor(color);
			color = c;

			g.setColor(c);
			g.drawRect(currentX, currentY, scale, scale);

			currentX++;
			if (currentX > 600) {
				currentX = 0;
				currentY++;
			}
		}

		g.dispose();

		this.image = image;


		try {
			ImageIO.write(this.image, "png", new File("hehexd.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Updates the state of the scene
	 *
	 * @param dt The time between 2 frames in ms
	 */
	@Override
	public void update(float dt) {
		renderer.getWindow().setTitle(1 / dt + "");
		this.init(renderer);
	}

	/**
	 * Draws the scene content to the screen
	 *
	 * @param g The graphics that will handle the drawing
	 */
	@Override
	public void draw(Graphics g) {
		g.drawImage(image, 0, 0, null);
	}
}
