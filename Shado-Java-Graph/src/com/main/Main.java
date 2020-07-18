package com.main;

import com.engin.Renderer;
import com.graph.scenes.AnimationTest;

public class Main {

	public static void main(String[] args) {
		// write your code here

		Renderer renderer = new Renderer();
		renderer.start();

		renderer.submit(new AnimationTest());


	}
}
