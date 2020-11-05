/**
 *
 */
package com.core.types;

public class ShadoVariable<T> {

	public final String name;
	private T value;

	public ShadoVariable(String name, T value) {
		this.name = null;
	}

	public void setValue(T newValue)	{
		value = newValue;
	}

	public T value()	{
		return value;
	}
}
