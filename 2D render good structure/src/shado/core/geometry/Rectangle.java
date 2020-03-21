/**
 *
 */

package shado.core.geometry;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import shado.core.util.Dimension;
import shado.core.util.Vec3;
import shado.core.util.Vertex;

public class Rectangle extends Shape {

	public Rectangle(float x, float y, float w, float h) {
		super(x, y, w, h);
	}

	public Rectangle(Vec3 v, Dimension d) {
		super((float) v.x, (float) v.y, d.width, d.height);
	}

	public Rectangle() {
		super(0.0f, 0.0f, 0.0f, 0.0f);
	}

	public Rectangle(Rectangle other) {
		super(other.position, other.dimensions);
	}

	/**
	 * Draws the shape to the screen
	 *
	 * @param c The canvas wheren to draw
	 */
	@Override
	public void draw(Canvas c) {
		GraphicsContext gc = c.getGraphicsContext2D();

		// Draw Shape
		gc.setFill(fill);
		gc.setStroke(stroke);
		gc.setLineWidth(lineWidth);

		float newX = (float) (position.x * c.getWidth());
		float newY = (float) (position.y * c.getHeight());
		float newWidth = dimensions.width * (float) c.getWidth();
		float newHeight = dimensions.height * (float) c.getHeight();

		gc.fillRect(newX, newY, newWidth, newHeight);
		gc.strokeRect(newX, newY, newWidth, newHeight);
	}

	// ======================= COLLISION =======================

	/**
	 * Checks if the calling object Collides with a rectangle
	 *
	 * @param rect The rectangle
	 * @return Returns true if they overlap and false otherwise
	 */
	@Override
	public boolean collides(Rectangle rect) {
		return rect.position.x + rect.dimensions.width >= position.x
				&& rect.position.x <= position.x + dimensions.width
				&& rect.position.y + rect.dimensions.height >= position.y
				&& rect.position.y <= position.y + dimensions.height;
	}

	/**
	 * Checks if the calling object Collides with a Circle
	 *
	 * @param circle The rectangle
	 * @return Returns true if they overlap and false otherwise
	 */
	@Override
	public boolean collides(Circle circle) {
		return circle.collides(this);
	}

	/**
	 * Checks if the calling object Collides with a Vertex
	 *
	 * @param v The Vertex
	 * @return Returns true if they overlap and false otherwise
	 */
	@Override
	public boolean collides(Vertex v) {
		return v.x >= position.x
				&& v.x <= position.x + dimensions.width
				&& v.y >= position.y
				&& v.y <= position.y + dimensions.height;
	}
}
