package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

/**
 * 
 * @author shadi
 *
 */
public class Camera {

	private Vector3f position;
	private Vector3f rotation;

	public Camera() {
		position = new Vector3f(0, 0, 0);
		rotation = new Vector3f(0, 0, 0);
	}

	public void move(float dx, float dy, float dz) {
		position.x += dx;
		position.y += dy;
		position.z += dz;
	}

	public void rotate(float dx, float dy, float dz) {
		rotation.x += dx;
		rotation.y += dy;
		rotation.z += dz;
	}

	public Vector3f getPosition() {
		return position;
	}

	public Vector3f getRotation() {
		return rotation;
	}

	public void addWASDListener() {
		if (Keyboard.isKeyDown(Keyboard.KEY_W))
			position.z -= 0.02f;
		if (Keyboard.isKeyDown(Keyboard.KEY_S))
			position.z += 0.02f;
		if (Keyboard.isKeyDown(Keyboard.KEY_D))
			position.x += 0.02f;
		if (Keyboard.isKeyDown(Keyboard.KEY_A))
			position.x -= 0.02f;
		if (Keyboard.isKeyDown(Keyboard.KEY_E))
			rotate(-1, -1, -1);
		if (Keyboard.isKeyDown(Keyboard.KEY_Q))
			rotate(1, 1, 1);
	}
}