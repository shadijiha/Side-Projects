/**
 *
 */
package com.engin.math;

import java.io.PrintStream;

public final class Matrix<T extends Number> {

	private int rows;
	private int cols;
	private T[] data;

	public Matrix(int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
		this.data = (T[]) new Number[rows * cols];
	}

	public Matrix(int rows, int cols, T[] data) {
		this.cols = cols;
		this.rows = rows;
		this.data = data;
	}

	public Matrix() {
		this(4, 4);
	}

	public Matrix(final Matrix<? extends T> m) {
		this(m.rows, m.cols);

		for (int i = 0; i < m.data.length; i++) {
			this.data[i] = m.data[i];
		}
	}

	public T get(int row, int col) {
		return this.data[row * cols + col];
	}

	public void print(PrintStream stream) {

		stream.print("[");
		for (int y = 0; y < this.rows; y++) {
			for (int x = 0; x < this.cols; x++)
				stream.print(data[y * cols + x] + "\t");
			stream.println();
		}
		stream.print("]");
	}

	public void print() {
		print(System.out);
	}
}
