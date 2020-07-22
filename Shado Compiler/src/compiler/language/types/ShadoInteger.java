package compiler.language.types;

public class ShadoInteger extends PremetiveType {

	protected ShadoInteger(String className, Object defaultValue) {
		super("Integer", "int", defaultValue);
	}

	public void init() {

		var add = (ShadoInteger other) -> {
			return other.get;
		};

	}
}
