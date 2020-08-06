/**
 *
 */
package com.editor;

public interface IRenderable {

	/**
	 * Transforms the object into a none abstract state
	 * @return Returns the value of the object after being rendered
	 * Note: The return can sometimes be ignored (as if it was void)
	 */
	public String render();
}
