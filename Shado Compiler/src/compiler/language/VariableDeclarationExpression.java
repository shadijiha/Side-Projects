/**
 *
 */
package compiler.language;

import compiler.Compiler;
import compiler.*;
import compiler.language.exceptions.*;
import compiler.util.*;

public class VariableDeclarationExpression extends Token {

	private String variableType;
	private String variableName;
	private Object variableValue;

	private boolean parseSuccess;
	private Compiler compiler;

	public VariableDeclarationExpression(String raw_token) {
		super(raw_token);
	}

	/**
	 * Parsers the token from a string to java code
	 */
	@Override
	protected void parse() {

		String[] buffer = rawToken.split("\\s");
		buffer = Util.removeWhiteSpace(buffer);

		variableType = buffer[0];
		variableName = buffer[1].trim().replaceAll(";", "");

		// Assign the default value to the variable
		variableValue = Variable.defaultValue(variableType);
	}

	/**
	 * Executes the raw instruction given as a string
	 *
	 * @param c
	 */
	@Override
	public void execute(Compiler c) {
		parse();

		if (!parseSuccess)
			return;

		// Add the variable to the compiler
		// Before adding it check if the variable name is already taken
		var var = new Variable(variableType, variableName, null);
		if (c.hasVariable(var))
			throw new SymbolRedefinition(var, c.getFileName(), c.getCurrentLine());

		c.addVariable(var);
	}
}
