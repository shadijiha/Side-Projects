/**
 *
 */

package com.main;

import com.engin.*;
import com.engin.shapes.Rectangle;
import com.engin.shapes.Shape;
import com.engin.shapes.*;

import java.awt.*;
import java.util.List;
import java.util.*;

public class Enviroment extends Scene {

	public final static Enviroment singleton = new Enviroment();

	public static final int GROUND_LEVEL = 400;

	private List<Shape> shapes;


	private Enviroment() {
		super("Enviroment", -1);
	}

	/**
	 * Initializes the scene and its variables
	 *
	 * @param renderer The renderer that will handle the scene
	 */
	@Override
	public void init(Renderer renderer) {

		shapes = new ArrayList<>();

		var sky = new Rectangle(-renderer.getWidth() * 100, 0, renderer.getWidth() * 400, renderer.getHeight());
		sky.setFill(new Color(153, 217, 234));
		shapes.add(sky);

		var grass = new Rectangle(-renderer.getWidth() * 100, GROUND_LEVEL, renderer.getWidth() * 400, renderer.getHeight() - GROUND_LEVEL);
		grass.setFill(new Color(34, 177, 76));
		shapes.add(grass);

		var sun = new Circle(20, 20, 50);
		sun.setFill(Color.YELLOW);
		shapes.add(sun);
	}

	/**
	 * Updates the state of the scene
	 *
	 * @param dt The time between 2 frames in ms
	 */
	@Override
	public void update(float dt) {

	}

	/**
	 * Draws the scene content to the screen
	 *
	 * @param g The graphics that will handle the drawing
	 */
	@Override
	public void draw(Graphics g) {
		for (Shape s : shapes)
			s.draw(g);
	}

	public void moveObjects(float x) {
		for (Shape s : shapes)
			s.moveBy(x, 0);
	}
}
