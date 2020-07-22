/**
 *
 */
package com.engin;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Renderer extends JPanel {

	private final static float FPS = 120.0f;

	private final JFrame frame;
	private final List<Scene> scenes;

	private volatile long startTime;
	private volatile long endTime;
	private volatile long deltaTime;

	private final Thread drawThread;
	private final Thread updateThread;

	public Renderer(int width, int height) {
		scenes = new ArrayList<>();

		frame = new JFrame("Renderer");
		frame.setPreferredSize(new Dimension(width, height));
		frame.add(this);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		drawThread = new Thread(() -> paintComponent(this.getGraphics()));
		updateThread = new Thread(() -> {
			while (true) {
				updateComponent();
			}
		});
	}

	public Renderer() {
		this(1280, 720);
	}

	public void start() {
		frame.setVisible(true);

		startTime = System.currentTimeMillis();
		endTime = System.currentTimeMillis();
		deltaTime = endTime - startTime;

		// Sort the scenes by their Z buffer
		sortScenes();

		// Start scenes scenes
//		scenes.stream().forEach(scene -> {
//			scene.init(this);
//		});

		drawThread.start();
		updateThread.start();
	}

	public void updateComponent() {
		// Render scenes
		scenes.stream().forEach(scene -> {
			scene.update(deltaTime);
		});

		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void paintComponent(Graphics g) {

		startTime = System.currentTimeMillis();

		// Clear screen
		g.clearRect(0, 0, frame.getWidth(), frame.getHeight());

		// Render scenes
		for (final Scene scene : scenes)
			scene.draw(g);

		// Clear screen
		repaint();

		endTime = System.currentTimeMillis();
		deltaTime = endTime - startTime;

		try {
			Thread.sleep((long) (1000 / FPS));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void submit(Scene scene) {
		scene.init(this);
		frame.addKeyListener(scene);
		frame.addMouseListener(scene);
		frame.addMouseMotionListener(scene);
		scenes.add(scene);
		sortScenes();
	}

	public Scene remove(long sceneID) {
		Scene val = null;
		for (int i = 0; i < scenes.size(); i++) {
			if (scenes.get(i).getId() == sceneID) {
				val = scenes.get(i);
				scenes.remove(i);
				break;
			}
		}

		return val;
	}

	public Scene remove(String sceneName) {
		Scene val = null;
		for (int i = 0; i < scenes.size(); i++) {
			if (scenes.get(i).getName().equals(sceneName)) {
				val = scenes.get(i);
				scenes.remove(i);
				break;
			}
		}

		return val;
	}

	private void sortScenes() {
		scenes.sort((Scene obj1, Scene obj2) -> {
			return Integer.compare(obj1.getzIndex(), obj2.getzIndex());
		});
	}

	public JFrame getWindow() {
		return frame;
	}
}
