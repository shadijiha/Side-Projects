/**
 * 
 */
package shaders;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector4f;

import entities.Camera;
import util.Maths;

/**
 * @author shadi
 *
 */
public final class FlatColorShader extends Shader {

	private final static String SHADER_FILE = "src/resources/float_color_shader.glsl";

	private int locationTransformMatrix;
	private int locationProjectionMat;
	private int locationViewMatrix;
	private int locationColor;

	public FlatColorShader() {
		super(SHADER_FILE);
	}

	@Override
	protected void bindAttributes() {
		// TODO Auto-generated method stub
		super.bindAttribute(0, "position");
	}

	@Override
	protected void getAllUniformLocations() {
		// TODO Auto-generated method stub
		locationTransformMatrix = super.getUniformLocation("tranformMat");
		locationProjectionMat = super.getUniformLocation("projectionMat");
		locationViewMatrix = super.getUniformLocation("viewMatrix");
		locationColor = super.getUniformLocation("u_color");
	}

	public void loadTransformMatrix(Matrix4f matrix) {
		super.loadMatrix(locationTransformMatrix, matrix);
	}

	public void loadViewMatrix(Camera camera) {
		Matrix4f viewMatrix = Maths.createViewMatrix(camera);
		super.loadMatrix(locationViewMatrix, viewMatrix);
	}

	public void loadProjectionMatrix(Matrix4f projection) {
		super.loadMatrix(locationProjectionMat, projection);
	}

	public void loadColor(Vector4f color) {
		super.loadVector4f(locationColor, color);
	}
}
