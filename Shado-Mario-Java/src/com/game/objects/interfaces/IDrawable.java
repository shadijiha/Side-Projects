/**
 * An object that can be drawn to the screen should implement this interface and have
 * a Texture attribute
 */
package com.game.objects.interfaces;

import java.awt.*;

public interface IDrawable {

	/**
	 * Draws the object to the screen
	 *
	 * @param g The graphics context
	 */
	public void draw(Graphics g);
}
