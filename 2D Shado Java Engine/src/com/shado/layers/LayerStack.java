/**
 *
 */

package com.shado.layers;

import com.shado.core.Renderer2D;

import java.util.ArrayList;
import java.util.List;

public class LayerStack {

	private List<Layer<?>> layers;

	public LayerStack(int intial_size) {
		layers = new ArrayList<Layer<?>>(intial_size);
	}

	public LayerStack() {
		layers = new ArrayList<Layer<?>>();
	}

	public void submit(Layer<?> layer) {
		layers.add(layer);
	}

	public void render(Renderer2D r) {

		// TODO:: make this parallel
		for (Layer<?> layer : layers) {
			for (var object : layer.objects_to_render) {
				object.draw(r);
			}
		}
	}
}
