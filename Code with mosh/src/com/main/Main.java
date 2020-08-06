package com.main;

import com.engin.Renderer;
import com.engin.*;
import com.engin.components.*;
import com.engin.math.Vector;

import javax.swing.*;
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
			JOptionPane.showMessageDialog(null, e.toString(), "Runtime Error!", JOptionPane.ERROR_MESSAGE);
		}
	}
}

class DebugScene extends Scene {

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
		object.<Transform>getComponent().scale = new Vector(100, 100);
		object.addComponent(new MeshRenderer(object));

		object.addComponent(new Script(object) {
			@Override
			public void update(float dt) {
				var transform = (Transform) object.getComponent(Transform.class);
				transform.position = new ImmutableVector(4, 1);
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
			var script = object.<Script>getComponent();
			if (script != null)
				script.update(dt);
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
			var meshRenderer = object.<MeshRenderer>getComponent();
			if (meshRenderer != null)
				meshRenderer.draw(g);
		}
	}
}
