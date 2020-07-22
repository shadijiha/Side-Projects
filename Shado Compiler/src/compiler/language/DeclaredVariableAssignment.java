/**
 *
 */
package compiler.language;

import compiler.Compiler;
import compiler.*;
import compiler.language.exceptions.*;
import compiler.util.*;

public class DeclaredVariableAssignment extends Token {

	private String variableName;
	private Object variableValue;

	public DeclaredVariableAssignment(String raw_token) {
		super(raw_token);
	}

	/**
	 * Parsers the token from a string to java code
	 */
	@Override
	protected void parse() {

		// Split with the =
		String[] temp = rawToken.split("=");
		temp = Util.removeWhiteSpace(temp);

		// The variable name
		variableName = temp[0];
		variableValue = temp[1];
	}

	/**
	 * Executes the raw instruction given as a string
	 *
	 * @param c
	 */
	@Override
	public void execute(Compiler c) {

		// Add the variable to the compiler
		// Before adding it check if the variable name is already taken
		var var = new Variable(null, variableName, null);
		if (!c.hasVariable(var))
			throw new SymbolRedefinition(var, c.getFileName(), c.getCurrentLine());

		c.updateVariable(var.getName(), variableValue);
	}
}
