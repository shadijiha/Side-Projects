/**
 *
 */
package compiler.language;

import java.util.*;

public class Variable {

	private static final List<String> PREMETIVES = Arrays.asList("int", "float", "double", "long",
			"char", "byte", "bool", "string", "void", "unknown");

	private static final Map<String, Object> DEFAULT_VALUES = Map.of("int", 0, "float", 0.0f, "double", 0.0, "long", 0L,
			"char", (char) 0, "byte", (byte) 0, "bool", false, "string", "", "void", null, "unknown", null);

	private String type;
	private String name;
	private Object value;

	public Variable(String type, String name, Object value) {
		this.type = type;
		this.name = name;
		this.value = value;
	}

	public void setValue(Object value) {
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

	public static Object defaultValue(String of) {
		return DEFAULT_VALUES.get(of);
	}

	public static List<String> getPremetives() {
		return new ArrayList<>(PREMETIVES);
	}
}
