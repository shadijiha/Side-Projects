package com.shado.math;

public abstract class Vec implements Comparable<Vec> {
	public double x;
	public double y;

	protected Vec(double x, double y) {
		this.x = x;
		this.y = y;
	}

	protected Vec(double scale) {
		this.x = 1.0;
		this.y = 1.0;
		this.random(scale);
	}

	protected Vec(Vec other) {
		this.x = other.x;
		this.y = other.y;
	}

	/**
	 * Modifies the current <b>x</b> and <b>y</b> to have random values
	 *
	 * @param scale The maximum value that the coordinates can have
	 */
	public void random(double scale) {
		this.x = Math.floor(Math.random() * scale);
		this.y = Math.floor(Math.random() * scale);
	}

	/**
	 * Modifies the current <b>x</b> and <b>y</b> to have random values with a scale
	 * of 10
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
	public abstract double mag();

	/**
	 * Modifies all the coordinates to have a value between 0.0 and 1.0
	 */
	public abstract void normalize();

	/**
	 * Adds a vector with the calling vector (does not modify the calling vector)
	 *
	 * @param other The other vector you want to add
	 * @return Returns the vector sum of other + the calling vector
	 */
	public abstract Vec add(final Vec other);

	/**
	 * Substracts a vector from the calling vector (does not modify the calling
	 * vector)
	 *
	 * @param other The other vector you want to substract
	 * @return Returns the vector diffrence of the calling vector - other
	 */
	public abstract Vec subtract(final Vec other);

	/**
	 * Computes the dot product of the calling vector and the vector b
	 *
	 * @param b The other vector you want to calculate dot product
	 * @return Returns the dot product of the calling vector and b
	 */
	public abstract double dotProduct(final Vec b);

	/**
	 * Computes the cross product of the calling vector and the vector b
	 *
	 * @param b The other vector you want to calculate cross product with
	 * @return Returns a vector representing the cross product between the calling
	 * vector and b
	 */
	public abstract Vec crossProduct(final Vec b);

	/**
	 * Multiplies the calling vector with a given matrix (does not modify the
	 * calling vector)
	 *
	 * @param matrix The matrix you want to multiply with
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
	 * @return Returns the resulting vector
	 */
	public Vec multiply(double scale) {
		Vec2D result = new Vec2D(this.x, this.y);
		result.scale(scale);
		return result;
	}

	/**
	 * Computes the resulting vector of the projection of the calling vector on
	 * another vector
	 *
	 * @param other The vector on which you want to project the calling
	 * @return Returns the resulting projection vector
	 */
	public Vec project(final Vec other) {

		double multiplier = other.dotProduct(this);

		multiplier = multiplier / (other.mag() * other.mag());

		return other.multiply(multiplier);
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
	 * 2 Vectors are equal if they have the same Magnitude
	 *
	 * @param o The other vector to compare
	 * @return Returns true if they 2 Vectors have the same Magnitude
	 */
	public boolean equals(Object o) {
		if (o == null || o.getClass() != getClass())
			return false;
		else {
			Vec v = (Vec) o;
			return v.mag() == this.mag();
		}
	}

	/**
	 * @return Returns a string with the following format (x: <i>x</i>, y: <i>y</i>,
	 * z: <i>z</i>)
	 */
	public String toString() {
		return String.format("(x: %f, y: %f)", this.x, this.y);
	}

	/**
	 * Compares the calling vector the the passed vector
	 *
	 * @param vec The vector to compare with
	 * @return Returns negative number if the calling vector is smaller, 0 if they are equal, positive number otherwise
	 */
	@Override
	public int compareTo(Vec vec) {
		return Double.compare(this.mag(), vec.mag());
	}
}
