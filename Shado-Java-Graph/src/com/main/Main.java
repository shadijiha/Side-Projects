package com.main;

import com.engin.Renderer;
import com.engin.math.Matrix;
import com.graph.EventTest;

public class Main {

	public static void main(String[] args) {
		// write your code here

		var renderer = new Renderer();
		renderer.start();

		renderer.submit(new EventTest());

		Matrix mat = new Matrix(3, 3, new Integer[]{1, 3, 4,
				7, 6, 1,
				9, 8, 0});

		mat.print();
	}
}
