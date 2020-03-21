/**
 *
 */

package shado.core.geometry;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import shado.core.util.Vertex;

public class Circle extends Shape {

	public Circle(float x, float y, float r) {
		super(x, y, r, r);
	}

	public Circle() {
		this(0.0f, 0.0f, 0.0f);
	}

	public Circle(Circle other) {
		super(other);
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
		float newHeight = dimensions.height * (float) c.getWidth();            // Because it is a Circle

		gc.fillOval(newX, newY, newWidth, newHeight);
		gc.strokeOval(newX, newY, newWidth, newHeight);
	}

	public Vertex getCenter() {
		return new Vertex((float) (position.x + getRadius()), (float) (position.y + getRadius()));
	}

	public float getRadius() {
		return dimensions.width / 2;
	}

	// ================= COLLISION =========================

	/**
	 * Checks if the calling object Collides with a rectangle
	 *
	 * @param rect The rectangle
	 * @return Returns true if they overlap and false otherwise
	 */
	@Override
	public boolean collides(Rectangle rect) {
		Rectangle hitBox = new Rectangle((float) position.x, (float) position.y, dimensions.width, dimensions.width);
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
		var centerO = circle.getCenter();
		var centerT = this.getCenter();
		return centerO.getDistance(centerT) <= circle.getRadius() + this.getRadius();
	}

	/**
	 * Checks if the calling object Collides with a Vertex
	 *
	 * @param v The Vertex
	 * @return Returns true if they overlap and false otherwise
	 */
	@Override
	public boolean collides(Vertex v) {
		return Math.abs(v.getDistance(getCenter())) <= Math.abs(this.getRadius());
	}
}
