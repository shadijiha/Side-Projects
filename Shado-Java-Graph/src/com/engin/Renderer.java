/**
 *
 */
package com.engin;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.*;

public final class Renderer extends JPanel {

	private final static float FPS = 120.0f;

	private final JFrame frame;
	private final List<Scene> scenes;

	long startTime = 0;
	long endTime = 0;
	long framerate = 1000 / 60;
	// time the frame began. Edit the second value (60) to change the prefered FPS (i.e. change to 50 for 50 fps)
	long frameStart;
	// number of frames counted this second
	long frameCount = 0;
	// time elapsed during one frame
	long elapsedTime;
	// accumulates elapsed time over multiple frames
	long totalElapsedTime = 0;
	// the actual calculated framerate reporte
	double reportedFramerate = 0;

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

	public final void start() {
		frame.setVisible(true);

		// Sort the scenes by their Z buffer
		sortScenes();

		// Start scenes scenes
//		scenes.stream().forEach(scene -> {
//			scene.init();
//		});

		drawThread.start();
		updateThread.start();
	}

	private void updateComponent() {
		// Render scenes
		scenes.stream().forEach(scene -> {

			// Only update if there's no division by 0
			var temp = 1.0f / (float) reportedFramerate;
			if (temp != Double.POSITIVE_INFINITY)
				scene.update(temp);
		});

		try {
			Thread.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public final void paintComponent(final Graphics g) {

		frameStart = System.currentTimeMillis();

		// Clear screen
		g.clearRect(0, 0, frame.getWidth(), frame.getHeight());

		// Render scenes
		scenes.parallelStream().forEachOrdered(scene -> {
			scene.draw(g);
		});

		// Clear screen
		repaint();

		// calculate the time it took to render the frame
		elapsedTime = System.currentTimeMillis() - frameStart;
		// sync the framerate
		try {
			// make sure framerate milliseconds have passed this frame
			if (elapsedTime < framerate) {
				Thread.sleep(framerate - elapsedTime);
			} else {
				// don't starve the garbage collector
				Thread.sleep(5);
			}
		} catch (InterruptedException e) {
			return;
		}
		++frameCount;
		totalElapsedTime += (System.currentTimeMillis() - frameStart);
		if (totalElapsedTime > 1000) {
			reportedFramerate = (long) ((double) frameCount
					/ (double) totalElapsedTime * 1000.0);

			frameCount = 0;
			totalElapsedTime = 0;
		}

	}

	/**
	 * Submit a scene to be drawn to the screen
	 * @param scene The scene to draw
	 */
	public final void submit(final Scene scene) {
		scene.init(this);

		frame.addKeyListener(scene);
		frame.addMouseListener(scene);
		frame.addMouseMotionListener(scene);
		frame.addMouseWheelListener(scene);

		scenes.add(scene);
		sortScenes();
	}

	/**
	 * Submit a scene to be drawn to the screen with a specific Z-index
	 * @param scene The scene to draw
	 * @param zIndex The desired z index (smaller z-index = will be drawn earlier)
	 */
	public final void submit(final Scene scene, final int zIndex) {
		scene.zIndex = zIndex;
		submit(scene);
	}

	/**
	 * Removes a scene from the scene list by its ID
	 * @param sceneID The ID of the scene to remove
	 * @return Returns the removed scene
	 */
	public final Scene remove(final long sceneID) {
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

	/**
	 * Removes a scene from the scene list by its name
	 * @param sceneName The name of the scene to remove
	 * @return Returns the removed scene
	 */
	public final Scene remove(final String sceneName) {
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

	/**
	 * Sorts the scenes by there Z-index
	 */
	private void sortScenes() {
		scenes.sort((Scene obj1, Scene obj2) -> {
			return Integer.compare(obj1.getzIndex(), obj2.getzIndex());
		});
	}

	public final JFrame getWindow() {
		return frame;
	}
}
