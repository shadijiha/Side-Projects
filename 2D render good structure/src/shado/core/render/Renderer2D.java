/**
 *
 */

package shado.core.render;

import javafx.scene.canvas.Canvas;
import shado.core.geometry.Shape;
import shado.core.interfaces.Mouse;

import java.util.ArrayList;
import java.util.List;

public class Renderer2D {

	private List<Layer> layer_stack = new ArrayList<>();
	private Canvas canvas;
	private Camera camera;

	public Renderer2D(Canvas c, Camera camera) {
		canvas = c;

		this.camera = camera;

		// Register mouse position
		canvas.setOnMouseMoved(event -> {
			Mouse.position.x = (float) (event.getX() / c.getWidth());
			Mouse.position.y = (float) (event.getY() / c.getHeight());
		});

		canvas.setOnMouseClicked(event -> {
			Mouse.last_click.x = (float) (event.getX() / c.getWidth());
			Mouse.last_click.y = (float) (event.getY() / c.getHeight());
		});
	}

	/**
	 * Render all the objects in the Queue to the Screen
	 */
	public void render() {

	public void render() {

		// TODO: Make this parallel
		for (Layer layer : layer_stack) {

			// Don't draw if the layer is hidden
			if (layer.isHidden())
				continue;

			// Draw all shapes of the layer
			for (Shape shape : layer) {

				// Don't draw if the Shape is hidden
				if (shape.isHidden())
					continue;

				// TODO:: Check for events
				// Handle click event
				if (shape.clickEvent() != null) {
					if (shape.collides(Mouse.getLastClick())) {
						shape.clickEvent().accept(shape);
						Mouse.resetLastClicked();
					}
				}

				// Handle Hover event
				if (shape.hoverEvent() != null) {
					if (shape.collides(Mouse.getPosition())) {
						shape.hoverEvent().accept(shape);
						shape.consumeOutEvent(false);
					}
				}

				// Handle mouse out event
				if (shape.outEvent() != null) {
					if (!shape.collides(Mouse.getPosition()) && !shape.outEventConsumed()) {
						shape.outEvent().accept(shape);
						shape.consumeOutEvent(true);
					}
				}

				var matrix = camera.getViewProjectionMatrix();

				Matrix transform = Matrix.generateIdentity(4, 4);
				Vec4 newPos = (matrix.multiply(transform).multiply(new Vec4(shape.getPosition(), 1.0f))).toVector4D();

				shape.setPosition(newPos.to3D());
				shape.setPosition((float) matrix.getData(0, 3), (float) matrix.getData(1, 3));
				//shape.setPosition(transform.getData(), newPos.y);
				//shape.setDimensions((float) newDimension3D.x, (float) newDimension3D.y);
				shape.draw(canvas);
			}
		}
	}
	}

	public void clear() {
		canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
	}

	/**
	 * Submit a shape to the render Queue
	 * @param layer The shape to add
	 */
	public void submit(Layer layer) {
		layer_stack.add(layer);
	}

	public Canvas getCanvas() {
		return canvas;
	}
}
