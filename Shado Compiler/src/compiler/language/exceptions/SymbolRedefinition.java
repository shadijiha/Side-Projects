package compiler.language.exceptions;

import compiler.language.Variable;

public class SymbolRedefinition extends CompilationError {

	public SymbolRedefinition(Variable var, String file, int line) {
		super("Variable redefined " + var.getName() + " at " + file + "::" + line);
	}
}
