/**
 *
 */

package com.shado.math;

public class Vec2D extends Vec {
	public double x;
	public double y;

	/**
	 * The constructor used to initialize a 2D vector. This constructor initializes
	 * <b>z</b> to 0
	 *
	 * @param _x The x position of the vector
	 * @param _y The y position of the vector
	 */
	public Vec2D(double _x, double _y) {
		super(_x, _y);
		this.x = _x;
		this.y = _y;
	}

	/**
	 * The constructor used to initialize a 2D vector with random <b>x</b> and
	 * <b>y</b>
	 *
	 * @param scale The maximum value that the coordinates can have
	 */
	public Vec2D(int scale) {
		this(0, 0);
		this.random(scale);
	}

	/**
	 * The default constructor initializes a 2D vector with a scale of 0
	 *
	 */
	public Vec2D() {
		this(10);
	}

	/**
	 * The copy constructor copies any given vector
	 *
	 * @param vectorTocopy The vector that you want to copy
	 */
	public Vec2D(Vec2D vectorTocopy) {
		super(vectorTocopy);
		this.x = vectorTocopy.x;
		this.y = vectorTocopy.y;
	}

	/**
	 * Modifies the current <b>x</b> and <b>y</b> to have random values
	 *
	 * @param scale The maximum value that the coordinates can have
	 */
	public void random(int scale) {
		this.x = Math.floor(Math.random() * scale);
		this.y = Math.floor(Math.random() * scale);
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
	}

	/**
	 * multiplies all the coodinates by a value <b>scale</b>
	 *
	 * @param scale The value you want to multiply the vector with
	 */
	public void scale(double scale) {
		this.x *= scale;
		this.y *= scale;
	}

	/**
	 * @return Returns the magnitude of the calling vector
	 */
	public double mag() {
		return Math.sqrt(this.x * this.x + this.y * this.y);
	}

	/**
	 * Modifies all the coordinates to have a value between 0.0 and 1.0
	 */
	public void normalize() {
		double tempMag = this.mag();
		this.x = this.x / tempMag;
		this.y = this.y / tempMag;
	}

	/**
	 * Adds a vector with the calling vector (does not modify the calling vector)
	 *
	 * @param other The other vector you want to add
	 *
	 * @return Returns the vector sum of other + the calling vector
	 */
	public Vec2D add(final Vec other) {
		return new Vec2D(this.x + other.x, this.y + other.y);
	}

	/**
	 * Substracts a vector from the calling vector (does not modify the calling
	 * vector)
	 *
	 * @param other The other vector you want to substract
	 *
	 * @return Returns the vector diffrence of the calling vector - other
	 */
	public Vec2D subtract(final Vec other) {
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
		if (b instanceof Vec2D) {
			return this.x * b.x + this.y * b.y;
		} else {
			Vec3D temp = new Vec3D(this.x, this.y, 0.0D);
			return temp.dotProduct(b);
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
		i.setData(0, 1, 0);
		i.setData(1, 0, b.y);
		i.setData(1, 1, 0);

		Matrix j = new Matrix(2, 2);
		j.setData(0, 0, x);
		j.setData(0, 1, 0);
		j.setData(1, 0, b.x);
		j.setData(1, 1, 0);

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
	public Vec2D multiply(double scale) {
		Vec2D result = new Vec2D(this.x, this.y);
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
		};
		return new Matrix(temp);
	}

	/***
	 * Converts the calling Vector to a Complex number
	 * @return Returns the resulting Complex number
	 */
	public Complex toComplex() {
		return new Complex(this.x, this.y);
	}

	// Overriden java.lang.*

	/**
	 * 2 Vectors are equal if they have the same X and the same Y
	 * @param o The other vector to compare
	 * @return Returns true if they 2 Vectors have the same coordinates
	 */
	public boolean equals(Object o) {
		if (o == null || o.getClass() != getClass())
			return false;
		else {
			Vec2D v = (Vec2D) o;
			return v.x == this.x && v.y == this.y;
		}
	}

	// Printers

	/**
	 *
	 * @return Returns a string with the following format (x: <i>x</i>, y: <i>y</i>,
	 *         z: <i>z</i>)
	 */
	public String toString() {
		return String.format("(x: %f, y: %f)", this.x, this.y);
	}
}
