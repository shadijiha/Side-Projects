/**
 *
 */

package com.shado.events;

import com.shado.core.GameContainer;

import java.awt.event.*;

public class Input implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {

	private GameContainer gc;

	private final int NUM_KEYS = 256;
	private boolean[] keys = new boolean[NUM_KEYS];
	private boolean[] keysLast = new boolean[NUM_KEYS];

	private final int NUM_BUTTONS = 10;
	private boolean[] buttons = new boolean[NUM_BUTTONS];
	private boolean[] buttonsLast = new boolean[NUM_BUTTONS];

	private int mouseX, mouseY;
	private int scroll;

	public Input(GameContainer gc) {
		this.gc = gc;
		mouseX = 0;
		mouseY = 0;
		scroll = 0;

		gc.getWindow().getCanvas().addKeyListener(this);
		gc.getWindow().getCanvas().addMouseMotionListener(this);
		gc.getWindow().getCanvas().addMouseListener(this);
		gc.getWindow().getCanvas().addMouseWheelListener(this);
	}

	/**
	 * Takes all current inputs and store them in the "Last" inputs
	 */
	public void update() {
		scroll = 0;

		for (int i = 0; i < NUM_KEYS; i++) {
			keysLast[i] = keys[i];
		}

		for (int i = 0; i < NUM_BUTTONS; i++) {
			buttonsLast[i] = buttons[i];
		}
	}

	public boolean isKey(int keyCode) {
		return keys[keyCode];
	}

	public boolean isKeyUp(int keyCode) {
		return !keys[keyCode] && keysLast[keyCode];
	}

	public boolean isKeyDown(int keyCode) {
		return keys[keyCode] && !keysLast[keyCode];
	}

	public boolean isButton(int button) {
		return buttons[button];
	}

	public boolean isButtonUp(int button) {
		return !buttons[button] && buttonsLast[button];
	}

	public boolean isButtonDown(int button) {
		return buttons[button] && !buttonsLast[button];
	}

	/**
	 * Invoked when a key has been typed.
	 * See the class description for {@link KeyEvent} for a definition of
	 * a key typed event.
	 *
	 * @param e the event to be processed
	 */
	@Override
	public void keyTyped(KeyEvent e) {

	}

	/**
	 * Invoked when a key has been pressed.
	 * See the class description for {@link KeyEvent} for a definition of
	 * a key pressed event.
	 *
	 * @param e the event to be processed
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
	}

	/**
	 * Invoked when a key has been released.
	 * See the class description for {@link KeyEvent} for a definition of
	 * a key released event.
	 *
	 * @param e the event to be processed
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}

	/**
	 * Invoked when the mouse button has been clicked (pressed
	 * and released) on a component.
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
		buttons[e.getButton()] = true;
	}

	/**
	 * Invoked when a mouse button has been released on a component.
	 *
	 * @param e the event to be processed
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		buttons[e.getButton()] = false;
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
	 * Invoked when a mouse button is pressed on a component and then
	 * dragged.  {@code MOUSE_DRAGGED} events will continue to be
	 * delivered to the component where the drag originated until the
	 * mouse button is released (regardless of whether the mouse position
	 * is within the bounds of the component).
	 * <p>
	 * Due to platform-dependent Drag&amp;Drop implementations,
	 * {@code MOUSE_DRAGGED} events may not be delivered during a native
	 * Drag&amp;Drop operation.
	 *
	 * @param e the event to be processed
	 */
	@Override
	public void mouseDragged(MouseEvent e) {
		mouseX = (int) (e.getX() / gc.getScale());
		mouseY = (int) (e.getY() / gc.getScale());
	}

	/**
	 * Invoked when the mouse cursor has been moved onto a component
	 * but no buttons have been pushed.
	 *
	 * @param e the event to be processed
	 */
	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = (int) (e.getX() / gc.getScale());
		mouseY = (int) (e.getY() / gc.getScale());
	}

	/**
	 * Invoked when the mouse wheel is rotated.
	 *
	 * @param e the event to be processed
	 * @see MouseWheelEvent
	 */
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		scroll = e.getWheelRotation();
	}

	// Getters and setters
	public int getMouseX() {
		return mouseX;
	}

	public int getMouseY() {
		return mouseY;
	}

	public int getScroll() {
		return scroll;
	}
}
