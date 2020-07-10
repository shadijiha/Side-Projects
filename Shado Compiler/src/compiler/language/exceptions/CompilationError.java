package compiler.language.exceptions;

abstract public class CompilationError extends RuntimeException {

	/**
	 * Constructs a new runtime exception with {@code null} as its
	 * detail message.  The cause is not initialized, and may subsequently be
	 * initialized by a call to {@link #initCause}.
	 */
	public CompilationError() {
	}

	/**
	 * Constructs a new runtime exception with the specified detail message.
	 * The cause is not initialized, and may subsequently be initialized by a
	 * call to {@link #initCause}.
	 *
	 * @param message the detail message. The detail message is saved for
	 *                later retrieval by the {@link #getMessage()} method.
	 */
	public CompilationError(String message) {
		super(message);
	}

	public CompilationError(String file, int line) {
		super(String.format("Compilation error! in " + file + " at line " + line));
	}

}
