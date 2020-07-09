package compiler.preprocessor;

import compiler.Compiler;
import compiler.*;
import compiler.preprocessor.Exceptions.*;
import compiler.preprocessor.commands.*;

import java.util.*;

public final class Preprocessor extends Token {

	private static final List<PreprocessorCommand> COMMANDS = new ArrayList<>();
	private static boolean hasBeenInitialized = false;

	private PreprocessorCommand command;


	public Preprocessor(String raw_token) {
		super(raw_token);

		if (!raw_token.startsWith("#"))
			throw new IllegalArgumentException("Preprocessor instructions always start with a \"#\"");

		// First time this object gets created it must initialize all commands
		if (!hasBeenInitialized) {
			initializeCommands();
			hasBeenInitialized = true;
		}
	}

	@Override
	protected void parse() {

		// get the command and its arguments
		// E.g.: "#include "main.shado" --> {"#include", ""main.shado""}
		String[] tokens = rawToken.split(" ");

		// Remove the "#" and the ""
		for (int i = 0; i < tokens.length; i++)
			tokens[i] = tokens[i].replaceAll("[#\"]", "");

		// See if the command exists
		String[] args = Arrays.stream(tokens).skip(1).toArray(String[]::new);

		PreprocessorCommand tempCommand;

		switch (tokens[0]) {
			case "include":
				tempCommand = new IncludeCommand(rawToken, args[0]);
				break;
			case "define":
				tempCommand = new DefineCommand(rawToken, args[0], args[1]);
				break;
			// Add more here
			default:
				throw new InvalidCommand(tokens[0]);

		}

		// If it is valid then save it to exceute it later
		command = tempCommand;
	}

	@Override
	public void execute(Compiler c) {

		// Parse the command
		parse();

		command.execute(c);
	}

	private void initializeCommands() {

//		PreprocessorCommand include = new PreprocessorCommand("include", 1, new Class[]{String.class});
//		PreprocessorCommand define = new PreprocessorCommand("define", 2, new Class[]{String.class, Object.class});
//
//		include.setTask(new Runnable() {
//
//		});
//
//		COMMANDS.add(include);
//		COMMANDS.add(define);
	}

	/**
	 * This function evaluates if the passed argument is a valid preprocessor command
	 *
	 * @param cmd The command to evaluate
	 * @return Return true if it is a valid command, false otherwise
	 */
	private boolean isValidCommand(String cmd) {
		for (var command : COMMANDS)
			if (command.getCommand().equals(cmd))
				return true;
		return false;
	}

	private PreprocessorCommand getCommandByName(String name) throws PreprocessorException {

		if (isValidCommand(name)) {
			for (var command : COMMANDS)
				if (command.getCommand().equals(name))
					return command;
		}

		throw new InvalidCommand(name);
	}
}
