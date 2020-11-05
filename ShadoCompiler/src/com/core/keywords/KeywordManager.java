/**
 *
 */
package com.core.keywords;

import java.util.*;

public abstract class KeywordManager {

	protected static List<Keyword> keywords = new ArrayList<>();

	public static void init()	{

		new IntKeyword();
		new DoubleKeyword();
		new VoidKeyword();

	}
}
