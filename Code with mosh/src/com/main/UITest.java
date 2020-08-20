/**
 *
 */
package com.main;

import com.engin.*;
import com.engin.ui.TextField;
import com.engin.ui.*;

import java.awt.*;

public class UITest extends Scene {

	//private UIComponent textField;

	public UITest() {
		super("UITest", 30);
	}

	/**
	 * Initializes the scene and its variables
	 *
	 * @param renderer The renderer that will handle the scene
	 */
	@Override
	public void init(Renderer renderer) {
		final var textField = new TextField(50, 50, 200, 50);
		textField.onClick(System.out::println);
		textField.onMouseOver(e -> {
			textField.setBackgroundColor(Color.RED);
		});
		textField.onMouseOut(e -> {
			textField.setBackgroundColor(Color.WHITE);
		});

		UIManager.addComponent(textField);
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

	}

}
