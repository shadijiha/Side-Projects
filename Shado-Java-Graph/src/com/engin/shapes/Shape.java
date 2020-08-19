/**
 *
 */
package com.engin.shapes;

import com.engin.math.Dimension;
import com.engin.math.*;

import java.awt.*;
import java.io.*;

public abstract class Shape implements Serializable {

	private static final long serialVersionUID = 3183164399459748671L;

	protected Vector2f position;
	protected Dimension<Integer> dimension;
	protected int lineWidth;

	protected Color fill;
	protected Color stroke;
	protected Texture texture;

	private Shape(int x, int y, int w, int h, Texture t) {
		this.position = new Vector2f(x, y);
		this.dimension = new Dimension<>(w, h);
		this.texture = t;
		this.fill = Color.WHITE;
		this.stroke = Color.BLACK;
		this.lineWidth = 1;
	}

	public Shape(int x, int y, int w, int h) {
		this(x, y, w, h, null);
	}

	public abstract void draw(Graphics g);

	/**
	 * Moves the shape to new location
	 * 
	 * @param x The new X
	 * @param y The new Y
	 */
	public void moveTo(float x, float y) {
		this.position.x = x;
		this.position.y = y;
	}

	public void moveTo(ICoordinates2F ICoordinates2F) {
		moveTo((int) ICoordinates2F.getX(), (int) ICoordinates2F.getY());
	}

	/**
	 * Increments the position of the object by the parameters passed
	 * 
	 * @param x The X to add
	 * @param y The Y to add
	 */
	public void moveBy(float x, float y) {
		position.x += x;
		position.y += y;
	}

	public void moveBy(ICoordinates2F ICoordinates2F) {
		moveBy((int) ICoordinates2F.getX(), (int) ICoordinates2F.getY());
	}

	/**
	 * Changes the fill of the shape
	 * 
	 * @param c The new Color
	 */
	public void setFill(Color c) {
		this.fill = c;
	}

	/**
	 * Changes the fill of the shape
	 * 
	 * @param c The new Color
	 */
	public void setStroke(Color c) {
		this.stroke = c;
	}

	/**
	 * Changes the line width of the shape
	 * 
	 * @param c The new line Width
	 */
	public void setLineWidth(int c) {
		this.lineWidth = c;
	}

	/**
	 * Changes the dimension of the shape
	 * 
	 * @param w
	 * @param h
	 */
	public void setDimension(int w, int h) {
		this.dimension.w = w;
		this.dimension.h = h;

		if (texture != null)
			this.texture = new Texture(Texture.resize(texture.getImage(), w, h));
	}

	public void setDimension(Dimension<Integer> d) {
		setDimension(d.w, d.h);
	}

	/**
	 * Changes the texture of the object
	 * 
	 * @param t
	 */
	public void setTexture(final Texture t) {
		var temp = new Texture(t.getPath(), dimension.w, dimension.h);
		this.texture = temp;
	}

	public void setTexture(final String path) {
		this.texture = new Texture(path, dimension.w, dimension.h);
	}

	public void removeTexture() {
		this.texture = null;
	}

	/**
	 * @return Returns a deep copy of the position of this shape
	 */
	public ImmutableVec2f getPosition() {
		return position.toImmutableVector();
	}

	/**
	 * @return Returns a deep copy of the dimensions of this object
	 */
	public Dimension<Integer> getDimension() {
		return new Dimension<>(this.dimension);
	}

	/**
	 * @return Returns the fill of the shape
	 */
	public Color getFill() {
		return fill;
	}

	/**
	 * @return Returns the stroke of the shape
	 */
	public Color getStroke() {
		return stroke;
	}

	/**
	 * @return Returns the fill of the shape
	 */
	public Texture getTexture() {
		return texture;
	}

	/**
	 * @return Returns the linewidth of the shape
	 */
	public int getLineWidth() {
		return lineWidth;
	}
}
