/**
 *
 */

package shado.core.interfaces;

import shado.core.util.Vec3;

public interface Movable {

	/**
	 * Overrides the position of the calling object
	 * @param v The new Position
	 */
	public void setPosition(Vec3 v);

	/**
	 * @return Returns the position of the calling object
	 */
	public Vec3 getPosition();

	/**
	 * Moves the calling object by a certain offset
	 * @param x The X offset
	 * @param y The Y offset
	 */
	public void move(float x, float y);
}
