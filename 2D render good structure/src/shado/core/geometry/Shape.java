/**
 *
 */

package shado.core.geometry;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import shado.core.interfaces.Collidable;
import shado.core.interfaces.EventListener;
import shado.core.interfaces.Movable;
import shado.core.util.Dimension;
import shado.core.util.Vec3;

import java.util.function.Consumer;

public abstract class Shape implements EventListener<Shape>, Collidable, Movable {

	protected Vec3 position;
	protected Dimension dimensions;

	protected Color fill;
	protected Color stroke;
	protected int lineWidth;

	protected Consumer<Shape> clickEvent;
	protected Consumer<Shape> hoverEvent;
	protected Consumer<Shape> outEvent;
	protected boolean outEventConsumed;

	protected boolean hidden;

	/**
	 * Constructor from the percentage of x,y,w,h to the screen
	 * @param x The position of the shape to the screen (E.g. 0.25 = 25% of the screen width)
	 * @param y The position of the shape to the screen (E.g. 0.25 = 25% of the screen height)
	 * @param w The width of the shape to the screen (E.g. 0.25 = 25% of the screen width)
	 * @param h The height of the shape to the screen (E.g. 0.25 = 25% of the screen height)
	 */
	protected Shape(float x, float y, float w, float h) {
		position = new Vec3(x, y);
		dimensions = new Dimension(w, h);

		fill = Color.WHITE;
		stroke = Color.BLACK;
		lineWidth = 1;
	}

	protected Shape(Vec3 v, Dimension d) {
		this((float) v.x, (float) v.y, d.width, d.height);
	}

	protected Shape(Shape other) {
		position = new Vec3(other.position);
		dimensions = new Dimension(other.dimensions);

		fill = Color.rgb((int) other.fill.getRed(), (int) other.fill.getGreen(), (int) other.fill.getBlue(), other.fill.getOpacity());
		stroke = Color.rgb((int) other.stroke.getRed(), (int) other.stroke.getGreen(), (int) other.stroke.getBlue(), other.stroke.getOpacity());

		lineWidth = other.lineWidth;
	}

	/**
	 * Draws the shape to the screen
	 * @param c The canvas wheren to draw
	 */
	public abstract void draw(Canvas c);

	// Set Events

	/**
	 * Execute a function whenever mouse is clicked on the element
	 * @param function The function to execute
	 */
	public void onClick(Consumer<Shape> function) {
		clickEvent = function;
	}

	/**
	 * Execute a function whenever mouse is over the element
	 * @param function The function to execute
	 */
	public void onMouseOver(Consumer<Shape> function) {
		hoverEvent = function;
	}

	/**
	 * Execute a function whenever mouse is out of the element
	 * @param function The function to execute
	 */
	public void onMouseOut(Consumer<Shape> function) {
		outEvent = function;
	}

	public void consumeOutEvent(boolean status) {
		outEventConsumed = status;
	}

	// Get events

	/**
	 * @return Returns the function for whenever mouse is clicked on the element
	 */
	public Consumer<Shape> clickEvent() {
		return clickEvent;
	}

	/**
	 * @return Returns the function for whenever mouse is over the element
	 */
	public Consumer<Shape> hoverEvent() {
		return hoverEvent;
	}

	/**
	 * @return Returns the function for whenever mouse is out of the element
	 */
	public Consumer<Shape> outEvent() {
		return outEvent;
	}

	public boolean outEventConsumed() {
		return outEventConsumed;
	}

	// Setters

	/**
	 * Change the position of the shape
	 * @param x The new X position in a percentage to the screen width
	 * @param y The new Y position in a percentage to the screen height
	 */
	public void setPosition(float x, float y) {
		position.x = x;
		position.y = y;
	}

	public void setPosition(Vec3 v) {
		position.x = (float) v.x;
		position.y = (float) v.y;
	}

	public void move(float x, float y) {
		position.x += x;
		position.y += y;
	}

	/**
	 * Change the dimensions of the shape
	 * @param w The new Width in a percentage to the screen width
	 * @param h The new Height in a percentage to the screen height
	 */
	public void setDimensions(float w, float h) {
		dimensions.width = w;
		dimensions.height = h;
	}

	/**
	 * Set the fill of the shape
	 * @param color The color to set
	 */
	public void setFill(Color color) {
		this.fill = color;
	}

	/**
	 * Set the stroke of the shape
	 * @param color The color to set
	 */
	public void setStroke(Color color) {
		this.stroke = color;
	}

	/**
	 * Set the line width of the stroke of the shape
	 * @param lineWidth The line width to set
	 */
	public void setLineWidth(int lineWidth) {
		this.lineWidth = lineWidth;
	}

	/**
	 * Hide the layer to NOT render it to the screen
	 */
	public void hide() {
		hidden = false;
	}

	/**
	 * Show the layer to render it to the screen
	 */
	public void show() {
		hidden = true;
	}

	/**
	 * @return Returns the position of the shape
	 */
	public Vec3 getPosition() {
		return position;
	}

	/**
	 * @return Returns the dimensions of the shape
	 */
	public Dimension getDimensions() {
		return dimensions;
	}

	/**
	 * @return Returns the fill of the shape
	 */
	public Color getFill() {
		return fill;
	}

	/**
	 * @return Returns the stroke color of the shape
	 */
	public Color getStroke() {
		return stroke;
	}

	public boolean isHidden() {
		return hidden;
	}

	public String toString() {
		return String.format("(x: %f, y: %f, w: %f, h: %f)", position.x, position.y, dimensions.width, dimensions.height);
	}
}
