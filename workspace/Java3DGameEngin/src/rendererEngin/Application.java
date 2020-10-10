/**
 *
 */
package rendererEngin;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import org.lwjgl.opengl.Display;

import entities.Camera;

/**
 * @author shadi
 *
 */
public class Application implements Runnable {

	private List<Scene> scenes = new ArrayList<>();
	private Queue<Scene> submitionQueue = new ArrayDeque<>();

	private Loader loader;
	private Camera defaultCamera;
	private Renderer renderer;

	// FPS calculating stuff
	long nextSecond = System.currentTimeMillis() + 1000;
	int framesInLastSecond = 0;
	int framesInCurrentSecond = 0;

	public Application() {
		loader = null;
		defaultCamera = null;
		renderer = null;
	}

	/**
	 * Starts the game loop
	 */
	public void run() {

		DisplayManager.createDisplay();

		loader = new Loader();
		defaultCamera = new Camera();
		renderer = new Renderer(defaultCamera, loader);

		while (!Display.isCloseRequested()) {

			// Clear the screen
			renderer.clear();

			// Move scenes from the queue to the List
			while (submitionQueue.size() != 0) {
				var next = submitionQueue.poll();
				if (next == null)
					continue;

				next.init(loader, defaultCamera);
				scenes.add(next);
				sortScenes();
			}

			// Update defaults
			defaultCamera = renderer.getCamera();

			// Update
			for (Scene scene : scenes) {

				var delta = 1f / framesInLastSecond;
				if (Double.isInfinite(delta))
					delta = 0;

				scene.update(delta); // TODO: Change this shit
			}

			// Draw
			for (Scene scene : scenes) {
				if (!scene.isHidden())
					scene.draw(renderer);
			}

			// Update display
			DisplayManager.updateDisplay();

			// Keep track of time for FPS Calculation
			long currentTime = System.currentTimeMillis();
			if (currentTime > nextSecond) {
				nextSecond += 1000;
				framesInLastSecond = framesInCurrentSecond;
				framesInCurrentSecond = 0;
			}
			framesInCurrentSecond++;

			// Don't starve the garbage collector
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		renderer.shutdown();
		loader.close();

		DisplayManager.closeDisplay();
	}

	/**
	 * Submit a scene to be drawn to the screen
	 *
	 * @param scene The scene to draw
	 */
	public final void submit(final Scene scene) {

		// Put the submissions in a queue to avoid creating scenes without OpenGL
		// context
		submitionQueue.add(scene);

//		scene.init(loader, defaultCamera);
//		scenes.add(scene);
//		sortScenes();
	}

	/**
	 * Submit a scene to be drawn to the screen with a specific Z-index
	 *
	 * @param scene  The scene to draw
	 * @param zIndex The desired z index (smaller z-index = will be drawn earlier)
	 */
	public final void submit(final Scene scene, final int zIndex) {
		scene.zIndex = zIndex;
		submit(scene);
	}

	/**
	 * Removes a scene from the scene list by its ID
	 *
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
	 *
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

	public final void setTitle(String title) {
		if (title != Display.getTitle() && !Display.getTitle().equals(title))
			Display.setTitle(title);
	}

	public final int getFPS() {
		return framesInLastSecond;
	}

	/**
	 * Sorts the scenes by there Z-index
	 */
	private void sortScenes() {
		scenes.sort((Scene obj1, Scene obj2) -> {
			return Integer.compare(obj1.getZindex(), obj2.getZindex());
		});
	}
}
