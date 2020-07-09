package compiler.util;

import compiler.Compiler;

public interface Executable {

	/**
	 * Execute a predefined Runnable function
	 */
	//public void execute();

	/**
	 * Execute a predefined function with a single paramater
	 */
	public void execute(Compiler o);
}
