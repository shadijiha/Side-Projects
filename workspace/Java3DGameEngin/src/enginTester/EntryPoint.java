/**
 * 
 */
package enginTester;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import models.TexturedModel;
import rendererEngin.DisplayManager;
import rendererEngin.Loader;
import rendererEngin.Renderer;
import rendererEngin.Scene;
import shaders.StaticShader;
import textures.ModelTexture;

/**
 * @author shadi
 *
 */
public final class EntryPoint {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		DisplayManager.createDisplay();

		var loader = new Loader();
		var shader = new StaticShader();
		var camera = new Camera();
		var renderer = new Renderer(camera, loader, shader);

		float[] vertices = { -0.5f, 0.5f, 0, -0.5f, -0.5f, 0, 0.5f, -0.5f, 0, 0.5f, 0.5f, 0f };
		int[] indices = { 0, 1, 3, 3, 1, 2 };
		float[] textureCoords = { 0, 0, 0, 1, 1, 1, 1, 0 };

		var model = loader.loadToVAO(vertices, textureCoords, indices);
		var texture = new ModelTexture(loader.loadTexture("resources/Untitled.png"));
		var textModel = new TexturedModel(model, texture);

		var entity = new Entity(textModel, new Vector3f(0, 0, -5), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1));

		// Initialize application
		Application.init();

		while (!Display.isCloseRequested()) {

			renderer.clear();

			// Initialize unintialized scenes
			var scenes = Scene.allScenes();
			for (Scene scene : scenes) {
				if (!scene.hasInitialized()) {
					scene.init(loader);
					scene.inited();
				}
			}

			// Update
			for (Scene scene : scenes) {
				scene.update(1 / DisplayManager.FPS_CAP); // TODO: Change this shit
			}

			// entity.increasePosition(0, 0, -0.05f);
			entity.increaseRotation(0.5f, 0, 0);
			shader.loadViewMatrix(camera);
			camera.addWASDListener();

			// Draw
			shader.bind();
			renderer.render(entity, shader);
			shader.unbind();

			for (Scene scene : scenes) {
				scene.draw(renderer);
			}

			// Put the game logic here
			// TODO: add scene System

			// Update display
			DisplayManager.updateDisplay();
		}

		shader.close();
		loader.close();

		DisplayManager.closeDisplay();
	}

}