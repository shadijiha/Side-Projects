/**
 *
 * Shado Vm
 *
 */
package com.core;

import com.core.types.*;

import java.util.*;

public final class VirtualMachine {

	private Map<String, ShadoFunction> functions;
	private Map<String, ShadoVariable> variables;

	public VirtualMachine()	{
		functions = new HashMap<>();
		variables = new HashMap<>();
	}

}
