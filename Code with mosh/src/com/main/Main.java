package com.main;

import com.engin.*;
import com.engin.components.*;
import com.engin.logger.*;
import com.engin.math.Vector;

import java.awt.*;
import java.util.List;
import java.util.*;

public class Main {

	public static void main(String[] args) {
		// write your code here

		try {
			Renderer renderer = new Renderer();
			renderer.start();

			renderer.submit(new DebugScene());
		} catch (Exception e) {
			Debug.error(e);
		}
	}
}

class DebugScene extends Scene {

	private static final long serialVersionUID = 3863558974487580979L;
	private Renderer renderer;

	List<GameObject> gameObjects;

	public DebugScene() {
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
	public void update(float dt) {

		for (var object : gameObjects) {
			var script = (Script) object.getComponent(Script.class);
			if (script != null)
				script.run(dt);
		}
	}

	/**
	 * Draws the scene content to the screen
	 *
	 * @param g The graphics that will handle the drawing
	 */
	@Override
	public void draw(Graphics g) {

		for (var object : gameObjects) {
			var meshRenderer = (MeshRenderer) object.getComponent(MeshRenderer.class);
			if (meshRenderer != null)
				meshRenderer.draw(g);
		}
	}
}
