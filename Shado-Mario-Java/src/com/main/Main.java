package com.main;

import com.engin.*;
import com.game.objects.*;

import java.awt.*;

public class Main {

	public static void main(String[] args) {
		// write your code here

		Renderer renderer = new Renderer();
		renderer.start();

		Scene debugScene = new Scene("Test scene") {

			private Monster test;

			@Override
			public void init() {
				test = new Monster(100, 100);
			}

			@Override
			public void update(long dt) {

			}

			@Override
			public void draw(Graphics g) {
				test.draw(g);
			}
		};

		renderer.submit(debugScene);

	}
}
