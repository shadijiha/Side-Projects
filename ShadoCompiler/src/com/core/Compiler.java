/**
 *
 */
package com.core;

import java.util.*;

import static com.core.util.FileUtil.fileAsString;

public class Compiler {

	public static final Compiler singleton = new Compiler();

	private VirtualMachine vm;

	private Compiler()	{
		vm = new VirtualMachine();
	}

	public static void compile(String filename)	{

		String fileContent = fileAsString(filename);
		List<String> lines = new ArrayList<>(Arrays.asList(fileContent.split("\n")));

		int lineNumber = 0;

	}


}
