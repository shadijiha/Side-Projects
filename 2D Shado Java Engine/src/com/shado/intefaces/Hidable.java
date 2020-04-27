/**
 *
 */

package com.shado.intefaces;

public interface Hidable {

	/**
	 * Returns if the calling object is hidden or not
	 */
	public boolean isHidden();

	/**
	 * Allows the calling object to be sent to the renderer
	 */
	public void show();

	/**
	 * Prevents the calling object from being sent to the renderer
	 */
	public void hide();
}
