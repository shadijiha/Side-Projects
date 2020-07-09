package compiler.preprocessor.commands;

import compiler.Compiler;
import compiler.preprocessor.*;

import java.io.*;
import java.util.*;

public class IncludeCommand extends PreprocessorCommand {

	private String filename;
	private String fileContent;

	public IncludeCommand(String raw_command, String filename) {
		super(raw_command, "include", 1, new Object[]{filename});

		this.filename = filename;
		this.raw_command = raw_command;
	}

	/**
	 * Execute a predefined Runnable function
	 */
	@Override
	public void execute(Compiler compiler) {

		// Load the target file
		File file = new File(filename);
		if (!file.exists())
			throw new RuntimeException(filename + " was not found or cannot be read!");

		StringBuilder builder = new StringBuilder();

		try {
			Scanner scanner = new Scanner(file);

			while (scanner.hasNextLine())
				builder.append(scanner.nextLine()).append("\n");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		// Copy and paste the file to the original "main" file
		compiler.replace(raw_command, builder.toString());
	}
}
