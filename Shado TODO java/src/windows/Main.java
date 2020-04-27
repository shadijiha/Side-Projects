package windows;

import java.util.concurrent.*;

/**
 *
 */

public class Main {

	public static final ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

	public static void main(String[] args) {

		executor.submit(new Runnable() {
			@Override
			public void run() {
				new MainWindow();
			}
		});

		executor.shutdown();
	}
}
