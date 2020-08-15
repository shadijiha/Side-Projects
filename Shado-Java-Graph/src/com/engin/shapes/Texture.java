package com.engin.shapes;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.IOException;

public class Texture extends Image {

	private int x;
	private int y;
	private final String path;

	private final BufferedImage image;
	private final int originalWidth;
	private final int originalHeight;

	public Texture(String path, int desiredwidth, int desiredHeight) {
		this.path = path;

		int originalHeight1;
		int originalWidth1;
		BufferedImage image1;
		try {
			image1 = ImageIO.read(new File(path));
			originalWidth1 = image1.getWidth(null);
			originalHeight1 = image1.getHeight(null);

			// Resize the image
			image1 = resize(image1, desiredwidth, desiredHeight);
		} catch (IOException e) {
			e.printStackTrace();
			image1 = null;
			originalWidth1 = 0;
			originalHeight1 = 0;
		}
		originalHeight = originalHeight1;
		originalWidth = originalWidth1;
		image = image1;
	}

	public Texture(String path) {
		this.path = path;

		int originalHeight1;
		int originalWidth1;
		BufferedImage image1;
		try {
			image1 = ImageIO.read(new File(path));
			originalWidth1 = image1.getWidth(null);
			originalHeight1 = image1.getHeight(null);
		} catch (IOException e) {
			e.printStackTrace();
			image1 = null;
			originalWidth1 = 0;
			originalHeight1 = 0;
		}
		originalHeight = originalHeight1;
		originalWidth = originalWidth1;
		image = image1;
	}

	Texture(BufferedImage image) {
		this.path = null;

		this.image = image;
		originalWidth = image.getWidth();
		originalHeight = image.getHeight();
	}

	static BufferedImage resize(BufferedImage img, int newW, int newH) {
		Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
		BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2d = dimg.createGraphics();
		g2d.drawImage(tmp, 0, 0, null);
		g2d.dispose();

		return dimg;
	}

	public void draw(Graphics g) {
		g.drawImage(image, x, y, null);
	}

	/***************************** Getters and setters *******************************/
	public Texture setPosition(int x, int y) {
		this.x = x;
		this.y = y;
		return this;
	}

	public int getX() {
		return x;
	}

	public Texture setX(int x) {
		this.x = x;
		return this;
	}

	public int getY() {
		return y;
	}

	public Texture setY(int y) {
		this.y = y;
		return this;
	}

	BufferedImage getImage() {
		return this.image;
	}

	/**
	 * Determines the width of the image. If the width is not yet known,
	 * this method returns {@code -1} and the specified
	 * {@code ImageObserver} object is notified later.
	 *
	 * @param observer an object waiting for the image to be loaded.
	 * @return the width of this image, or {@code -1}
	 * if the width is not yet known.
	 * @see Image#getHeight
	 * @see ImageObserver
	 */
	@Override
	public int getWidth(ImageObserver observer) {
		return image.getWidth(null);
	}

	public int getWidth() {
		return getWidth(null);
	}

	/**
	 * Determines the height of the image. If the height is not yet known,
	 * this method returns {@code -1} and the specified
	 * {@code ImageObserver} object is notified later.
	 *
	 * @param observer an object waiting for the image to be loaded.
	 * @return the height of this image, or {@code -1}
	 * if the height is not yet known.
	 * @see Image#getWidth
	 * @see ImageObserver
	 */
	@Override
	public int getHeight(ImageObserver observer) {
		return image.getHeight(null);
	}

	public int getHeight() {
		return getHeight(null);
	}

	/**
	 * Gets the object that produces the pixels for the image.
	 * This method is called by the image filtering classes and by
	 * methods that perform image conversion and scaling.
	 *
	 * @return the image producer that produces the pixels
	 * for this image.
	 * @see ImageProducer
	 */
	@Override
	public ImageProducer getSource() {
		return image.getSource();
	}

	/**
	 * Creates a graphics context for drawing to an off-screen image.
	 * This method can only be called for off-screen images.
	 *
	 * @return a graphics context to draw to the off-screen image.
	 * @throws UnsupportedOperationException if called for a
	 *                                       non-off-screen image.
	 * @see Graphics
	 * @see Component#createImage(int, int)
	 */
	@Override
	public Graphics getGraphics() {
		return image.getGraphics();
	}

	/**
	 * Gets a property of this image by name.
	 * <p>
	 * Individual property names are defined by the various image
	 * formats. If a property is not defined for a particular image, this
	 * method returns the {@code UndefinedProperty} object.
	 * <p>
	 * If the properties for this image are not yet known, this method
	 * returns {@code null}, and the {@code ImageObserver}
	 * object is notified later.
	 * <p>
	 * The property name {@code "comment"} should be used to store
	 * an optional comment which can be presented to the application as a
	 * description of the image, its source, or its author.
	 *
	 * @param name     a property name.
	 * @param observer an object waiting for this image to be loaded.
	 * @return the value of the named property.
	 * @throws NullPointerException if the property name is null.
	 * @see ImageObserver
	 * @see Image#UndefinedProperty
	 */
	@Override
	public Object getProperty(String name, ImageObserver observer) {
		return image.getProperty(name, observer);
	}

	public String getPath() {
		return path;
	}
}