/**
 *
 */
package com.shado.math;

import java.util.*;

/**
 * @author shadi
 *
 */
public class Vec3D extends Vec {

	public double x;
	public double y;
	public double z;

	/**
	 * The constructor used to initialize a 3D vector
	 *
	 * @param _x The x position of the vector
	 * @param _y The y position of the vector
	 * @param _z the z position of the vector
	 */
	public Vec3D(double _x, double _y, double _z) {
		super(_x, _y);
		this.x = _x;
		this.y = _y;
		this.z = _z;
	}

	/**
	 * The constructor used to initialize a 2D vector. This constructor initializes
	 * <b>z</b> to 0
	 *
	 * @param _x The x position of the vector
	 * @param _y The y position of the vector
	 */
	public Vec3D(double _x, double _y) {
		this(_x, _y, 0);
	}

	/**
	 * The constructor used to initialize a 2D vector with random <b>x</b> and
	 * <b>y</b>
	 *
	 * @param scale The maximum value that the coordinates can have
	 */
	public Vec3D(int scale) {
		this(0, 0);
		this.random(scale);
		List<Integer> test = new ArrayList<Integer>();
	}

	/**
	 * The default constructor initializes a 2D vector with a scale of 0
	 *
	 */
	public Vec3D() {
		this(10);
	}

	/**
	 * The copy constructor copies any given vector
	 *
	 * @param vectorTocopy The vector that you want to copy
	 */
	public Vec3D(Vec3D vectorTocopy) {
		super(vectorTocopy);
		this.x = vectorTocopy.x;
		this.y = vectorTocopy.y;
		this.z = vectorTocopy.z;
	}

	/**
	 * Modifies the current <b>x</b> and <b>y</b> to have random values
	 *
	 * @param scale The maximum value that the coordinates can have
	 */
	public void random(int scale) {
		this.x = Math.floor(Math.random() * scale);
		this.y = Math.floor(Math.random() * scale);
		this.z = Math.floor(Math.random() * scale);
	}

	/**
	 * Modifies the current <b>x</b> and <b>y</b> to have random values with a scale
	 * of 10
	 *
	 */
	public void random() {
		this.random(10);
	}

	/**
	 * multiplies all the coodinates of the calling vector by -1
	 */
	public void inverse() {
		this.x = -this.x;
		this.y = -this.y;
		this.z = -this.z;
	}

	/**
	 * multiplies all the coodinates by a value <b>scale</b>
	 *
	 * @param scale The value you want to multiply the vector with
	 */
	public void scale(double scale) {
		this.x *= scale;
		this.y *= scale;
		this.z *= scale;
	}

	/**
	 * @return Returns the magnitude of the calling vector
	 */
	public double mag() {
		return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
	}

	/**
	 * Modifies all the coordinates to have a value between 0.0 and 1.0
	 */
	public void normalize() {
		double tempMag = this.mag();
		this.x = this.x / tempMag;
		this.y = this.y / tempMag;
		this.z = this.z / tempMag;
	}

	/**
	 * Adds a vector with the calling vector (does not modify the calling vector)
	 *
	 * @param other The other vector you want to add
	 *
	 * @return Returns the vector sum of other + the calling vector
	 */
	public Vec3D add(final Vec other) {
		if (other instanceof Vec3D) {
			final Vec3D v = (Vec3D) other;
			return new Vec3D(this.x + v.x, this.y + v.y, this.z + v.z);
		} else {
			return new Vec3D(this.x + other.x, this.y + other.y);
		}

	}

	/**
	 * Substracts a vector from the calling vector (does not modify the calling
	 * vector)
	 *
	 * @param other The other vector you want to substract
	 *
	 * @return Returns the vector diffrence of the calling vector - other
	 */
	public Vec3D subtract(final Vec other) {
		other.inverse();
		return this.add(other);
	}

	/**
	 * Computes the dot product of the calling vector and the vector b
	 *
	 * @param b The other vector you want to calculate dot product
	 *
	 * @return Returns the dot product of the calling vector and b
	 */
	public double dotProduct(final Vec b) {
		if (b instanceof Vec3D) {
			final Vec3D v = (Vec3D) b;
			return this.x * v.x + this.y * v.y + this.z * v.z;
		} else {
			return this.x * b.x + this.y * b.y;
		}
	}

	/**
	 * Computes the cross product of the calling vector and the vector b
	 *
	 * @param v The other vector you want to calculate cross product with
	 *
	 * @return Returns a vector representing the cross product between the calling
	 *         vector and b
	 */
	public Vec3D crossProduct(final Vec v) {

		final Vec3D b;
		if (v instanceof Vec3D) {
			b = (Vec3D) v;
		} else {
			b = new Vec3D(v.x, v.y, 0.0f);
		}

		Matrix i = new Matrix(2, 2);
		i.setData(0, 0, y);
		i.setData(0, 1, z);
		i.setData(1, 0, b.y);
		i.setData(1, 1, b.z);

		Matrix j = new Matrix(2, 2);
		j.setData(0, 0, x);
		j.setData(0, 1, z);
		j.setData(1, 0, b.x);
		j.setData(1, 1, b.z);

		Matrix k = new Matrix(2, 2);
		k.setData(0, 0, x);
		k.setData(0, 1, y);
		k.setData(1, 0, b.x);
		k.setData(1, 1, b.y);

		return new Vec3D(i.determinant(), -1.0f * j.determinant(), k.determinant());
	}

	/**
	 * Multiplies the calling vector with a given matrix (does not modify the
	 * calling vector)
	 *
	 * @param matrix The matrix you want to multiply with
	 *
	 * @return Returns the result matrix of the multiplication
	 */
	public Matrix multiply(final Matrix matrix) {
		Matrix vec = this.toMatrix();
		return vec.multiply(matrix);
	}

	/**
	 * Multiplies all the coodinates of the calling vector with a number (does not
	 * modify the calling vector)
	 *
	 * @param scale The value you want to multiply the vector with
	 *
	 * @return Returns the resulting vector
	 */
	public Vec3D multiply(double scale) {
		Vec3D result = new Vec3D(this.x, this.y, this.z);
		result.scale(scale);
		return result;
	}

	/***
	 * Converts the calling vector a matrix and returns it
	 * @return Returns the resulting Matrix
	 */
	public Matrix toMatrix() {
		Double[][] temp = {
				{this.x},
				{this.y},
				{this.z}
		};
		return new Matrix(temp);
	}

	/**
	 *
	 * @return Returns a string with the following format (x: <i>x</i>, y: <i>y</i>,
	 *         z: <i>z</i>)
	 */
	public String toString() {
		return String.format("(x: %f, y: %f, z: %f)", this.x, this.y, this.z);
	}
}
