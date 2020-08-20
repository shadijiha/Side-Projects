/**
 *
 */
package com.engin.ui;

import com.engin.*;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.*;

public final class UIManager extends Scene {

	public static final UIManager singleton = new UIManager();

	private static final long serialVersionUID = 5667995755940048705L;

	private List<UIComponent> components;

	private UIManager() {
		super("UI Manager Scene", 30);

		components = new ArrayList<>();
	}

	public static void addComponent(UIComponent component) {
		singleton.components.add(component);
	}

	public static void removeComponent(UIComponent component) {
		singleton.components.remove(component);
	}

	public static void removeComponent(long id) {
		for (int i = 0; i < singleton.components.size(); i++)
			if (singleton.components.get(i).getId() == id)
				singleton.components.remove(i);
	}

	/**
	 * Initializes the scene and its variables
	 *
	 * @param renderer The renderer that will handle the scene
	 */
	@Override
	public void init(Renderer renderer) {

	}

	/**
	 * Updates the state of the scene
	 *
	 * @param dt The time between 2 frames in ms
	 */
	@Override
	public void update(float dt) {

	}

	/**
	 * Draws the scene content to the screen
	 *
	 * @param g The graphics that will handle the drawing
	 */
	@Override
	public void draw(Graphics g) {

		for (UIComponent component : components)
			component.render(g);

	}

	// Handle events

	/**
	 * Invoked when the mouse button has been clicked (pressed and released) on a
	 * component.
	 *
	 * @param e the event to be processed
	 */
	@Override
	public void mouseClicked(MouseEvent e) {

		for (UIComponent component : components) {
			if (collision(e.getX(), e.getY(), component)) {
				System.out.println(e + "\t" + component.dimension);
				component.mouseClicked(e);
			}

		}

	}

	/**
	 * Invoked when the mouse cursor has been moved onto a component but no buttons
	 * have been pushed.
	 *
	 * @param e the event to be processed
	 */
	@Override
	public void mouseMoved(MouseEvent e) {

		// Move event
		for (UIComponent component : components) {
			if (collision(e.getX(), e.getY(), component)) {
				component.mouseMoved(e);
			}

		}

		// Hover event
		for (UIComponent component : components) {
			if (!component.hoverTriggered && collision(e.getX(), e.getY(), component)) {
				component.mouseHovered(e);
				component.hoverTriggered = true;
			}
		}

		// Out event
		for (UIComponent component : components) {
			if (component.hoverTriggered && !collision(e.getX(), e.getY(), component)) {
				component.mouseExited(e);
				component.hoverTriggered = false;
			}
		}


	}

	private static boolean collision(int x, int y, UIComponent ui) {
		return (x < ui.position.x + ui.dimension.w &&
				x + 5 > ui.position.x &&
				y < ui.position.y + ui.dimension.h &&
				y + 5 > ui.position.y);
	}
}
