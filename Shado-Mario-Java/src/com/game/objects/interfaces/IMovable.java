/**
 * Interface used for all objects that can move
 * This is important for camera later when all the scenes move to simulate a player moving
 */
package com.game.objects.interfaces;

public interface IMovable {

	/**
	 * Move int x, y by a certain amount
	 *
	 * @param x The x to move by
	 * @param y The y to move by
	 */
	public void move(int x, int y);

	/**
	 * Moves the object by 1 x and 1 y by default
	 */
	default public void move() {
		move(1, 1);
	}
}
