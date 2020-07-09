/**
 *
 */

package compiler;

import compiler.util.*;

public abstract class Token implements Executable {

	protected String rawToken;
	protected Compiler compiler;

	protected Token(String raw_token) {
		this.rawToken = raw_token;
	}

	/**
	 * Parsers the token from a string to java code
	 */
	protected abstract void parse();

	/**
	 * Executes the raw instruction given as a string
	 */
	public abstract void execute(Compiler c);
}
