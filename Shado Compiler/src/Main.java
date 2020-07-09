import compiler.Compiler;

import java.io.*;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		// write your code here

//		Task task1 = new Task(() -> System.out.println("Hello 1"));
//		Task task2 = new Task(() -> {
//			long sum = 0;
//			for (int i = 0; i < 10_000_000; i++)
//				sum += 10 * i;
//		});
//
//		OS.submit(task1);
//		OS.submit(task2);
//
//		OS.shutdown();
//
//		System.out.println(task1.deltaTime() + " ms");
//		System.out.println(task2.deltaTime() + " ms");
//
//		System.out.println(OS.tasksCompleted());

		Compiler compiler = new Compiler("main.shado");
		compiler.compile();

	}
}
