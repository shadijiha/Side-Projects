/**
 *
 */

package shado.core.interfaces;

import javafx.scene.canvas.*;

public interface Drawable {

	/**
	 * Allows the calling object to be drawn to the canvas
	 * @param r The renderer
	 */
	public void draw(Canvas r);
}
