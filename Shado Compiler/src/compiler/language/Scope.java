package compiler.language;

import compiler.Compiler;
import compiler.Token;

public class Scope extends Token {

	private int line_to_start;

	protected Scope(String raw_token, int line_to_start) {
		super(raw_token);
		this.line_to_start = line_to_start;
	}

	/**
	 * Parsers the token from a string to java code
	 */
	@Override
	protected void parse() {

		int level = 0;
		char[] chars = rawToken.toCharArray();

		for (char c : chars) {

			if (c == '{')
				level++;
			else if (c == '}')
				level--;

		}


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
