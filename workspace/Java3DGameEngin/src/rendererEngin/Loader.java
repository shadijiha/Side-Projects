
package rendererEngin;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import models.RawModel;

/**
 * This class loads the information about the Model3D into memory by storing
 * information about VAO
 * 
 * @author shadi
 *
 * @see RawModel.java
 */
public final class Loader {

	private List<Integer> vaos = new ArrayList<>();
	private List<Integer> vbos = new ArrayList<>();
	private List<Integer> textures = new ArrayList<Integer>();

	public RawModel loadToVAO(float[] positions, int[] indices) {
		int vaoID = createVAO();

		bindIndexBuffer(indices);
		storeDataInAttribList(0, 3, positions);
		unbindVAO();

		return new RawModel(vaoID, indices.length);
	}

	public RawModel loadToVAO(float[] positions, float[] textureCoords, int[] indices) {
		int vaoID = createVAO();

		bindIndexBuffer(indices);
		storeDataInAttribList(0, 3, positions);
		storeDataInAttribList(1, 2, textureCoords);
		unbindVAO();

		return new RawModel(vaoID, indices.length);
	}

	/**
	 * Loads a texture to memory
	 * 
	 * @param filename The filename
	 * @param format   The format "PNG", "JPG", "BMP", etc
	 * @return Returns the texture ID
	 */
	public int loadTexture(String filepath, String format) {
		Texture texture = null;

		try {
			texture = TextureLoader.getTexture(format, new FileInputStream(filepath));
		} catch (IOException e) {
			e.printStackTrace();
		}

		int textureID = texture.getTextureID();
		textures.add(textureID);

		return textureID;
	}

	/**
	 * Loads a PNG texture to memory
	 * 
	 * @param filename The filename
	 * @return Returns the texture ID
	 */
	public int loadTexture(String filepath) {
		return loadTexture(filepath, "PNG");
	}

	public void close() {
		for (var vao : vaos)
			GL30.glDeleteVertexArrays(vao);

		for (var vbo : vbos)
			GL15.glDeleteBuffers(vbo);

		for (var t : textures)
			GL11.glDeleteTextures(t);
	}

	private int createVAO() {
		int vaoID = GL30.glGenVertexArrays();
		vaos.add(vaoID);

		GL30.glBindVertexArray(vaoID);
		return vaoID;
	}

	/**
	 * Stores Data in an attribute buffer
	 * 
	 * @param attribNumber    The attribute number
	 * @param coordinatesSize The coordinates size. Example: {x,y,z} = 3 ; {x,y} = 2
	 * @param data            The data to store
	 */
	private void storeDataInAttribList(int attribNumber, int coordinatesSize, float[] data) {
		int vboID = GL15.glGenBuffers();
		vbos.add(vboID);

		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);

		var buffer = storeDataInFloatBuffer(data);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(attribNumber, coordinatesSize, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}

	private void unbindVAO() {
		GL30.glBindVertexArray(0);
	}

	private void bindIndexBuffer(int[] indices) {
		int vboId = GL15.glGenBuffers();
		vbos.add(vboId);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboId);

		var buffer = storeDataInIntBuffer(indices);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);

	}

	private IntBuffer storeDataInIntBuffer(int[] data) {
		var buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();

		return buffer;
	}

	private FloatBuffer storeDataInFloatBuffer(float[] data) {
		var buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();

		return buffer;
	}
}
