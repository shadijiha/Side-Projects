/**
 *
 */
package com.engin.components;

import com.engin.*;
import com.engin.shapes.Rectangle;
import com.engin.shapes.Shape;

import java.awt.*;

public final class MeshRenderer extends EntityComponent {
	public final Transform transform;
	public Shape shape;

	public MeshRenderer(GameObject obj) {
		super(obj);

		// Check if it has a transform
		transform = gameObject.<Transform>getComponent();
		if (transform == null)
			throw new RuntimeException("An object must contain a transform before a Mesh renderer");

		this.shape = new Rectangle((int) transform.position.getX(), (int) transform.position.getY(), (int) transform.scale.getX(), (int) transform.scale.getY());
	}

	public void draw(Graphics g) {
		var g2d = (Graphics2D) g;

		var old = g2d.getTransform();

		g2d.rotate(Math.toRadians(transform.rotation));

		this.shape.moveTo(transform.position);
		this.shape.setDimension((int) transform.scale.getX(), (int) transform.scale.getY());
		this.shape.draw(g2d);

		g2d.setTransform(old);
	}

	public void setShape(final Shape newShape) {
		this.shape = newShape;
	}

	public Shape getShape() {
		return shape;
	}
}
