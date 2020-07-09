package compiler.preprocessor;

import compiler.Compiler;
import compiler.util.*;

import java.util.*;

abstract public class PreprocessorCommand implements Executable {

	protected String raw_command;
	protected String command;
	protected int numberOfArguments;
	protected Class[] argumentsTypes;
	protected Object[] arguments;

	public PreprocessorCommand(String raw_command, String command, int numberOfArguments, Class<?>[] argumentsTypes) {
		this.command = command;
		this.numberOfArguments = numberOfArguments;
		this.argumentsTypes = argumentsTypes;
		this.raw_command = raw_command;

		if (this.argumentsTypes.length != this.numberOfArguments)
			throw new IllegalArgumentException("Number of arguments must be equal to the number of arguments types!");
	}

	public PreprocessorCommand(String raw_command, String command, int numberOfArguments, Object[] arguments) {
		this.command = command;
		this.numberOfArguments = numberOfArguments;
		this.argumentsTypes = new Class[arguments.length];
		this.arguments = arguments;
		this.raw_command = raw_command;

		// Get classes of each argument
		for (int i = 0; i < arguments.length; i++) {
			this.argumentsTypes[i] = this.arguments[i].getClass();
		}

		if (this.arguments.length != this.numberOfArguments)
			throw new IllegalArgumentException("Number of arguments must be equal to the number of arguments types!");
	}

	/**
	 * Execute a predefined Runnable function
	 */
	@Override
	public abstract void execute(Compiler o);

	public String getCommand() {
		return command;
	}

	public int getNumberOfArguments() {
		return numberOfArguments;
	}

	public Object[] getArgumentsTypes() {
		return argumentsTypes;
	}

	public Class<?> getArgumentType(int index) {

		if (index < 0 || index >= argumentsTypes.length)
			return null;

		return argumentsTypes[index];
	}

	public Object getArgument(int index) {

		if (index < 0 || index >= arguments.length)
			return null;

		return arguments[index];
	}

	public boolean equals(Object o) {
		if (o == null || o.getClass() != this.getClass())
			return false;
		else {
			var temp = (PreprocessorCommand) o;
			return command.equals(temp.command) && numberOfArguments == temp.numberOfArguments && Arrays.equals(arguments, temp.arguments) && Arrays.equals(argumentsTypes, temp.argumentsTypes);
		}
	}

}
