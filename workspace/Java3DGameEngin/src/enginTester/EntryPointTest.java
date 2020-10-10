package enginTester;

import rendererEngin.Application;

/**
 * Every Application That user this engin should have a class that extends this
 * class
 * 
 * @author shadi
 *
 */
public final class EntryPointTest {

	/**
	 * All scenes should be initialized here. Example:
	 * 
	 * 2 | @Override 3 | public static void init() { 4 | new MyScene1(); 5 | new
	 * MyScene2(); 6 | new MyScene3(); 7 | }
	 */
	public static void main(String[] args) {
		// All scenes here
		var app = new Application();
		app.submit(new ExampleScene());
		app.run();
	}

}
