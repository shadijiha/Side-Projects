/**
 *
 */

package com.shado.intefaces;

import com.shado.math.*;

public interface Movable {

	/**
	 * Moves the calling object by a certain amount
	 * @param amount The amount the move the object
	 */
	public void move(Vec amount);

	/**
	 * Moves the calling object by a certain default amount
	 */
	public void move();
}
