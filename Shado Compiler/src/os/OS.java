package os;

import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

public final class OS implements Runnable {

	private static final OS instance = new OS();

	private BlockingQueue<Task> tasks;
	private Thread thread;
	private volatile boolean on;
	private AtomicInteger tasksCompleted;

	private OS() {
		tasks = new ArrayBlockingQueue<Task>(100);
		on = true;
		thread = new Thread(this);
		thread.start();
		tasksCompleted = new AtomicInteger();
	}

	@Override
	public final void run() {

		Thread[] pool = new Thread[Runtime.getRuntime().availableProcessors() - 1];

		while (on) {

			if (tasks.size() == 0) {
				Thread.yield();
				continue;
			}

			// Loop through the tasks and solve them
			while (tasks.size() != 0) {

				// Create threads
				for (int i = 0; i < pool.length; i++) {

					if (tasks.peek() == null)
						continue;

					pool[i] = new Thread(tasks.poll().get());
					pool[i].start();
				}

				// Solve them
				for (Thread t : pool) {
					try {

						if (t == null)
							continue;

						t.join();
						tasksCompleted.incrementAndGet();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	private static OS getInstance() {
		return instance;
	}

	public static void submit(Task t) {
		getInstance().tasks.add(t);
	}

	public static void shutdown() {
		getInstance().on = false;
	}

	public static int tasksCompleted() {
		return getInstance().tasksCompleted.get();
	}
}
