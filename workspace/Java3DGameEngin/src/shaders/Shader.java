package shaders;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.Scanner;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

/**
 * 
 * @author shadi
 *
 */
public abstract class Shader {

	private static final FloatBuffer MATRIX_BUFFER = BufferUtils.createFloatBuffer(16);

	private int programID;
	private int vertexShaderId;
	private int fragmentShaderID;

	public Shader(String vertexFile, String fragmentFile) {

		vertexShaderId = loadShaderFile(vertexFile, GL20.GL_VERTEX_SHADER);
		fragmentShaderID = loadShaderFile(fragmentFile, GL20.GL_FRAGMENT_SHADER);

		programID = GL20.glCreateProgram();
		GL20.glAttachShader(programID, vertexShaderId);
		GL20.glAttachShader(programID, fragmentShaderID);
		bindAttributes();
		GL20.glLinkProgram(programID);
		GL20.glValidateProgram(programID);

		getAllUniformLocations();

	}

	public Shader(String vertexAndFragmentFile) {

		var loaded = loadFromFile(vertexAndFragmentFile);

		vertexShaderId = compileAndBindShader(loaded[0], GL20.GL_VERTEX_SHADER);
		fragmentShaderID = compileAndBindShader(loaded[1], GL20.GL_FRAGMENT_SHADER);

		programID = GL20.glCreateProgram();
		GL20.glAttachShader(programID, vertexShaderId);
		GL20.glAttachShader(programID, fragmentShaderID);
		bindAttributes();
		GL20.glLinkProgram(programID);
		GL20.glValidateProgram(programID);
		getAllUniformLocations();
	}

	public void bind() {
		GL20.glUseProgram(programID);
	}

	public void unbind() {
		GL20.glUseProgram(0);
	}

	public void close() {
		unbind();
		GL20.glDetachShader(programID, vertexShaderId);
		GL20.glDetachShader(programID, fragmentShaderID);
		GL20.glDeleteShader(vertexShaderId);
		GL20.glDeleteShader(fragmentShaderID);
		GL20.glDeleteProgram(programID);
	}

	protected int getUniformLocation(String uniformName) {
		return GL20.glGetUniformLocation(programID, uniformName);
	}

	protected abstract void getAllUniformLocations();

	protected abstract void bindAttributes();

	public void loadFloat(int location, float value) {
		GL20.glUniform1f(location, value);
	}

	public void loadVector3f(int location, Vector3f vector) {
		GL20.glUniform3f(location, vector.x, vector.y, vector.z);
	}

	public void loadVector4f(int location, Vector4f vector) {
		GL20.glUniform4f(location, vector.x, vector.y, vector.z, vector.w);
	}

	public void loadBoolean(int location, boolean value) {
		float toLoad = value ? 1 : 0;
		GL20.glUniform1f(location, toLoad);
	}

	public void loadMatrix(int location, Matrix4f matrix) {
		matrix.store(MATRIX_BUFFER);
		MATRIX_BUFFER.flip();
		GL20.glUniformMatrix4(location, false, MATRIX_BUFFER);
	}

	protected void bindAttribute(int attribute, String variableName) {
		GL20.glBindAttribLocation(programID, attribute, variableName);
	}

	private static int loadShaderFile(String file, int type) {

		StringBuilder shader = new StringBuilder();

		try {
			Scanner scanner = new Scanner(new FileInputStream(file));

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine() + "\n";
				shader.append(line);
			}

			scanner.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		int shaderID = GL20.glCreateShader(type);
		GL20.glShaderSource(shaderID, shader);
		GL20.glCompileShader(shaderID);
		if (GL20.glGetShader(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
			System.out.println(GL20.glGetShaderInfoLog(shaderID, 500));
			System.out.println("Could not compile shader!");
			System.exit(-1);
		}

		return shaderID;

	}

	private static int compileAndBindShader(String shader, int type) {
		int shaderID = GL20.glCreateShader(type);
		GL20.glShaderSource(shaderID, shader);
		GL20.glCompileShader(shaderID);
		if (GL20.glGetShader(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
			System.out.println(GL20.glGetShaderInfoLog(shaderID, 500));
			System.out.println("Could not compile shader!");
			System.exit(-1);
		}

		return shaderID;
	}

	/**
	 * Loads a single file containing both Vertex and Fragment Shaders Declared by
	 * the "#type SOMETHING"
	 * 
	 * @param file The path to that file
	 * @return Returns a string array containing the Vertex and Fragment Shader
	 *         seperated
	 */
	private static String[] loadFromFile(String file) {

		StringBuilder vertexShader = new StringBuilder();
		StringBuilder fragmentShader = new StringBuilder();

		int type = 0;

		try {

			Scanner scanner = new Scanner(new FileInputStream(file));

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();

				// Swap depending on comments "#type vertex" OR "#type fragment"
				if (line.startsWith("#")) {
					if (line.contains("type") && line.contains("vertex")) {
						type = 0;
						continue;
					} else if (line.contains("type") && line.contains("fragment")) {
						type = 1;
						continue;
					}

				}

				if (type == 0) {
					vertexShader.append(line).append("\n");
				} else if (type == 1) {
					fragmentShader.append(line).append("\n");
				}

			}

			scanner.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return new String[] { vertexShader.toString(), fragmentShader.toString() };
	}

}
