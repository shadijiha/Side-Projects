/**
 *
 */
package compiler.language;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Variable {

	private static final List<String> PREMETIVES = Arrays.asList("int", "float", "double", "long",
			"char", "byte", "bool", "string", "void", "unknown");

	private String type;
	private String name;
	private Object value;

	public Variable(String type, String name, Object value) {
		this.type = type;
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public Object getValue() {
		return value;
	}

	public String getType() {
		return type;
	}

	public boolean isPremetive() {
		return PREMETIVES.contains(this.type);
	}

	public static List<String> getPremetives() {
		return new ArrayList<>(PREMETIVES);
	}
}
