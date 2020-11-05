/**
 *
 */
package com.core.util;

import java.io.*;
import java.util.*;

public class FileUtil {

	public static String fileAsString(final String filename)	{

		try {
			Scanner scanner = new Scanner(new FileInputStream(filename));
			StringBuilder builder = new StringBuilder();

			while (scanner.hasNextLine())
				builder.append(scanner.nextLine()).append("\n");

			return builder.toString();

		} catch (IOException e)	{
			e.printStackTrace();
			return null;
		}
	}

}
