/**
 *
 */
package com.engin;

public abstract class Util {

	private Util() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Generates a random number between a range
	 * @param min The minimum number
	 * @param max The maximum number it can take
	 * @return Returns the generated number
	 */
	public static double random(double min, double max) {
		return Math.random() * (max - min) + min;
	}

	/**
	 * Generates a random number between a range
	 * @param min The minimum number
	 * @param max The maximum number it can take
	 * @return Returns the generated number
	 */
	public static int random(int min, int max) {
		return (int) random((double) min, (double) max);
	}

	/**
	 * Generates a random string
	 * @param chars The number of characters
	 * @param specialChars if you want special chars and numbers in your string
	 * @return Returns the generated string
	 */
	public static String randomString(int chars, boolean specialChars) {
		String abc = "abcdefghijklmnopqrstuvwxyz";
		String special = "1234567890!@#$%^&*(){}[];'/.,`";
		String complete = specialChars ? abc + special : abc;

		StringBuilder result = new StringBuilder();
		for (int i = 0; i < chars; i++) {
			boolean capital = random(0.0f, 1.0f) >= 0.5;
			String c = String.valueOf(complete.charAt(random(0, complete.length())));

			if (capital)
				result.append(c.toUpperCase());
			else
				result.append(c.toLowerCase());
		}

		return result.toString();
	}

	/**
	 * Generates a random string with letters only
	 * @param chars The number of characters
	 * @return Returns the generated string
	 */
	public static String randomString(int chars) {
		return randomString(chars, false);
	}
}
