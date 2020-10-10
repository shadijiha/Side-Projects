/**
 * 
 */
package shaders;

import org.lwjgl.util.vector.Vector4f;

/**
 * @author shadi
 *
 */
public final class FlatColorShader extends Shader {

	private final static String SHADER_FILE = "src/resources/float_color_shader.glsl";

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

	public void loadColor(Vector4f color) {
		super.loadVector4f(locationColor, color);
	}
}
