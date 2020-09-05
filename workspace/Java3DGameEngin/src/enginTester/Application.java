package enginTester;

/**
 * Every Application That user this engin should have a class that extends this class
 * @author shadi
 *
 */
public final class Application {

	/**
	 * All scenes should be initialized here. Example:
	 * 
	 * 2 |	@Override
	 * 3 |	public static void init() {
	 * 4 |		new MyScene1();
	 * 5 |		new MyScene2();
	 * 6 |		new MyScene3();
	 * 7 |	}
	 */
	public static void init()	{
		// All scenes here
		new TestScene();
	}

}
