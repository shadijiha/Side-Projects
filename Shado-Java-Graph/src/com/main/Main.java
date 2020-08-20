package com.main;

import com.engin.Renderer;
import com.graph.EventTest;
import com.racingGame.GlabalScene;

public class Main {

	public static void main(String[] args) {
		// write your code here

		var renderer = new Renderer();
		renderer.start();

		renderer.submit(new EventTest());
		renderer.submit(new GlabalScene());

	}
}
