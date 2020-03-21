package shado.core.geometry;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import shado.core.util.Dimension;
import shado.core.util.Vec3;
import shado.core.util.Vertex;

import java.io.File;

public class Image extends Shape {

	private String src;
	private javafx.scene.image.Image img;
	private File file;

	public Image(String source, float x, float y, float w, float h) {
		super(x, y, w, h);
		src = source;

		// Open the image
		if (src != null) {
			file = new File(src);
			img = new javafx.scene.image.Image(String.valueOf(file.toURI()));
		}
	}

	public Image(String source, Vec3 v, Dimension d) {
		this(source, (float) v.x, (float) v.y, d.width, d.height);
	}

	public Image() {
		this(null, 0.0f, 0.0f, 0.0f, 0.0f);
	}

	public Image(Image other) {
		this(other.src, other.position, other.dimensions);
	}

	/**
	 * Draws the shape to the screen
	 *
	 * @param c The canvas where to draw
	 */
	@Override
	public void draw(Canvas c) {

		GraphicsContext g = c.getGraphicsContext2D();

		float newX = (float) (position.x * c.getWidth());
		float newY = (float) (position.y * c.getHeight());
		float newWidth = dimensions.width * (float) c.getWidth();
		float newHeight = dimensions.height * (float) c.getHeight();

		g.drawImage(img, newX, newY, newWidth, newHeight);

	}

	/**
	 * Change the position of the Image
	 *
	 * @param x The new X position in a percentage to the screen width
	 * @param y The new Y position in a percentage to the screen height
	 */
	@Override
	public void setPosition(float x, float y) {
		position.x = x;
		position.y = y;
	}

	/**
	 * Change the dimensions of the Image
	 *
	 * @param w The new Width in a percentage to the screen width
	 * @param h The new Height in a percentage to the screen height
	 */
	@Override
	public void setDimensions(float w, float h) {
		dimensions.width = w;
		dimensions.height = h;
		img = new javafx.scene.image.Image(src, dimensions.width, dimensions.height, false, true);
	}

	/**
	 * Changes the source of the drawn image
	 *
	 * @param path The new path of the image
	 */
	public void setSource(String path) {
		src = path;
		file = new File(path);
		img = new javafx.scene.image.Image(String.valueOf(file.toURI()));
	}

	//========================= COLLISION =======================

	/**
	 * Checks if the calling object Collides with a rectangle
	 *
	 * @param rect The rectangle
	 * @return Returns true if they overlap and false otherwise
	 */
	@Override
	public boolean collides(Rectangle rect) {
		Rectangle hitBox = new Rectangle(position, dimensions);
		return hitBox.collides(rect);
	}

	/**
	 * Checks if the calling object Collides with a Circle
	 *
	 * @param circle The rectangle
	 * @return Returns true if they overlap and false otherwise
	 */
	@Override
	public boolean collides(Circle circle) {
		Rectangle hitBox = new Rectangle(position, dimensions);
		return hitBox.collides(circle);
	}

	/**
	 * Checks if the calling object Collides with a Vertex
	 *
	 * @param v The Vertex
	 * @return Returns true if they overlap and false otherwise
	 */
	@Override
	public boolean collides(Vertex v) {
		Rectangle hitBox = new Rectangle(position, dimensions);
		return hitBox.collides(v);
	}
}
