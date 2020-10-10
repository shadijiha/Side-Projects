package shaders;

/**
 * 
 * @author shadi
 *
 */
public final class StaticShader extends Shader {

	private final static String SHADER_FILE = "src/resources/static_shader.glsl";

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
}
