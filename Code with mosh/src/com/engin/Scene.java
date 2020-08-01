package com.engin;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

public abstract class Scene implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener,
		Serializable, Comparable<Scene> {

	public static final long serialVersionUID = 54745456474142L;

	private final long id;
	private final String name;
	protected int zIndex;

	public Scene(String name, int z_index) {
		this.name = name;
		this.id = (long) (Math.random() * Long.MAX_VALUE);
		this.zIndex = z_index;
	}

	public Scene(String name) {
		this(name, 0);
	}

	/**
	 * Initializes the scene and its variables
	 *
	 * @param renderer The renderer that will handle the scene
	 */
	public abstract void init(Renderer renderer);

	/**
	 * Updates the state of the scene
	 *
	 * @param dt The time between 2 frames in ms
	 */
	public abstract void update(final float dt);

	/**
	 * Draws the scene content to the screen
	 *
	 * @param g The graphics that will handle the drawing
	 */
	public abstract void draw(final Graphics g);

	/**
	 * @return Returns the ID of scene
	 */
	public final long getId() {
		return id;
	}

	/**
	 * @return Returns the name of scene
	 */
	public final String getName() {
		return name;
	}

	/**
	 * @return Returns the z-index of scene
	 */
	public final int getzIndex() {
		return zIndex;
	}

	/**
	 * Compares 2 scenes based on their Z-index
	 *
	 * @param o The other scene
	 * @return a negative integer, zero, or a positive integer as this object is
	 *         less than, equal to, or greater than the specified object.
	 */
	public final int compareTo(Scene o) {
		return Integer.compare(zIndex, o.zIndex);
	}

	/**
	 * Invoked when a key has been typed. See the class description for
	 * {@link KeyEvent} for a definition of a key typed event.
	 *
	 * @param e the event to be processed
	 */
	@Override
	public void keyTyped(KeyEvent e) {

	}

	/**
	 * Invoked when a key has been pressed. See the class description for
	 * {@link KeyEvent} for a definition of a key pressed event.
	 *
	 * @param e the event to be processed
	 */
	@Override
	public void keyPressed(KeyEvent e) {

	}

	/**
	 * Invoked when a key has been released. See the class description for
	 * {@link KeyEvent} for a definition of a key released event.
	 *
	 * @param e the event to be processed
	 */
	@Override
	public void keyReleased(KeyEvent e) {

	}

	/**
	 * Invoked when the mouse button has been clicked (pressed and released) on a
	 * component.
	 *
	 * @param e the event to be processed
	 */
	@Override
	public void mouseClicked(MouseEvent e) {

	}

	/**
	 * Invoked when a mouse button has been pressed on a component.
	 *
	 * @param e the event to be processed
	 */
	@Override
	public void mousePressed(MouseEvent e) {

	}

	/**
	 * Invoked when a mouse button has been released on a component.
	 *
	 * @param e the event to be processed
	 */
	@Override
	public void mouseReleased(MouseEvent e) {

	}

	/**
	 * Invoked when the mouse enters a component.
	 *
	 * @param e the event to be processed
	 */
	@Override
	public void mouseEntered(MouseEvent e) {

	}

	/**
	 * Invoked when the mouse exits a component.
	 *
	 * @param e the event to be processed
	 */
	@Override
	public void mouseExited(MouseEvent e) {

	}

	/**
	 * Invoked when a mouse button is pressed on a component and then dragged.
	 * {@code MOUSE_DRAGGED} events will continue to be delivered to the component
	 * where the drag originated until the mouse button is released (regardless of
	 * whether the mouse position is within the bounds of the component).
	 * <p>
	 * Due to platform-dependent Drag&amp;Drop implementations,
	 * {@code MOUSE_DRAGGED} events may not be delivered during a native
	 * Drag&amp;Drop operation.
	 *
	 * @param e the event to be processed
	 */
	@Override
	public void mouseDragged(MouseEvent e) {

	}

	/**
	 * Invoked when the mouse cursor has been moved onto a component but no buttons
	 * have been pushed.
	 *
	 * @param e the event to be processed
	 */
	@Override
	public void mouseMoved(MouseEvent e) {

	}

	/**
	 * Invoked when the mouse wheel is rotated.
	 *
	 * @param e the event to be processed
	 * @see MouseWheelEvent
	 */
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {

	}
}
