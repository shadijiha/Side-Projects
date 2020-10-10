package enginTester;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import models.TexturedModel;
import rendererEngin.Loader;
import rendererEngin.Renderer;
import rendererEngin.Scene;
import shaders.Shader;
import shaders.StaticShader;
import textures.ModelTexture;

/**
 * 
 * @author shadi
 *
 */
public class ExampleScene extends Scene {

	private ModelTexture texture;
	private Entity entity;
	private StaticShader shader;
	private Shader positionShader;

	private Camera c2 = new Camera();
	private Camera defaultCam;

	private Vector3f rot;

	@Override
	public void init(Loader loader, Camera defaultCamera) {
		// TODO Auto-generated method stub
		shader = new StaticShader();

		this.defaultCam = defaultCamera;

		texture = new ModelTexture(loader.loadTexture("resources/Untitled.png"));

		float[] vertices = { -0.5f, 0.5f, 0, -0.5f, -0.5f, 0, 0.5f, -0.5f, 0, 0.5f, 0.5f, 0f };
		int[] indices = { 0, 1, 3, 3, 1, 2 };
		float[] textureCoords = { 0, 0, 0, 1, 1, 1, 1, 0 };

		var model = loader.loadToVAO(vertices, textureCoords, indices);
		var texture = new ModelTexture(loader.loadTexture("resources/Untitled.png"));
		var textModel = new TexturedModel(model, texture);

		entity = new Entity(textModel, new Vector3f(0, 0, -5), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1));

		// Load position shader
		positionShader = new Shader("resources/positionColor.glsl") {

			@Override
			protected void getAllUniformLocations() {
				// TODO Auto-generated method stub
				locationTransformMatrix = super.getUniformLocation("tranformMat");
				locationProjectionMat = super.getUniformLocation("projectionMat");
				locationViewMatrix = super.getUniformLocation("viewMatrix");
			}

			@Override
			protected void bindAttributes() {
				// TODO Auto-generated method stub
				bindPosition();
			}
		};

		rot = new Vector3f(0, 0, 0);
	}

	@Override
	public void update(float dt) {
		// TODO Auto-generated method stub
		Display.setTitle(1 / dt + "");

		c2.rotate(dt * 5f, 0, 0);
		defaultCam.listenToWASD(dt);

		rot.z += dt * 100;
	}

	@Override
	public void draw(Renderer renderer) {
		// TODO Auto-generated method stub
//		renderer.drawQuad(new Vector3f(0.25f, 1.0f, -5), new Vector2f(0.5f, 0.5f), Color.RED);
//		renderer.drawQuad(new Vector3f(-1f, 1.0f, -5), new Vector2f(1f, 1f), texture);
//		renderer.drawRotatedQuad(new Vector3f(-0.5f, 1.0f, -5), new Vector2f(1f, 1f), new Vector3f(0, 45, 0),
//				Color.GREEN);
//		renderer.render(entity);

//		for (float i = -1.0f; i < 2.0f; i += 0.5f) {
//			for (float j = -1.0f; j < 2.0f; j += 0.5f) {
//				renderer.drawQuad(new Vector3f(i, j, 0), new Vector2f(0.4f, 0.4f), positionShader);
//
//			}
//		}

		for (float i = -1.0f; i < 20.0f; i += 0.5f) {
			for (float j = -1.0f; j < 20.0f; j += 0.5f) {
				// var color = new Color((float) Math.random(), (float) Math.random(), (float)
				// Math.random());
				renderer.drawRotatedQuad(new Vector3f(i, j, 0), new Vector2f(0.5f, 0.5f), rot, positionShader);
			}
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
			renderer.setCamera(c2);
		else
			renderer.setCamera(defaultCam);
	}

}
