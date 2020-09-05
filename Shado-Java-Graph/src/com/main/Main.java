package com.main;

import com.ecosystem.EcosystemSimulation;
import com.ecosystem.Environment;
import com.ecosystem.UI;
import com.engin.Renderer;

public class Main {

	public static void main(String[] args) {
		// write your code here

		var renderer = new Renderer(1000, 1000);
		renderer.start();

		//renderer.submit(new EventTest());
		renderer.submit(new Environment());
		renderer.submit(new UI());
		renderer.submit(new EcosystemSimulation());
	}
}
