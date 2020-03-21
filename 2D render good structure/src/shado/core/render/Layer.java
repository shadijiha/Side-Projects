/**
 *
 */

package shado.core.render;

import shado.core.geometry.Shape;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Layer implements Iterable<Shape> {

	private String name;
	private int id;
	private boolean hidden;

	private List<Shape> buffer;

	public Layer(String name) {
		this.name = name;
		this.id = (int) (Math.random() * Integer.MAX_VALUE);
		this.buffer = new ArrayList<>();
		this.hidden = false;
	}

	/**
	 * Adds a shape to the layer
	 * @param shape The shape to Add
	 */
	public void add(Shape shape) {
		buffer.add(shape);
	}

	/**
	 * @return Returns the name of the Layer
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return Returns the ID of the Layer
	 */
	public int getID() {
		return id;
	}

	public boolean isHidden() {
		return hidden;
	}

	// Setters

	/**
	 * Hide the layer to NOT render it to the screen
	 */
	public void hide() {
		hidden = true;
	}

	/**
	 * Show the layer to render it to the screen
	 */
	public void show() {
		hidden = false;
	}

	// Make the layer behave as an Iterator

	/**
	 * Returns an iterator over elements of type {@code T}.
	 *
	 * @return an Iterator.
	 */
	@Override
	public Iterator<Shape> iterator() {
		return buffer.iterator();
	}
}
