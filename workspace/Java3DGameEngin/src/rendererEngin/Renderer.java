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
import shaders.StaticShader;
import textures.ModelTexture;
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
	private final StaticShader shader;

	public Renderer(Camera camera, Loader loader, StaticShader shader) {
		projectionMatrix = new Matrix4f();
		createProjectionMatrix();

		shader.bind();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.unbind();

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

	public void render(RawModel model) {
		GL30.glBindVertexArray(model.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
	}

	public void render(TexturedModel model) {

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

	public void render(Entity e, StaticShader shader) {

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
	}

	public void drawQuad(Vector3f position, Vector2f dimensions, Color c) {

		// TODO: change the position.z ADD DEPTH
		float[] vertices = { position.x, position.y, position.z, position.x, position.y + dimensions.y, position.z,
				position.x + dimensions.x, position.y + dimensions.y, position.z, position.x + dimensions.x, position.y,
				position.z };

		int[] indices = { 0, 1, 3, 3, 1, 2 };

		var colorShader = new FlatColorShader();
		colorShader.bind();
		colorShader.loadProjectionMatrix(projectionMatrix);
		colorShader.loadViewMatrix(camera);

		var transformMat = Maths.createTranformMatrix(position, new Vector3f(0, 0, 0),
				new Vector3f(dimensions.x, dimensions.y, 0f));
		colorShader.loadTransformMatrix(transformMat);
		colorShader.loadColor(c.toVector4f());

		var model = loader.loadToVAO(vertices, indices);
		render(model);

		colorShader.unbind();
	}

	public void drawQuad(Vector3f position, Vector2f dimensions, ModelTexture texture) {

		// TODO: change the position.z ADD DEPTH
		float[] vertices = { position.x, position.y, position.z, position.x, position.y + dimensions.y, position.z,
				position.x + dimensions.x, position.y + dimensions.y, position.z, position.x + dimensions.x, position.y,
				position.z };

		int[] indices = { 0, 1, 3, 3, 1, 2 };

		// Update Shader
		this.shader.bind();
		this.shader.loadViewMatrix(camera);

		var transformMat = Maths.createTranformMatrix(position, new Vector3f(0, 0, 0),
				new Vector3f(dimensions.x, dimensions.y, 0f));
		this.shader.loadTransformMatrix(transformMat);

		var texturedModel = new TexturedModel(loader.loadToVAO(vertices, indices), texture);
		render(texturedModel);

		this.shader.unbind();
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