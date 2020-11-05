/**
 *
 */
package com.core.keywords;

public abstract class Keyword {

	protected String word;
	protected int numberOfArguments;
	protected Class<?> javaType;

	public Keyword(String word, int numberOfArguments, Class<?> javaType) {
		this.word = word;
		this.numberOfArguments = numberOfArguments;
		this.javaType = javaType;

		KeywordManager.keywords.add(this);
	}
}
