package enginTester;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import rendererEngin.Loader;
import rendererEngin.Renderer;
import rendererEngin.Scene;
import textures.ModelTexture;
import util.Color;

/**
 * 
 * @author shadi
 *
 */
public class TestScene extends Scene {

	ModelTexture texture;

	@Override
	public void init(Loader loader) {
		// TODO Auto-generated method stub

		texture = new ModelTexture(loader.loadTexture("resources/Untitled.png"));

	}

	@Override
	public void update(float dt) {
		// TODO Auto-generated method stub

	}

	@Override
	public void draw(Renderer renderer) {
		// TODO Auto-generated method stub
		renderer.drawQuad(new Vector3f(0.25f, 1.0f, -5), new Vector2f(0.5f, 0.5f), Color.RED);
		renderer.drawQuad(new Vector3f(-1f, 1.0f, -5), new Vector2f(1f, 1f), texture);
	}

}