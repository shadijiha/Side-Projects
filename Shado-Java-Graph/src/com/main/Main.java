package com.main;

import com.engin.*;
import com.graph.scenes.*;

public class Main {

	public static void main(String[] args) {
		// write your code here

		var renderer = new Renderer();
		renderer.start();

		renderer.submit(new AnimationTest());


	}
}
