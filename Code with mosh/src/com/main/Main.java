package com.main;

import com.engin.GameObject;
import com.engin.Renderer;
import com.engin.Scene;
import com.engin.components.MeshRenderer;
import com.engin.components.Script;
import com.engin.components.Transform;
import com.engin.math.Vector;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

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

		final GameObject object = GameObject.instantiate();
		object.<Transform>getComponent().scale = new Vector(100, 100);
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
			if (script != null) {
				script.run(dt);
			}

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