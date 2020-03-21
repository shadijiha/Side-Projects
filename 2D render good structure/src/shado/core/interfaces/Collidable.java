/**
 *
 */

package shado.core.interfaces;

import shado.core.geometry.Circle;
import shado.core.geometry.Rectangle;
import shado.core.util.Vertex;

public interface Collidable {

	/**
	 * Checks if the calling object Collides with a rectangle
	 * @param rect The rectangle
	 * @return Returns true if they overlap and false otherwise
	 */
	public boolean collides(Rectangle rect);

	/**
	 * Checks if the calling object Collides with a Circle
	 * @param circle The rectangle
	 * @return Returns true if they overlap and false otherwise
	 */
	public boolean collides(Circle circle);

	/**
	 * Checks if the calling object Collides with a Vertex
	 * @param v The Vertex
	 * @return Returns true if they overlap and false otherwise
	 */
	public boolean collides(Vertex v);
}
