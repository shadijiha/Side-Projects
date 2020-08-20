package com.main;

import com.engin.GameObject;
import com.engin.Renderer;
import com.engin.Scene;
import com.engin.components.MeshRenderer;
import com.engin.components.Script;
import com.engin.components.Transform;
import com.engin.math.Vector;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

class ExampleScene extends Scene {

	private static final long serialVersionUID = 3863558974487580979L;
	private Renderer renderer;

	List<GameObject> gameObjects;

	public ExampleScene() {
		super("Debug scene");
		gameObjects = new ArrayList<>();
	}

	/**
	 * Initializes the scene and its variables
	 *
	 * @param renderer The renderer that will handle the scene
	 */
	@Override
	public void init(Renderer renderer) {
		this.renderer = renderer;

		GameObject object = GameObject.instantiate();
		((Transform) object.getComponent(Transform.class)).scale = new Vector(100, 100);
		object.addComponent(new MeshRenderer(object));

		object.addComponent(new Script(object) {
			@Override
			public void run(float dt) {
				var transform = (Transform) object.getComponent(Transform.class);
				transform.position.x += 1;
			}
		});

		gameObjects.add(object);
	}

	/**
	 * Updates the state of the scene
	 *
	 * @param dt The time between 2 frames in ms
	 */
	@Override
	public void update(final float dt) {

		for (var object : gameObjects) {
			Script script = (Script) object.getComponent(Script.class);
			if (script != null)
				script.run(dt);
		}
		renderer.getWindow().setTitle((int) (1f / dt) + "");
	}

	/**
	 * Draws the scene content to the screen
	 *
	 * @param g The graphics that will handle the drawing
	 */
	@Override
	public void draw(final Graphics g) {

		for (var object : gameObjects) {
			var meshRenderer = (MeshRenderer) object.getComponent(MeshRenderer.class);
			if (meshRenderer != null)
				meshRenderer.draw(g);
		}
	}

	/**
	 * Invoked when the mouse button has been clicked (pressed and released) on a
	 * component.
	 *
	 * @param e the event to be processed
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		//AudioManager.submit("kayle-morgana-the-righteous-the-fallen-login-screen-league-of-legends.wav");
	}
}

public class Example {

	public static void main(String[] args) {
		// write your code here
		Renderer renderer = new Renderer();
		renderer.start();
		renderer.submit(new ExampleScene());
		renderer.submit(new UITest());
	}
}

