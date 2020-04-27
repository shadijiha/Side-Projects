/**
 *
 */

package com.shado.gfx;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Image {

	private int width, height;
	private int[] pixels;

	protected boolean alpha = false;
	protected int light_block = Light.NO_BLOCK;

	public Image(String path) {
		BufferedImage image = null;

		// Load image
		try {
			image = ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (image == null) {
			System.out.println("\nCould not open image! " + new File(path).getAbsolutePath());
			System.exit(-1);
		}

		width = image.getWidth();
		height = image.getHeight();
		pixels = image.getRGB(0, 0, width, height, null, 0, width);

		image.flush();

		// Detect if Image has alpha
		detectAlpha();
	}

	public Image(int[] pixels, int width, int height) {
		this.pixels = pixels;
		this.width = width;
		this.height = height;
	}

	private void detectAlpha() {

		OuterLoop:
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (((pixels[x + y * width] >> 24) & 0xff) < 255) {
					alpha = true;
					break OuterLoop;
				}
			}
		}
	}

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

	public int[] getPixels() {
		return pixels;
	}

	public void setPixels(int[] pixels) {
		this.pixels = pixels;
	}

	public boolean hasAlpha() {
		return alpha;
	}

	public int getLightBlock() {
		return light_block;
	}

	public void setLightBlock(int light_block) {
		this.light_block = light_block;
	}
}
