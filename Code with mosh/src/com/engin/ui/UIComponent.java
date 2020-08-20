/**
 *
 */
package com.engin.ui;

import com.engin.math.Dimension;
import com.engin.math.*;
import jdk.jfr.*;

import java.awt.*;
import java.awt.event.*;
import java.util.function.*;

abstract public class UIComponent {

	protected final long id;

	protected Vector position;
	protected Dimension<Integer> dimension;

	// Properties
	protected Font font;
	protected Color borderColor;
	protected int borderWidth;
	protected Color fontColor;
	protected Color backgroundColor;

	// Mouse functions
	boolean hoverTriggered;
	private Consumer<MouseEvent> clickEvent;
	private Consumer<MouseEvent> hoverEvent;
	private Consumer<MouseEvent> outEvent;
	private Consumer<MouseEvent> moveEvent;


	/**
	 * Creats UI Component at position X, y, and with dimension W and H in PIXELS
	 * @param x The X position in pixels
	 * @param y The Y position in pixels
	 * @param w The width in pixels
	 * @param h The height in pixels
	 */
	protected UIComponent(float x, float y, float w, float h) {
		id = (long) (Math.random() * Long.MAX_VALUE);
		position = new Vector(x, y);
		dimension = new Dimension<Integer>((int) w, (int) h);

		font = new Font("Times New Roman", Font.PLAIN, 12);
		borderColor = Color.BLACK;
		borderWidth = 1;
		fontColor = Color.BLACK;
		backgroundColor = new Color(0, 0, 0, 0);

		clickEvent = (e) -> {
		};
		hoverEvent = (e) -> {
		};
		outEvent = (e) -> {
		};
		moveEvent = (e) -> {
		};

	}

	protected UIComponent() {
		this(0, 0, 0, 0);
	}

	protected UIComponent(final UIComponent other) {
		this(other.position.x, other.position.y, other.dimension.w, other.dimension.h);

		font = other.font;
		borderColor = other.borderColor;
		borderWidth = other.borderWidth;
		fontColor = other.fontColor;
		backgroundColor = other.backgroundColor;
	}

	/**
	 * Renders the UI element to the screen
	 * @param g The graphics context
	 */
	public abstract void render(Graphics g);

	// Events
	public void onClick(Consumer<MouseEvent> e) {
		clickEvent = e;
	}

	public void onMouseOver(Consumer<MouseEvent> e) {
		hoverEvent = e;
	}

	public void onMouseOut(Consumer<MouseEvent> e) {
		outEvent = e;
	}

	public void onMouseMove(Consumer<MouseEvent> e) {
		moveEvent = e;
	}

	// For internal use only
	@Description("Do not use this function. For internal use only")
	void mouseClicked(MouseEvent e) {
		clickEvent.accept(e);
	}

	@Description("Do not use this function. For internal use only")
	void mouseHovered(MouseEvent e) {
		hoverEvent.accept(e);
	}

	@Description("Do not use this function. For internal use only")
	void mouseExited(MouseEvent e) {
		outEvent.accept(e);
	}

	@Description("Do not use this function. For internal use only")
	void mouseMoved(MouseEvent e) {
		moveEvent.accept(e);
	}

	// Setters
	public void setPosition(Vector position) {
		this.position = position;
	}

	public void setDimensions(Dimension<Integer> dimension) {
		this.dimension = dimension;
	}

	public void setFont(Font font) {
		this.font = font;
	}

	public void setBorderColor(Color borderColor) {
		this.borderColor = borderColor;
	}

	public void setBorderWidth(int borderWidth) {
		this.borderWidth = borderWidth;
	}

	public void setColor(Color fontColor) {
		this.fontColor = fontColor;
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	/**
	 * @return Returns a deep copy of the position of the UI element
	 */
	public ImmutableVector getPosition() {
		return position.toImmutableVector();
	}

	/**
	 * @return Returns a deep copy of the dimensions of the UI element
	 */
	public Dimension<Integer> getDimensions() {
		return new Dimension<>(dimension);
	}

	public Font getFont() {
		return font;
	}

	public Color getBorderColor() {
		return borderColor;
	}

	public int getBorderWidth() {
		return borderWidth;
	}

	/**
	 * @return Returns the text color of the UI element
	 */
	public Color getColor() {
		return fontColor;
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	/**
	 * @return Returns the id of the component
	 */
	public long getId() {
		return id;
	}

	public String getComponentName() {
		return getClass().getSimpleName();
	}
}
