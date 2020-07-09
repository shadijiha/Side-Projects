package compiler.preprocessor.Exceptions;

import compiler.preprocessor.*;

public class InvalidCommand extends PreprocessorException {

	private String wrongCommand;

	public InvalidCommand(String wrongCommand) {
		super("ERROR! " + wrongCommand + " is not a valid preprocessor command!");
		this.wrongCommand = wrongCommand;
	}

	public InvalidCommand(PreprocessorCommand wrongCommand) {
		super("ERROR! " + wrongCommand.getCommand() + " is not a valid preprocessor command!");
		this.wrongCommand = wrongCommand.getCommand();
	}

	public String getWrongCommand() {
		return this.wrongCommand;
	}
}
