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
		position = new Vector3f(0, 0, 5);
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

	public void listenToWASD(float dt) {
		if (Keyboard.isKeyDown(Keyboard.KEY_W))
			position.y += 2f * dt;
		if (Keyboard.isKeyDown(Keyboard.KEY_S))
			position.y -= 2f * dt;
		if (Keyboard.isKeyDown(Keyboard.KEY_D))
			position.x += 2f * dt;
		if (Keyboard.isKeyDown(Keyboard.KEY_A))
			position.x -= 2f * dt;
		if (Keyboard.isKeyDown(Keyboard.KEY_UP))
			position.z -= 2f * dt;
		if (Keyboard.isKeyDown(Keyboard.KEY_DOWN))
			position.z += 2f * dt;
		if (Keyboard.isKeyDown(Keyboard.KEY_E))
			rotate(0, 10 * dt, 0);
		if (Keyboard.isKeyDown(Keyboard.KEY_Q))
			rotate(0, -10 * dt, 0);
	}
}
