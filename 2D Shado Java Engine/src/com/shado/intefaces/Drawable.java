/**
 * This interface is for all objects that can be drawn to the screen
 */

package com.shado.intefaces;

import com.shado.core.Renderer2D;

public interface Drawable {

	/**
	 * Draws the calling object to the window
	 *
	 * @param r The renderer used
	 */
	public void draw(Renderer2D r);
}
