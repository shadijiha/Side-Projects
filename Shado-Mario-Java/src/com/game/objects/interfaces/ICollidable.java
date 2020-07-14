/**
 *
 */

package com.game.objects.interfaces;

public interface ICollidable {

	/**
	 * Evalutes if an object is colliding another object
	 *
	 * @param other The other object to evaulat with
	 * @return Returns true if the 2 objects are in collision
	 */
	public boolean collides(ICollidable other);

	/**
	 * @return Returns the position of the object in X
	 */
	public int getX();

	/**
	 * @return Returns the position of the object in Y
	 */
	public int getY();

	/**
	 * @return Returns the width of the object
	 */
	public int getWidth();

	/**
	 * @return Returns the height of the object
	 */
	public int getHeight();
}
