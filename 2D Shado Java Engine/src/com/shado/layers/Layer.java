/**
 *
 */

package com.shado.layers;

import com.shado.intefaces.*;

import java.util.*;

public class Layer<T extends Drawable> implements Hidable {

	private boolean hidden;
	private String name;
	List<T> objects_to_render;

	public Layer(String name) {
		this.name = name;
		this.hidden = false;
		this.objects_to_render = new ArrayList<T>();
	}

	public void add(T object) {
		objects_to_render.add(object);
	}

	public void remove(T object) {
		objects_to_render.remove(object);
	}

	/**
	 * Returns if the calling object is hidden or not
	 */
	@Override
	public boolean isHidden() {
		return hidden;
	}

	/**
	 * Allows the calling object to be sent to the renderer
	 */
	@Override
	public void show() {
		hidden = false;
	}

	/**
	 * Prevents the calling object from being sent to the renderer
	 */
	@Override
	public void hide() {
		hidden = true;
	}

	public List<T> objectsToRender() {
		return objects_to_render;
	}
}
