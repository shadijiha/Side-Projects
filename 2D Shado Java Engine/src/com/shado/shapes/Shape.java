/**
 *
 */

package com.shado.shapes;

import com.shado.core.Renderer2D;
import com.shado.gfx.Light;
import com.shado.intefaces.Drawable;
import com.shado.math.Dimension;
import com.shado.math.Util;
import com.shado.math.Vertex;

public abstract class Shape implements Drawable {

	protected Vertex position;
	protected Dimension<Integer> dimensions;
	protected int stroke_color;
	protected int fill_color;

	protected boolean no_stroke = false;
	protected boolean no_fill = true;
	protected int light_block = Light.FULL_BLOCK;

	protected Shape(int x, int y, int w, int h) {
		position = new Vertex(x, y);
		dimensions = new Dimension<Integer>(w, h);
		fill_color = 0xff000000;
		stroke_color = 0xff000000;
	}

	protected Shape(Vertex position, Dimension<Integer> dimensions) {
		this((int) position.x, (int) position.y, dimensions.width, dimensions.height);
	}

	protected Shape(final Shape other) {
		this(other.position, other.dimensions);
		this.fill_color = other.fill_color;
		this.stroke_color = other.stroke_color;
		this.no_fill = other.no_fill;
		this.no_stroke = other.no_stroke;
	}

	public abstract void draw(Renderer2D r);

	@Override
	public String toString() {
		return Util.getObjectName(this) + "{" +
				"position=" + position.toString() +
				", dimensions=" + dimensions.toString() +
				'}';
	}

	public Vertex getPosition() {
		return position;
	}

	public void setPosition(Vertex position) {
		this.position = new Vertex(position);
	}

	public Dimension<Integer> getDimensions() {
		return dimensions;
	}

	public void setDimensions(Dimension<Integer> dimensions) {
		this.dimensions = new Dimension<>(dimensions);
	}

	public int getStroke() {
		return stroke_color;
	}

	public Shape setStroke(int stroke_color) {
		this.stroke_color = stroke_color;
		this.no_stroke = false;
		return this;
	}

	public Shape noStroke() {
		this.no_stroke = true;
		return this;
	}

	public int getFill() {
		return fill_color;
	}

	public Shape setFill(int fill_color) {
		this.fill_color = fill_color;
		this.no_fill = false;
		return this;
	}

	public Shape noFill() {
		this.no_fill = true;
		return this;
	}

	public Shape setLightBlock(int status) {
		this.light_block = status;
		return this;
	}

	public int getLightBlock() {
		return light_block;
	}
}
