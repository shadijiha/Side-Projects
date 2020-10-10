package rendererEngin;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import models.RawModel;
import models.TexturedModel;
import shaders.FlatColorShader;
import shaders.Shader;
import shaders.StaticShader;
import textures.ModelTexture;
import util.AvoidUsage;
import util.Color;
import util.Maths;

/**
 * 
 * @author shadi
 *
 */
public class Renderer {

	private static final float FOV = 70;
	private static final float NEAR_PLANE = 0.1f;
	private static final float FAR_PLANE = 1000f;

	private final Matrix4f projectionMatrix;
	private Camera camera;
	private final Loader loader;

	// Cache variables
	private Shader shader;
	private Shader customShader;
	private FlatColorShader flatColorShader;

	private CameraState flatColorShaderCameraState;
	private RawModel defaultRawModel;

	public Renderer(Camera camera, Loader loader) {
		projectionMatrix = new Matrix4f();
		createProjectionMatrix();

		this.camera = camera;
		this.loader = loader;

		this.shader = new StaticShader();
		this.shader.bind();
		this.shader.loadProjectionMatrix(projectionMatrix);
		this.shader.unbind();
	}

	public void clear() {
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClearColor(0, 0, 0, 1);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}

	public void shutdown() {
		clear();

		if (shader != null)
			shader.close();

		if (flatColorShader != null)
			flatColorShader.close();

		if (loader != null)
			loader.close();
	}

	@Deprecated
	private void render(RawModel model) {
		GL30.glBindVertexArray(model.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
	}

	@Deprecated
	private void render(TexturedModel model) {

		var raw = model.getModel();

		GL30.glBindVertexArray(raw.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getID());
		GL11.glDrawElements(GL11.GL_TRIANGLES, raw.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL30.glBindVertexArray(0);
	}

	public void render(Entity e, Shader shader) {

		shader.bind();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.loadViewMatrix(camera);

		var model = e.getModel();

		GL30.glBindVertexArray(model.getModel().getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);

		var transformMat = Maths.createTranformMatrix(e.getPositon(), e.getRotation(), e.getScale());

		shader.loadTransformMatrix(transformMat);

		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getID());
		GL11.glDrawElements(GL11.GL_TRIANGLES, model.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL30.glBindVertexArray(0);

		shader.unbind();
	}

	public void render(Entity e) {
		render(e, this.shader);
	}

	public void drawQuad(Vector3f position, Vector2f dimensions, Color c) {
		drawRotatedQuad(position, dimensions, new Vector3f(0, 0, 0), c);
	}

	@AvoidUsage(reason = "This function must be optomized before being used")
	public void drawQuad(Vector3f position, Vector2f dimensions, ModelTexture texture) {

		// TODO: change the position.z ADD DEPTH
		float[] vertices = { -0.5f, 0.5f, 0, -0.5f, -0.5f, 0, 0.5f, -0.5f, 0, 0.5f, 0.5f, 0f };

		float[] textureCoords = { 0, 0, 0, 1, 1, 1, 1, 0 };

		int[] indices = { 0, 1, 3, 3, 1, 2 };

		var model = new TexturedModel(loader.loadToVAO(vertices, textureCoords, indices), texture);

		var entity = new Entity(model, position, new Vector3f(0, 0, 0), new Vector3f(dimensions.x, dimensions.y, 0));
		shader.bind();
		shader.loadViewMatrix(camera);
		render(entity, shader);
		shader.unbind();
	}

	public void drawQuad(Vector3f position, Vector2f dimensions, Shader shader) {
		drawRotatedQuad(position, dimensions, new Vector3f(0, 0, 0), shader);
	}

	public void drawRotatedQuad(Vector3f position, Vector2f dimensions, Vector3f rotation, Color c) {

		float[] vertices = { -0.5f, 0.5f, 0, -0.5f, -0.5f, 0, 0.5f, -0.5f, 0, 0.5f, 0.5f, 0f };

		int[] indices = { 0, 1, 3, 3, 1, 2 };

		// Cache the color shader
		if (flatColorShader == null) {
			flatColorShader = new FlatColorShader();
			flatColorShader.bind();
			flatColorShader.loadProjectionMatrix(projectionMatrix);
		}

		flatColorShader.bind();

		// See if we have to calculate the view matrix or it is uneeded
		var state = new CameraState(camera);
		if (flatColorShaderCameraState == null || !flatColorShaderCameraState.equals(state)) {
			flatColorShader.loadViewMatrix(camera);
			flatColorShaderCameraState = state;
		}

		var transformMat = Maths.createTranformMatrix(position, rotation, new Vector3f(dimensions.x, dimensions.y, 0f));
		flatColorShader.loadTransformMatrix(transformMat);
		flatColorShader.loadColor(c.toVector4f());

		// Bind the VAO and render them
		// Cache them if necessary
		if (defaultRawModel == null)
			defaultRawModel = loader.loadToVAO(vertices, indices);
		render(defaultRawModel);
	}

	public void drawRotatedQuad(Vector3f position, Vector2f dimensions, Vector3f rotation, Shader shader) {

		float[] vertices = { -0.5f, 0.5f, 0, -0.5f, -0.5f, 0, 0.5f, -0.5f, 0, 0.5f, 0.5f, 0f };

		int[] indices = { 0, 1, 3, 3, 1, 2 };

		// Get the cached shader if it exists
		if (customShader != shader) {
			shader.bind();
			shader.loadProjectionMatrix(projectionMatrix);
			customShader = shader;
		}

		shader.bind();

		var state = new CameraState(camera);
		if (flatColorShaderCameraState == null || !flatColorShaderCameraState.equals(state)) {
			shader.loadViewMatrix(camera);
			flatColorShaderCameraState = state;
		}

		var transformMat = Maths.createTranformMatrix(position, rotation, new Vector3f(dimensions.x, dimensions.y, 0f));
		shader.loadTransformMatrix(transformMat);

		// Bind the VAO and render them
		// Cache them if necessary
		if (defaultRawModel == null)
			defaultRawModel = loader.loadToVAO(vertices, indices);
		render(defaultRawModel);

		shader.unbind();
	}

	public void setCamera(Camera c) {
		this.camera = c;
	}

	public void setDefaultShader(Shader s) {
		shader = s;
	}

	public Camera getCamera() {
		return camera;
	}

	private void createProjectionMatrix() {
		float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
		float yScale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio);
		float xScale = yScale / aspectRatio;
		float frustum_length = FAR_PLANE - NEAR_PLANE;

		projectionMatrix.m00 = xScale;
		projectionMatrix.m11 = yScale;
		projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
		projectionMatrix.m23 = -1;
		projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
		projectionMatrix.m33 = 0;
	}
}
