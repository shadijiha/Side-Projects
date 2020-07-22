/**
 *
 */
package compiler.language.types;

import java.util.*;

public abstract class PremetiveType extends VariableClass {

	public static final List<PremetiveType> PREMETIVE_TYPES = new ArrayList<>();

	protected String keyWord;

	protected PremetiveType(String className, String keyWord, Object defaultValue) {
		super(className, defaultValue);

		this.keyWord = keyWord;

		PREMETIVE_TYPES.add(this);
	}

}
