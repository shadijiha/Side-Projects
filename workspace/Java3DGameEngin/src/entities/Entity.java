package entities;

import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;

/**
 * 
 * @author shadi
 *
 */
public class Entity {

	private TexturedModel model;
	private Vector3f positon;
	private Vector3f rotation;
	private Vector3f scale;

	public Entity(TexturedModel model, Vector3f positon, Vector3f rotation, Vector3f scale) {
		super();
		this.model = model;
		this.positon = positon;
		this.rotation = rotation;
		this.scale = scale;
	}

	public void increasePosition(float dx, float dy, float dz) {
		positon.x += dx;
		positon.y += dy;
		positon.z += dz;
	}

	public void increaseRotation(float dx, float dy, float dz) {
		rotation.x += dx;
		rotation.y += dy;
		rotation.z += dz;
	}

	public void increaseScale(float dx, float dy, float dz) {
		scale.x += dx;
		scale.y += dy;
		scale.z += dz;
	}

	public TexturedModel getModel() {
		return model;
	}

	public Vector3f getPositon() {
		return positon;
	}

	public Vector3f getRotation() {
		return rotation;
	}

	public Vector3f getScale() {
		return scale;
	}
}