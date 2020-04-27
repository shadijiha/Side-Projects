/**
 *
 */

package com.shado.gfx;

public class ImageTile extends Image {

	private int tileWidth, tileHeight;

	public ImageTile(String path, int tileWidth, int tileHeight) {
		super(path);
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
	}

	public Image getImageTile(int indexX, int indexY) {

		int[] temp_pixels = new int[tileWidth * tileHeight];

		for (int y = 0; y < tileHeight; y++) {
			for (int x = 0; x < tileWidth; x++) {
				temp_pixels[x + y * tileWidth] = this.getPixels()[(x + indexX * tileWidth) + (y + indexY * tileHeight) * this.getWidth()];
			}
		}

		var result = new Image(temp_pixels, tileWidth, tileHeight);
		result.alpha = this.alpha;

		return result;
	}

	public int getTileWidth() {
		return tileWidth;
	}

	public void setTileWidth(int tileWidth) {
		this.tileWidth = tileWidth;
	}

	public int getTileHeight() {
		return tileHeight;
	}

	public void setTileHeight(int tileHeight) {
		this.tileHeight = tileHeight;
	}
}
