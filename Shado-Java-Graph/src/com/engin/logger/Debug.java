/**
 *
 */
package com.engin.logger;

import javax.swing.*;

public final class Debug {

	public static final Debug INSTANCE = new Debug();

	private Debug() {
	}

	public static void error(Throwable e) {
		JOptionPane.showMessageDialog(null, e.toString(), "Error!", JOptionPane.ERROR_MESSAGE);
	}

	public static void error(String message) {
		error(new Throwable(message));
	}

	public static void log(Throwable e) {
		JOptionPane.showMessageDialog(null, e.getMessage(), "Info!", JOptionPane.INFORMATION_MESSAGE);
	}

	public static void log(String message) {
		log(new Throwable(message));
	}

	public static void warn(Throwable e) {
		JOptionPane.showMessageDialog(null, e.getMessage(), "Warning!", JOptionPane.WARNING_MESSAGE);
	}

	public static void warn(String message) {
		warn(new Throwable(message));
	}
}
