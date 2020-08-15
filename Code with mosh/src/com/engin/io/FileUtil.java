/**
 *
 */
package com.engin.io;

import com.engin.logger.*;

import java.io.*;
import java.util.*;

public abstract class FileUtil {

	private FileUtil() {
	}

	/**
	 * Reads a file and converts the content to a string
	 * @param path The path of the file
	 * @return Returns the content of the file
	 */
	public static String fileToString(String path) {
		try {

			StringBuilder builder = new StringBuilder();
			Scanner scanner = new Scanner(new FileInputStream(path));

			while (scanner.hasNextLine())
				builder.append(scanner.nextLine()).append('\n');

			scanner.close();

			return builder.toString();

		} catch (IOException e) {
			Debug.error(e);
			return null;
		}
	}

	/**
	 * Writes some data to a file
	 * @param path The path of the file
	 * @param data The data to write
	 * @param append If you want the overwrite the file or append to its end
	 */
	public static void writeToFile(String path, Object data, boolean append) {

		PrintWriter writer = null;
		try {
			writer = new PrintWriter(new FileOutputStream(path, append));
			writer.print(data.toString());
		} catch (IOException e) {
			Debug.error(e);
		} finally {
			writer.close();
		}
	}

	/**
	 * Overwrites data in the file
	 * @param path The path of the file
	 * @param data The data to write
	 */
	public static void writeToFile(String path, Object data) {
		writeToFile(path, data, false);
	}

	/**
	 * Dumbs an object to a binary file
	 * @param path The file path
	 * @param object The object to dumb
	 */
	public static void dumbObject(String path, Object object) {
		try {
			ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(path));
			stream.writeObject(object);
			stream.close();
		} catch (IOException e) {
			Debug.error(e);
		}
	}

	public static Object readObject(String filepath) {

		try {
			FileInputStream fileIn = new FileInputStream(filepath);
			ObjectInputStream objectIn = new ObjectInputStream(fileIn);
			Object obj = objectIn.readObject();
			objectIn.close();
			return obj;
		} catch (Exception e) {
			Debug.error(e);
			return null;
		}
	}

	/**
	 * Converts a path to a file
	 * @param path The path of the file
	 * @return Return a file of the path
	 */
	public static File toFile(String path) {
		return new File(path);
	}
}
