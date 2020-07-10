package compiler.language.exceptions;

public class SyntaxError extends CompilationError {

	/**
	 * Constructs a new runtime exception with {@code null} as its
	 * detail message.  The cause is not initialized, and may subsequently be
	 * initialized by a call to {@link #initCause}.
	 */
	public SyntaxError() {
	}

	/**
	 * Constructs a new runtime exception with the specified detail message.
	 * The cause is not initialized, and may subsequently be initialized by a
	 * call to {@link #initCause}.
	 *
	 * @param message the detail message. The detail message is saved for
	 *                later retrieval by the {@link #getMessage()} method.
	 */
	public SyntaxError(String message) {
		super(message);
	}

	public SyntaxError(String expression, String file, int line) {
		super(String.format("Syntax error! " + expression + " in " + file + " at line " + line + " is not a valid expression!"));
	}
}
