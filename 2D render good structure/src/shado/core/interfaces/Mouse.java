package shado.core.interfaces;

import shado.core.util.Vertex;

public interface Mouse {

	public static final Vertex position = new Vertex();
	public static final Vertex last_click = new Vertex();

	public static float x() {
		return position.x;
	}

	public static float y() {
		return position.y;
	}

	public static float lastClickX() {
		return last_click.x;
	}

	public static float lastClickY() {
		return last_click.y;
	}

	public static Vertex getLastClick() {
		return new Vertex(last_click);
	}

	public static Vertex getPosition() {
		return new Vertex(position);
	}

	/**
	 * Puts the last clicked to a really far away value
	 */
	public static void resetLastClicked() {
		last_click.x = -100000.0f;
		last_click.y = -100000.0f;
	}
}
