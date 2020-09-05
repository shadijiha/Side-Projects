package shaders;

import org.lwjgl.util.vector.Matrix4f;

import entities.Camera;
import util.Maths;

/**
 * 
 * @author shadi
 *
 */
public class StaticShader extends Shader {

	private final static String SHADER_FILE = "resources/test.glsl";

	private int locationTransformMatrix;
	private int locationProjectionMat;
	private int locationViewMatrix;

	public StaticShader() {
		super(SHADER_FILE);
	}

	@Override
	protected void bindAttributes() {
		// TODO Auto-generated method stub
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
	}

	@Override
	protected void getAllUniformLocations() {
		// TODO Auto-generated method stub
		locationTransformMatrix = super.getUniformLocation("tranformMat");
		locationProjectionMat = super.getUniformLocation("projectionMat");
		locationViewMatrix = super.getUniformLocation("viewMatrix");
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
}