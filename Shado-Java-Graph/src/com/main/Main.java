package com.main;

import com.engin.*;

public class Main {

	public static void main(String[] args) {
		// write your code here

		var renderer = new Renderer(1000, 1000);
		renderer.start();

		//renderer.submit(new Environment());
		//renderer.submit(new UI());
		//renderer.submit(new EcosystemSimulation());
	}
}
