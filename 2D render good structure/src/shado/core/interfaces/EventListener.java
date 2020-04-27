/**
 *
 */

package shado.core.interfaces;

import java.util.function.*;

public interface EventListener<T> extends Collidable {

	/**
	 * Sets a function for whenever mouse is clicked on the element
	 * @param function The function to execute
	 */
	public void onClick(Consumer<T> function);

	/**
	 * Sets a function for whenever mouse is over the element
	 * @param function The function to execute
	 */
	public void onMouseOver(Consumer<T> function);

	/**
	 * Sets a function for whenever mouse is out of the element
	 * @param function The function to execute
	 */
	public void onMouseOut(Consumer<T> function);

	/**
	 * Puts the out event consumed to true so it is not exceuted every
	 * @param status set the variable to true or false
	 */
	public void consumeOutEvent(boolean status);

	/**
	 * @return Returns the function for whenever mouse is clicked on the element
	 */
	public Consumer<T> clickEvent();

	/**
	 * @return Returns the function for whenever mouse is over the element
	 */
	public Consumer<T> hoverEvent();

	/**
	 * @return Returns the function for whenever mouse is out of the element
	 */
	public Consumer<T> outEvent();

	/**
	 * @return Returns if the out event was consumed or not
	 */
	public boolean outEventConsumed();
}
