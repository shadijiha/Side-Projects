package compiler.util;

import java.io.*;
import java.util.*;

public abstract class Util {

	private Util() {
	}

	public static boolean isNumber(String s) {
		try {
			Double.parseDouble(s);

		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	public static Number toNumber(String s) {

		try {
			return Integer.parseInt(s);
		} catch (NumberFormatException ignored) {

		}

		try {
			return Double.parseDouble(s);
		} catch (NumberFormatException ignored) {

		}

		return null;
	}

	public static Object[] stringToObjects(String[] array) {

		List<Object> list = new ArrayList<>();

		for (String element : array) {
			if (isNumber(element))
				list.add(toNumber(element));
			else
				list.add(element);
		}

		return list.toArray(Object[]::new);
	}

	public static String readFile(String path) throws FileNotFoundException {

		StringBuilder builder = new StringBuilder();
		File file = new File(path);

		if (!file.exists())
			throw new FileNotFoundException(file.getAbsolutePath() + " was not found!");

		Scanner scanner = new Scanner(file);
		while (scanner.hasNextLine())
			builder.append(scanner.nextLine()).append("\n");

		return builder.toString();
	}

	public static void writeToFile(String path, String data) {

		PrintWriter writer = null;
		try {
			writer = new PrintWriter(new FileOutputStream(path));
			writer.print(data);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			writer.close();
		}
	}
}
