/**
 *
 */
package com.engin.logger;

import javax.swing.*;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public final class Debug {

	@Deprecated
	public static final Debug INSTANCE = new Debug();

	public static final Debug singleton = INSTANCE;

	private final List<String> allMessages = new ArrayList<>();

	private Debug() {
	}

	public static void error(Throwable e) {
		JOptionPane.showMessageDialog(null, e.toString(), "Error!", JOptionPane.ERROR_MESSAGE);

		singleton.allMessages.add("[" + new Date().toString() + "]\t" + "Error!\t" + e.getMessage());
	}

	public static void error(String message) {
		error(new Throwable(message));
	}

	public static void log(Throwable e) {
		JOptionPane.showMessageDialog(null, e.getMessage(), "Info!", JOptionPane.INFORMATION_MESSAGE);

		singleton.allMessages.add("[" + new Date().toString() + "]\t" + "Info!\t" + e.getMessage());
	}

	public static void log(String message) {
		log(new Throwable(message));
	}

	public static void warn(Throwable e) {
		JOptionPane.showMessageDialog(null, e.getMessage(), "Warning!", JOptionPane.WARNING_MESSAGE);

		singleton.allMessages.add("[" + new Date().toString() + "]\t" + "Warning!\t" + e.getMessage());
	}

	public static void warn(String message) {
		warn(new Throwable(message));
	}

	public static void push(String message) {
		singleton.allMessages.add("[" + new Date().toString() + "]\t" + "Info!\t" + message);
	}

	/**
	 * Prints all the Logger's buffer to the current System PrintStream and clears the buffer
	 */
	public static void flush(PrintStream stream) {
		for (String s : singleton.allMessages) {
			stream.println(s);
		}

		singleton.allMessages.clear();
	}

	public static void flush(String filename) {

		try {
			PrintWriter writer = new PrintWriter(new FileOutputStream(filename));

			for (String s : singleton.allMessages) {
				writer.println(s);
			}

			writer.close();
		} catch (Exception e) {
			Debug.error(e);
		}

		singleton.allMessages.clear();
	}

	public static void flush() {
		flush("logs.txt");
	}
}
