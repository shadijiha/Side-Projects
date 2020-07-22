/**
 *
 */
package compiler.language.types;

public enum Operator {

	PLUS("+"),
	MINUS("-"),
	MULT("*"),
	DIV("/"),
	MOD("%"),

	INCREMENT("++"),
	DECREMENT("--"),

	POINTER("*"),
	REF("&"),
	DOUBLE_REF("&&"),

	EXCLAMATION("!"),
	NULLABLE("?"),

	ASSIGNMENT("="),

	EQUAL("=="),
	NOT_EQUAL("!="),
	GREATER(">"),
	LESS("<"),
	GREATER_OR_EQUALS(">="),
	LESS_OR_EQUALS("<="),

	ARRAY_INDEX("[]"),

	COSTUME("^");

	private String op;

	Operator(String op) {
		this.op = op;
	}

	public String getUrl() {
		return op;
	}
}
