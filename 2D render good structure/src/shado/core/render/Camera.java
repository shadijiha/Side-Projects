/**
 *
 */

package shado.core.render;

import shado.core.interfaces.*;
import shado.core.util.*;

import static java.lang.StrictMath.*;
import static shado.core.util.Util.*;

public class Camera {

	private Vec3 position = new Vec3(0.0f, 0.0f, 0.0f);
	private float rotation = 0.0f;

	private Matrix projectionMatrix;
	private Matrix viewMatrix;
	private Matrix viewProjectionMatrix;

	private Movable lookAtObject;

	public Camera(float left, float right, float bottom, float top) {

		projectionMatrix = Ortho(left, right, bottom, top, -1.0f, 1.0f);
		viewMatrix = Matrix.generateIdentity(4, 4);

		viewProjectionMatrix = projectionMatrix.multiply(viewMatrix);
	}

	private void reCalculateViewMatrix() {

		// Transform and rotate only arround the Z axis
		Matrix transform_M = translate(position);
		Matrix rotation_M = rotation(radians(rotation), new Vec3(0.0, 0.0, 1.0));

		viewMatrix = transform_M.multiply(rotation_M);

		viewProjectionMatrix = projectionMatrix.multiply(viewMatrix);
	}

	private static Matrix translate(Vec3 v) {

		Matrix result = Matrix.generateIdentity(4, 4);

		result.setData(0, 3, v.x);
		result.setData(1, 3, v.y);
		result.setData(2, 3, v.z);

		return result;
	}

	private static Matrix inverseOrtho(float left, float right, float bottom, float top, float far, float near) {

		Matrix ortho = new Matrix(4, 4);

		ortho.setData(0, 0, (right - left) / 2);
		ortho.setData(1, 1, (top - bottom) / 2);
		ortho.setData(2, 2, (far - near) / 2);
		ortho.setData(3, 3, 1);

		ortho.setData(0, 3, (left + right) / 2);
		ortho.setData(1, 3, (top + bottom) / 2);
		ortho.setData(2, 3, -1 * (far + near) / 2);


		return ortho;
	}

	private static Matrix Ortho(float left, float right, float bottom, float top, float far, float near) {

		Matrix ortho = new Matrix(4, 4);

		ortho.setData(0, 0, 2 / (right - left));
		ortho.setData(1, 1, 2 / (top - bottom));
		ortho.setData(2, 2, -2 / (far - near));
		ortho.setData(3, 3, 1);

		ortho.setData(0, 3, -1 * (right + left) / (right - left));
		ortho.setData(1, 3, -1 * (top + bottom) / (top - bottom));
		ortho.setData(2, 3, -1 * (far + near) / (far - near));

		return ortho;
	}

	private static Matrix rotation(double angle, Vec3 arround_axis) {

		double t = angle;
		Matrix m = Matrix.generateIdentity(4, 4);
		Vec3 u = new Vec3(arround_axis);
		u.normalize();

		m.setData(0, 0, cos(t) + square(u.x) * (1 - cos(t)));
		m.setData(0, 1, u.x * u.y * (1 - cos(t)) - u.z * sin(t));
		m.setData(0, 2, u.x * u.z * (1 - cos(t)) + u.z * sin(t));

		m.setData(1, 0, u.y * u.x * (1 - cos(t)) + u.z * sin(t));
		m.setData(1, 1, cos(t) + square(u.y) * (1 - cos(t)));
		m.setData(1, 2, u.y * u.z * (1 - cos(t)) - u.x * sin(t));

		m.setData(2, 0, u.z * u.x * (1 - cos(t)) - u.y * sin(t));
		m.setData(2, 1, u.z * u.y * (1 - cos(t)) + u.x * sin(t));
		m.setData(2, 2, cos(t) + square(u.z) * (1 - cos(t)));

		return m;
	}

	// Setters
	public void setPosition(Vec3 position) {
		this.position = position;
		reCalculateViewMatrix();
	}

	public void setRotation(float rotation) {
		this.rotation = rotation;
		reCalculateViewMatrix();
	}

	public void move(float x, float y) {
		position.x += x;
		position.y += y;
		reCalculateViewMatrix();
	}

	public void lookAt(Movable object) {
		lookAtObject = object;
		position = lookAtObject.getPosition();
	}

	// Getters
	public Vec3 getPosition() {
		return position;
	}

	public float getRotation() {
		return rotation;
	}

	public Matrix getProjectionMatrix() {
		return projectionMatrix;
	}

	public Matrix getViewMatrix() {
		return viewMatrix;
	}

	public Matrix getViewProjectionMatrix() {
		return viewProjectionMatrix;
	}

}
