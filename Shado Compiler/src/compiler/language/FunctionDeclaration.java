package compiler.language;

import compiler.Compiler;
import compiler.Token;

public class FunctionDeclaration extends Token {

	private String functionName;
	private String functionBody;
	private Runnable function;


	public FunctionDeclaration(String raw_token) {
		super(raw_token);
	}

	/**
	 * Parsers the token from a string to java code
	 */
	@Override
	protected void parse() {

	}

	/**
	 * Executes the raw instruction given as a string
	 *
	 * @param c
	 */
	@Override
	public void execute(Compiler c) {

	}
}
