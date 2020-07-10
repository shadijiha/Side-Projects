package compiler.language;

import compiler.Compiler;
import compiler.Token;
import compiler.language.exceptions.SymbolRedefinition;
import compiler.language.exceptions.SyntaxError;
import compiler.util.Util;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.Arrays;
import java.util.regex.Pattern;

public class AssignmentExpression extends Token {

	private String variableType;
	private String variableName;
	private Object variableValue;

	private Compiler compiler;
	private boolean parseSuccess = true;


	public AssignmentExpression(String raw_token) {
		super(raw_token);
	}

	/**
	 * Parsers the token from a string to java code
	 */
	@Override
	protected void parse() {

		String[] buffer = rawToken.split("\\s");
		buffer = Util.removeWhiteSpace(buffer);

		String[] restArr = Arrays.copyOf(buffer, buffer.length);
		restArr[0] = "";
		restArr[1] = "";
		String rest = Util.stringArrayToString(restArr, " ")
				.replaceFirst("=", "")
				.replaceAll(";", "")
				.trim();

		try {
			variableType = buffer[0];
			variableName = buffer[1];

			if (Util.isNumber(rest))
				variableValue = Util.toNumber(rest);
			else {
				ScriptEngineManager mgr = new ScriptEngineManager();
				ScriptEngine engine = mgr.getEngineByName("JavaScript");
				variableValue = engine.eval(rest);
			}

		} catch (ArrayIndexOutOfBoundsException e) {
			throw new SyntaxError(rawToken, compiler.getFileName(), compiler.getCurrentLine());
		} catch (ScriptException e) {

			// IF we are here that means the the expression is not a number and
			// cannot be evaluate to a number

			// First see if the expression contains another variable
			// FOR this condition the other variable must be a premetive type
			// e.g.:
			// int x = 10;
			// int y = x + 20;
			int replacements = 0;
			for (var temp : compiler.allVariables().entrySet()) {
				Variable var = temp.getValue();

				boolean t = Pattern.compile("\\b" + var.getName() + "\\b").matcher(rest).find();
				if (t && var.isPremetive()) {
					rest = rest.replaceAll("\\b" + var.getName() + "\\b", var.getValue().toString());
					variableValue = rest;
					replacements++;
				}
			}

			// If changes were made, then re-parse
			if (replacements > 0) {
				String new_token = String.format("%s %s = %s", variableType, variableName, variableValue);
				new AssignmentExpression(new_token).execute(compiler);
				parseSuccess = false;
				return;
			}


			// For all other cases
			variableValue = rest;
		}
	}

	/**
	 * Executes the raw instruction given as a string
	 *
	 * @param c
	 */
	@Override
	public void execute(Compiler c) {

		this.compiler = c;

		parse();

		if (!parseSuccess)
			return;

		// Add the variable to the compiler
		// Before adding it check if the variable name is already taken
		var var = new Variable(variableType, variableName, variableValue);
		if (c.hasVariable(var))
			throw new SymbolRedefinition(var, c.getFileName(), c.getCurrentLine());

		c.addVariable(var);
	}
}
