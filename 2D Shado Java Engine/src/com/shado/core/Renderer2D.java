/**
 *
 */

package com.shado.core;

import com.shado.gfx.Image;
import com.shado.gfx.*;
import com.shado.intefaces.*;
import com.shado.layers.*;

import java.awt.*;
import java.awt.image.*;
import java.util.*;

public class Renderer2D {

	private int pW;    // Pixel width
	private int pH;    // Pixel Height
	private int[] p;    // Pixel array
	private int[] z_buffer;
	private int[] light_map;
	private int[] light_block;

	private int ambient_color = 0xff232323;
	private int zDepth = 0;

	private final ArrayList<ImageRequest> imageRequests = new ArrayList<ImageRequest>();
	private final ArrayList<LightRequest> lightRequests = new ArrayList<LightRequest>();

	private GameContainer gc;
	private boolean processing = false;

	public Renderer2D(GameContainer gc) {
		pW = gc.getWidth();
		pH = gc.getHeight();
		p = ((DataBufferInt) gc.getWindow().getImage().getRaster().getDataBuffer()).getData();
		z_buffer = new int[p.length];
		light_map = new int[p.length];
		light_block = new int[p.length];

		this.gc = gc;
	}

	/**
	 * Clears the Renderer with a Black color
	 */
	public void clear() {
		for (int i = 0; i < p.length; i++) {
			p[i] = 0;
			z_buffer[i] = 0;
			light_map[i] = ambient_color;
			light_block[i] = 0;
		}
	}

	/**
	 * Takes all Images that have alpha and sort them by their Z depth (renders closest last)
	 *
	 */
	public void process() {

		processing = true;

		imageRequests.sort(new Comparator<ImageRequest>() {
			@Override
			public int compare(ImageRequest o1, ImageRequest o2) {
				return Integer.compare(o2.z_depth, o1.z_depth);
			}
		});

		// Display Alpha images
		//TODO:: make this parallel
		synchronized (imageRequests) {
			for (ImageRequest request : imageRequests) {
				drawImage(request.image, request.x, request.y);
			}

			imageRequests.clear();
		}

		// Display Lights
		//TODO:: Make this parallel
		synchronized (lightRequests) {
			for (LightRequest request : lightRequests) {
				drawLightRequest(request.light, request.x, request.y);
			}
		}

		// Merge light map and pixel map
		for (int i = 0; i < p.length; i++) {
			float r = ((light_map[i] >> 16) & 0xff) / 255f;
			float g = ((light_map[i] >> 8) & 0xff) / 255f;
			float b = (light_map[i] & 0xff) / 255.0f;

			p[i] = ((int) (((p[i] >> 16) & 0xff) * r) << 16) | ((int) (((p[i] >> 8) & 0xff) * g) << 8) | ((int) ((p[i] & 0xff) * b));
		}

		imageRequests.clear();
		lightRequests.clear();
		processing = false;
	}

	/**
	 * Sets a particlar pixel in the screen to a specific color
	 * NOTE: this method uses magenta (0xffff00ff) as "transparent"
	 * @param x The X position
	 * @param y The Y position
	 * @param value The new Color value
	 */
	public void setPixel(int x, int y, int value) {

		int alpha = ((value >> 24) & 0xff);

		// If pixel is out of bounds
		if ((x < 0 || x >= pW || y < 0 || y >= pH) || alpha == 0)
			return;

		int index = x + y * pW;

		if (z_buffer[index] > zDepth)
			return;

		// Update Z buffer
		z_buffer[index] = zDepth;

		// Alpha blending
		if (alpha == 255) {
			p[index] = value;
		} else {
			int pixel_color = p[index];

			int newRed = ((pixel_color >> 16) & 0xff) - (int) ((((pixel_color >> 16) & 0xff) - ((value >> 16) & 0xff)) * (alpha / 255.0f));
			int newGreen = ((pixel_color >> 8) & 0xff) - (int) ((((pixel_color >> 8) & 0xff) - ((value >> 8) & 0xff)) * (alpha / 255.0f));
			int newBlue = (pixel_color & 0xff) - (int) (((pixel_color & 0xff) - (value & 0xff)) * (alpha / 255.0f));

			p[index] = (newRed << 16 | newGreen << 8 | newBlue);
		}
	}

	private void setPixel(double x, double y, int value) {
		setPixel((int) x, (int) y, value);
	}

	/**
	 * Modifies the light map of the renderer (to me merged with the image later)
	 * @param x The X position
	 * @param y The Y position
	 * @param value The color value to set
	 */
	private void setLightMap(int x, int y, int value) {
		if (x < 0 || x >= pW || y < 0 || y >= pH)
			return;

		int base_color = light_map[x + y * pW];

		int maxRed = Math.max((base_color >> 16) & 0xff, (value >> 16) & 0xff);
		int maxGreen = Math.max((base_color >> 8) & 0xff, (value >> 8) & 0xff);
		int maxBlue = Math.max(base_color & 0xff, value & 0xff);

		light_map[x + y * pW] = (maxRed << 16 | maxGreen << 8 | maxBlue);
	}

	private void setLightBlock(int x, int y, int value) {
		if (x < 0 || x >= pW || y < 0 || y >= pH)
			return;

		if (z_buffer[x + y * pW] > zDepth)
			return;

		light_block[x + y * pW] = value;
	}

	/**
	 * Draws an image to the screen
	 * @param image The image to draw
	 * @param offX The offset in X
	 * @param offY the offset in Y
	 */
	public void drawImage(Image image, int offX, int offY) {

		// If image has alpha
		if (image.hasAlpha() && !processing) {
			imageRequests.add(new ImageRequest(image, zDepth, offX, offY));
			return;
		}

		// Don't render code
		if (offX < -image.getWidth() || offX >= pW)
			return;
		if (offY < -image.getHeight() || offY >= pH)
			return;


		int newX = 0;
		int newY = 0;
		int newWidth = image.getWidth();
		int newHeight = image.getHeight();

		// Clipping code
		if (offX < 0) {
			newX -= offX;
		}
		if (offY < 0) {
			newY -= offY;
		}
		if (newWidth + offX >= pW) {
			newWidth -= newWidth + offX - pW;
		}
		if (newHeight + offY >= pH) {
			newHeight -= newHeight + offY - pH;
		}

		for (int y = newY; y < newHeight; y++) {
			for (int x = newX; x < newWidth; x++) {
				setPixel(x + offX, y + offY, image.getPixels()[x + y * image.getWidth()]);
				setLightBlock(x + offX, y + offY, image.getLightBlock());
			}
		}

	}

	/**
	 * Draws a tiled image to the Screen with a specific index to that tile
	 * @param image The tiled Image
	 * @param offX The X position
	 * @param offY The Y position
	 * @param tileX The colon (X) index of the tile to draw
	 * @param tileY The row (Y) index of the tile to draw
	 */
	public void drawImageTile(ImageTile image, int offX, int offY, int tileX, int tileY) {

		// If image has alpha
		if (image.hasAlpha() && !processing) {
			imageRequests.add(new ImageRequest(image.getImageTile(tileX, tileY), zDepth, offX, offY));
			return;
		}

		// Don't render code
		if (offX < -image.getTileWidth() || offX >= pW)
			return;
		if (offY < -image.getTileHeight() || offY >= pH)
			return;


		int newX = 0;
		int newY = 0;
		int newWidth = image.getTileWidth();
		int newHeight = image.getTileHeight();

		// Clipping code
		if (offX < 0) {
			newX -= offX;
		}
		if (offY < 0) {
			newY -= offY;
		}
		if (newWidth + offX >= pW) {
			newWidth -= newWidth + offX - pW;
		}
		if (newHeight + offY >= pH) {
			newHeight -= newHeight + offY - pH;
		}

		for (int y = newY; y < newHeight; y++) {
			for (int x = newX; x < newWidth; x++) {
				setPixel(x + offX,
						y + offY,
						image.getPixels()[(x + tileX * image.getTileWidth()) + (y + tileY * image.getTileHeight()) * image.getWidth()]);
				setLightBlock(x + offX, y + offY, image.getLightBlock());
			}
		}
	}

	/**
	 * Draws a text to the Screen
	 * @param text The content of the text to draw
	 * @param offX The position X
	 * @param offY The position Y
	 * @param font The font of the text
	 * @param color The color of the text
	 */
	public void drawText(String text, int offX, int offY, Font font, Color color) {

		var tempFont = font.deriveFont(font.getSize() * gc.getScale());
		gc.getWindow().addText(new Text(text, (int) (offX * gc.getScale()), (int) (offY * gc.getScale()), tempFont, color));

	}

	/**
	 * Draws a rectangle stroke to the BufferedImage (screen)
	 * @param offX The X position
	 * @param offY The Y position
	 * @param width The width
	 * @param height The Height
	 * @param color The color
	 */
	public void drawRect(int offX, int offY, int width, int height, int color) {

		// Draw
		for (int y = 0; y <= height; y++) {
			setPixel(offX, y + offY, color);
			setPixel(offX + width, y + offY, color);
		}

		for (int x = 0; x <= width; x++) {
			setPixel(offX + x, offY, color);
			setPixel(offX + x, offY + height, color);
		}
	}

	/**
	 * Fills a rectangle to the BufferedImage (screen)
	 * @param offX The X position
	 * @param offY The Y position
	 * @param width The width
	 * @param height The Height
	 * @param color The color
	 * @param block If the rectangle Blocks the light or not
	 */
	public void fillRect(int offX, int offY, int width, int height, int color, int block) {

		// Don't render code
		if (offX < -width || offX >= pW)
			return;
		if (offY < -height || offY >= pH)
			return;


		int newX = 0;
		int newY = 0;
		int newWidth = width;
		int newHeight = height;

		// Clipping code
		if (offX < 0) {
			newX -= offX;
		}
		if (offY < 0) {
			newY -= offY;
		}
		if (newWidth + offX >= pW) {
			newWidth -= newWidth + offX - pW;
		}
		if (newHeight + offY >= pH) {
			newHeight -= newHeight + offY - pH;
		}

		for (int y = newY; y <= newHeight; y++) {
			for (int x = newX; x <= newWidth; x++) {
				setPixel(offX + x, offY + y, color);
				setLightBlock(x + offX, y + offY, block);
			}
		}
	}

	public void drawLine(int offX, int offY, int endX, int endY, int color) {
		// Don't render code
		if (offX < -Math.abs(endX - offX) || offX >= pW)
			return;
		if (offY < -Math.abs(endY - offY) || offY >= pH)
			return;

		int dx = endX - offX;
		int dy = endY - offY;

		for (int x = offX; x <= endX; x++) {
			int temp_y = offY + dy * (x - offX) / dx;
			setPixel(x, temp_y, color);
		}
	}

	/**
	 * Draws an ellipse to the screen
	 * @param centerX The center of the ellipse in X
	 * @param centerY The center of the ellipse in Y
	 * @param width The width of the ellipse in X
	 * @param height The width of the ellipse in Y
	 * @param color The color of the ellipse
	 */
	public void drawEllipse(int centerX, int centerY, int width, int height, int color) {
		double dx, dy, d1, d2, x, y;
		double xc = centerX, yc = centerY, rx = width, ry = height;
		x = 0;
		y = ry;

		// Initial decision parameter of region 1
		d1 = (ry * ry) - (rx * rx * ry) +
				(0.25 * rx * rx);
		dx = 2 * ry * ry * x;
		dy = 2 * rx * rx * y;

		// For region 1
		while (dx < dy) {

			// Print points based on 4-way symmetry
			setPixel(x + xc, y + yc, color);
			setPixel(-x + xc, y + yc, color);
			setPixel(x + xc, -y + yc, color);
			setPixel(-x + xc, -y + yc, color);

			// Checking and updating value of
			// decision parameter based on algorithm
			if (d1 < 0) {
				x++;
				dx = dx + (2 * ry * ry);
				d1 = d1 + dx + (ry * ry);
			} else {
				x++;
				y--;
				dx = dx + (2 * ry * ry);
				dy = dy - (2 * rx * rx);
				d1 = d1 + dx - dy + (ry * ry);
			}
		}

		// Decision parameter of region 2
		d2 = ((ry * ry) * ((x + 0.5) * (x + 0.5))) +
				((rx * rx) * ((y - 1) * (y - 1))) -
				(rx * rx * ry * ry);

		// Plotting points of region 2
		while (y >= 0) {

			// Print points based on 4-way symmetry
			setPixel(x + xc, y + yc, color);
			setPixel(-x + xc, y + yc, color);
			setPixel(x + xc, -y + yc, color);
			setPixel(-x + xc, -y + yc, color);

			// Checking and updating parameter
			// value based on algorithm
			if (d2 > 0) {
				y--;
				dy = dy - (2 * rx * rx);
				d2 = d2 + (rx * rx) - dy;
			} else {
				y--;
				x++;
				dx = dx + (2 * ry * ry);
				dy = dy - (2 * rx * rx);
				d2 = d2 + dx - dy + (rx * rx);
			}
		}
	}

	/**
	 * Draws an ellipse to the renderer Buffered image
	 * @param offX The position X
	 * @param offY The position Y
	 * @param width The ellipse width
	 * @param height The ellipse height
	 * @param color The ellipse fill color
	 * @param block If the ellipse blocks the light or not
	 */
	public void fillEllipse(int offX, int offY, int width, int height, int color, int block) {
		for (int y = -height; y <= height; y++) {
			for (int x = -width; x <= width; x++) {
				if (x * x * height * height + y * y * width * width <= height * height * width * width) {
					setPixel(offX + x, offY + y, color);
					setLightBlock(x + offX, y + offY, block);
				}
			}
		}
	}

	/**
	 * Draws a light in the renderer (Adds it to the Light Queue)
	 * @param l The light to draw
	 * @param offX The position X of the light
	 * @param offY The position Y of the light
	 */
	public void drawLight(Light l, int offX, int offY) {
		lightRequests.add(new LightRequest(l, offX, offY));
	}

	/**
	 * Draws a light in the renderer
	 * @param l The light to draw
	 * @param offX The position X of the light
	 * @param offY The position Y of the light
	 */
	private void drawLightRequest(Light l, int offX, int offY) {

		for (int i = 0; i <= l.getDiameter(); i++) {
			drawLightLine(l, l.getRadius(), l.getRadius(), i, 0, offX, offY);
			drawLightLine(l, l.getRadius(), l.getRadius(), i, l.getDiameter(), offX, offY);
			drawLightLine(l, l.getRadius(), l.getRadius(), 0, i, offX, offY);
			drawLightLine(l, l.getRadius(), l.getRadius(), l.getDiameter(), i, offX, offY);
		}

	}

	/**
	 * Helper function that draws a single line of light (This function uses only integer algorithm)
	 * @param l The light to draw
	 * @param x0 The radius of the light to draw in X
	 * @param y0 The radius of the light to draw in Y
	 * @param x1 The start X of the line
	 * @param y1 The start Y of the line
	 * @param offX The offset in X of the line
	 * @param offY The offset in Y of the line
	 */
	private void drawLightLine(Light l, int x0, int y0, int x1, int y1, int offX, int offY) {
		int dx = Math.abs(x1 - x0);
		int dy = Math.abs(y1 - y0);

		int sx = x0 < x1 ? 1 : -1;
		int sy = y0 < y1 ? 1 : -1;

		int err = dx - dy;
		int err2;

		while (true) {

			int screenX = x0 - l.getRadius() + offX;
			int screenY = y0 - l.getRadius() + offY;
			int light_color = l.getLightValue(x0, y0);

			// Screen out of bounds
			if (screenX < 0 || screenX >= pW || screenY < 0 || screenY >= pH)
				return;

			// Light out of bounds
			if (light_color == Light.OUT_OF_BOUNDS)
				return;

			if (light_block[screenX + screenY * pW] == Light.FULL_BLOCK)
				return;

			setLightMap(screenX, screenY, light_color);

			if (x0 == x1 && y0 == y1)
				break;

			err2 = 2 * err;
			if (err2 > -1 * dy) {
				err -= dy;
				x0 += sx;
			}

			if (err2 < dx) {
				err += dx;
				y0 += sy;
			}
		}
	}

	public void drawLayer(Layer<?> layer) {
		//TODO: make this parallel
		for (Drawable d : layer.objectsToRender()) {
			d.draw(this);
		}
	}

	/**
	 * @return Returns the current Z buffer of the renderer
	 */
	public int getzDepth() {
		return zDepth;
	}

	/**
	 * Changes the Z buffer of the renderer (Useful for Alpha images rendering)
	 * @param zDepth The new Z buffer
	 */
	public void setDepth(int zDepth) {
		this.zDepth = zDepth;
	}

	/**
	 * Changes the Z buffer of the renderer (Useful for Alpha images rendering)
	 * @param zDepth The new Z buffer
	 */
	public void setZIndex(int zDepth) {
		setDepth(zDepth);
	}

	public void setAmbientColor(int color) {
		this.ambient_color = color;
	}
}
