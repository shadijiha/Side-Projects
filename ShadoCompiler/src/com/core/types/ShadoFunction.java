/**
 *
 *
 */
package com.core.types;

import java.util.function.*;

public class ShadoFunction {

	public final int numberOfArguments;
	public final String name;
	private final Function<Object[], Object> code;


	public ShadoFunction(String name, int numberOfArguments, Function<Object[], Object> code) {
		this.numberOfArguments = numberOfArguments;
		this.name = name;
		this.code = code;
	}


	/**
	 * When an object implementing interface <code>Runnable</code> is used
	 * to create a thread, starting the thread causes the object's
	 * <code>run</code> method to be called in that separately executing
	 * thread.
	 * <p>
	 * The general contract of the method <code>run</code> is that it may
	 * take any action whatsoever.
	 *
	 * @see Thread#run()
	 */
	public void execute(Object[] objects) {
		code.apply(objects);
	}
}
