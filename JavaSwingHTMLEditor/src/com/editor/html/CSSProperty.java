/**
 *
 */
package com.editor.html;

public final class CSSProperty {

	public final String attribute;
	public final String value;

	public CSSProperty(final String attribute, final String value) {
		this.attribute = attribute;
		this.value = value;
	}

	public CSSProperty(final CSSProperty other) {
		this(other.attribute, other.value);
	}
}
