/**
 * Shado Rectangle
 */

package shapes;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import sample.Mouse;
import shadoMath.Vector;
import shadoMath.Vertex;

import java.io.File;
import java.io.FileInputStream;
import java.util.function.Consumer;

public final class Shado {

	// Prevent initialization of a Shado object
	private Shado() {
	}

	public static class EventListener<E> {
		// Events
		protected Consumer<E> clickEvent;
		protected Consumer<E> hoverEvent;
		protected Consumer<E> mouseOutEvent;
		protected boolean outEventConsumed; // This variable is here to make sure that the onMouseOut event is applied
		// only once (and not every frame)

		protected EventListener() {
			// Initialize events
			clickEvent = null;
			hoverEvent = null;
			mouseOutEvent = null;
			outEventConsumed = false;
		}

		// Events setters
		public void onClick(Consumer<E> function) {
			clickEvent = function;
		}

		public void onMouseOver(Consumer<E> function) {
			hoverEvent = function;
		}

		public void onMouseOut(Consumer<E> function) {
			mouseOutEvent = function;
		}
	}

	public static abstract class Shape extends Shado.EventListener<Shado.Shape> implements Cloneable {
		protected Vertex position;
		protected Dimension dimensions;
		protected Color fill;
		protected Color stroke;
		protected int lineWidth;

		protected Shape() {
			super();
			position = new Vertex();
			dimensions = new Dimension();
			lineWidth = 1;
			fill = Color.TRANSPARENT;
			stroke = Color.BLACK;
		}

		/**
		 * Draws the calling rectangle to a JAVAFX Canvas
		 *
		 * @param g The GraphicsContext of the canvas
		 */
		public void draw(Canvas g) {

			// Handle click event
			if (clickEvent != null) {
				if (collides(Mouse.getLastClick().x, Mouse.getLastClick().y, 2, 2)) {
					clickEvent.accept(this);
					Mouse.resetLastClicked();
				}
			}

			// Handle Hover event
			if (hoverEvent != null) {
				if (collides(Mouse.getX(), Mouse.getY(), 2, 2)) {
					hoverEvent.accept(this);
					outEventConsumed = false;
				}
			}

			// Handle mouse out event
			if (mouseOutEvent != null) {
				if (!collides(Mouse.getX(), Mouse.getY(), 2, 2) && !outEventConsumed) {
					mouseOutEvent.accept(this);
					outEventConsumed = true;
				}
			}
		}

		/**
		 * @return Returns the area of the calling Shape
		 */
		public abstract double area();

		public void move(Vector velocity) {
			position.x += velocity.x;
			position.y += velocity.y;
		}

		public void move(double x, double y) {
			position.x += x;
			position.y += y;
		}

		// Collision
		public boolean collides(Shape other) {
			if (this instanceof Rectangle && other instanceof Rectangle) {
				return other.position.x + other.dimensions.width >= position.x
						&& other.position.x <= position.x + dimensions.width
						&& other.position.y + other.dimensions.height >= position.y
						&& other.position.y <= position.y + dimensions.height;
			} else if (this instanceof Circle && other instanceof Circle) {
				var centerO = other.getCenter();
				var centerT = this.getCenter();
				return centerO.getDistance(centerT) <= ((Circle) other).getRadius() + ((Circle) this).getRadius();
			} else if (this instanceof Rectangle && other instanceof Circle) {
				var hitBox = new Shado.Rectangle(other.position, other.dimensions);
				return this.collides(hitBox);
			} else {
				return false;
			}
		}

		public boolean collides(double x, double y, double w, double h) {
			return x + w >= position.x && x <= position.x + dimensions.width && y + h >= position.y
					&& y <= position.y + dimensions.height;
		}

		// Setters

		/**
		 * Changes the position of the shape
		 *
		 * @param pos The new position
		 * @return Returns the calling object
		 */
		public Shape setPosition(final Vertex pos) {
			this.position = new Vertex(pos);
			return this;
		}

		/**
		 * Changes the dimensions of the shape
		 *
		 * @param d The new dimensions
		 * @return Returns the calling object
		 */
		public Shape setDimensions(final Dimension d) {
			this.dimensions = new Dimension(d);
			return this;
		}

		/**
		 * Changes the Fill color of the calling object
		 *
		 * @param c The new Color
		 * @return Returns the calling object
		 */
		public Shape setFill(final Color c) {
			fill = new Color(c.getRed(), c.getGreen(), c.getBlue(), c.getOpacity());
			return this;
		}

		/**
		 * Changes the Stroke color of the calling object
		 *
		 * @param c The new stroke color
		 * @return Returns the calling object
		 */
		public Shape setStroke(final Color c) {
			stroke = new Color(c.getRed(), c.getGreen(), c.getBlue(), c.getOpacity());
			return this;
		}

		/**
		 * Changes the line width of the calling shape
		 *
		 * @param lineWidth The new line width
		 * @return Returns the calling object
		 */
		public Shape setLineWidth(int lineWidth) {
			this.lineWidth = lineWidth;
			return this;
		}

		public Shape noFill() {
			setFill(Color.rgb(0, 0, 0, 0));
			return this;
		}

		public Shape noStroke() {
			setStroke(Color.rgb(0, 0, 0, 0));
			return this;
		}

		// Overridden java.lang.Object functions
		public boolean equals(Object o) {
			if (o == null || o.getClass() != getClass()) {
				return false;
			} else {
				Shape s = (Shape) o;
				return dimensions.equals(s.dimensions) && position.equals(s.position);
			}
		}

		public String toString() {
			return String.format("%s (x: %f, y: %f, w: %f, h: %f)", getClass(), position.x, position.y,
					dimensions.width, dimensions.height);
		}

		public Shape clone() {
			try {
				Shape clone = (Shape) super.clone();
				clone.position = position.clone();
				clone.dimensions = dimensions.clone();
				clone.fill = new Color(fill.getRed(), fill.getGreen(), fill.getBlue(), fill.getOpacity());
				clone.stroke = new Color(stroke.getRed(), stroke.getGreen(), stroke.getBlue(), stroke.getOpacity());
				clone.lineWidth = lineWidth;
				return clone;
			} catch (CloneNotSupportedException e) {
				return null;
			}
		}

		// Getters

		/**
		 * @return Returns the position
		 */
		public Vertex getPosition() {
			return position;
		}

		public Vertex getCenter() {
			return new Vertex(position.x + dimensions.width / 2, position.y + dimensions.height / 2);
		}

		/**
		 * @return Returns the dimension of the shape
		 */
		public Dimension getDimensions() {
			return dimensions;
		}

		public double getAspectRatio() {
			return dimensions.width / dimensions.height;
		}
	}

	public static class Rectangle extends Shape {

		/**
		 * Initializes a Rectangle
		 *
		 * @param x The X position of the rectangle
		 * @param y The Y position of the rectangle
		 * @param w The width of the rectangle
		 * @param h The height of the rectangle
		 */
		public Rectangle(double x, double y, double w, double h) {
			super();
			position.x = x;
			position.y = y;
			dimensions.width = w;
			dimensions.height = h;
			fill = Color.rgb(255, 255, 255);
			stroke = Color.rgb(0, 0, 0);
		}

		public Rectangle(Vertex pos, Dimension d) {
			this(pos.x, pos.y, d.width, d.height);
		}

		/**
		 * Initializes a rectangle to (x: 0, y: 0, w: 100, h: 100)
		 */
		public Rectangle() {
			this(0, 0, 100, 100);
		}

		public Rectangle(final Rectangle other) {
			this(other.position, other.dimensions);
		}

		/**
		 * Draws the calling rectangle to a JAVAFX Canvas (Scales the shape depending on
		 * the canvas width and height)
		 *
		 * @param c The GraphicsContext of the canvas
		 */
		@Override
		public void draw(Canvas c) {

			GraphicsContext gc = c.getGraphicsContext2D();

			// Draw Shape
			gc.setFill(fill);
			gc.setStroke(stroke);
			gc.setLineWidth(lineWidth);

			gc.fillRect(position.x, position.y, dimensions.width, dimensions.height);
			gc.strokeRect(position.x, position.y, dimensions.width, dimensions.height);

			// Handle events by calling the draw in Shado.Shape
			super.draw(c);
		}

		/**
		 * @return Returns the area of the rectangle
		 */
		@Override
		public double area() {
			return dimensions.width * dimensions.height;
		}
	}

	public static class Circle extends Shape {

		public Circle(double x, double y, double r) {
			position.x = x;
			position.y = y;
			dimensions.width = r * 2;
			dimensions.height = r * 2;
		}

		public Circle(Vertex pos, Dimension d) {
			this(pos.x, pos.y, d.width / 2);
		}

		public Circle() {
			this(0, 0, 0);
		}

		public Circle(final Circle other) {
			this(other.position, other.dimensions);
		}

		@Override
		public void draw(Canvas c) {

			GraphicsContext gc = c.getGraphicsContext2D();

			gc.setFill(fill);
			gc.setStroke(stroke);
			gc.setLineWidth(lineWidth);

			gc.fillOval(position.x, position.y, dimensions.width, dimensions.height);
			gc.strokeOval(position.x, position.y, dimensions.width, dimensions.height);

			// Handle events by calling the draw in Shado.Shape
			super.draw(c);
		}

		@Override
		public double area() {
			return (float) (Math.PI * getRadius() * getRadius());
		}

		public double getRadius() {
			return dimensions.width / 2;
		}

		public Circle setRadius(float radius) {
			dimensions.width = radius * 2;
			dimensions.height = radius * 2;
			return this;
		}
	}

	public static class Line extends Shape {

		/**
		 * Initializes a Rectangle
		 *
		 * @param fromX The X position of the rectangle
		 * @param fromY The Y position of the rectangle
		 * @param toX   The width of the rectangle
		 * @param toY   The height of the rectangle
		 */
		public Line(double fromX, double fromY, double toX, double toY) {
			super();
			position.x = fromX;
			position.y = fromY;
			dimensions.width = toX;
			dimensions.height = toY;
			stroke = Color.rgb(0, 0, 0);
		}

		public Line(Vertex pos, Dimension d) {
			this(pos.x, pos.y, d.width, d.height);
		}

		/**
		 * Initializes a rectangle to (x: 0, y: 0, w: 100, h: 100)
		 */
		public Line() {
			this(0, 0, 100, 100);
		}

		public Line(final Line other) {
			this(other.position, other.dimensions);
		}

		/**
		 * Draws the calling rectangle to a JAVAFX Canvas
		 *
		 * @param c The canvas
		 */
		@Override
		public void draw(Canvas c) {

			GraphicsContext gc = c.getGraphicsContext2D();

			gc.setFill(fill);
			gc.setStroke(stroke);
			gc.setLineWidth(lineWidth);
			gc.strokeLine(position.x, position.y, dimensions.width, dimensions.height);

			// Handle events by calling the draw in Shado.Shape
			super.draw(c);
		}

		/**
		 * @return Returns the area of the rectangle
		 */
		@Override
		public double area() {
			return 0.0f;
		}
	}

	public static class Text extends Shape {

		private Font font;
		private String text;

		public Text(String content, double x, double y, Font font, Color fillColor, Color strokeColor) {
			super();
			this.text = content;
			position.x = x;
			position.y = y;
			this.font = font;
			fill = fillColor;
			stroke = strokeColor;
		}

		public Text(String content, double x, double y) {
			this(content, x, y, new Font("Arial", 16), Color.rgb(0, 0, 0), Color.rgb(0, 0, 0, 0));
		}

		public Text() {
			this("Sample text", 0.0f, 0.0f);
		}

		public Text(final Text other) {
			this(other.text, (float) other.position.x, (float) other.position.y, other.font, other.fill,
					other.stroke);
		}

		// Getters
		public double getWidth() {
			int lineLength = getBiggestLineLength();

			return lineLength * font.getSize();
		}

		private int getBiggestLineLength() {
			String[] temp = text.split("\n");

			int biggest = 0;
			for (String s : temp) {
				if (s.length() >= biggest) {
					biggest = s.length();
				}
			}
			return biggest;
		}

		// Setters

		/**
		 * Changes the font of the calling Text element
		 *
		 * @param newFont The new desired font
		 * @return Returns the calling object
		 */
		public Text setFont(Font newFont) {
			this.font = newFont;
			return this;
		}

		/**
		 * Changes the family of the calling Text element font's
		 *
		 * @param family The new font family
		 * @return Returns the calling object
		 */
		public Text setFontFamily(String family) {
			this.font = new Font(family, this.font.getSize());
			return this;
		}

		/**
		 * Changes the size of the calling Text element font's
		 *
		 * @param size The new font size
		 * @return Returns the calling object
		 */
		public Text setFontSize(int size) {
			this.font = new Font(this.font.getFamily(), size);
			return this;
		}

		/**
		 * Changes the Text content of the calling Text element
		 *
		 * @param text The new desired Text
		 * @return Returns the calling object
		 */
		public Text setText(String text) {
			this.text = text;
			return this;
		}

		@Override
		public void draw(Canvas c) {

			GraphicsContext g = c.getGraphicsContext2D();

			g.setFont(font);
			g.setFill(fill);
			g.setStroke(stroke);

			g.fillText(text, position.x, position.y);
			g.strokeText(text, position.x, position.y);

			// Handle events by calling the draw in Shado.Shape
			super.draw(c);
		}

		@Override
		public double area() {
			return 0.0f;
		}
	}

	public static class Image extends Shape {

		private FileInputStream path;
		private javafx.scene.image.Image img;
		private String source;
		private File file;

		public Image(String src, double x, double y, double w, double h) {
			position.x = x;
			position.y = y;
			dimensions.width = w;
			dimensions.height = h;
			source = src;

			// Get the absulte path and replace the disk name with "file"
			// So instead of "D:/path/"
			// We get "file:/path/"
			file = new File(src);
			img = new javafx.scene.image.Image(String.valueOf(file.toURI()));
		}

		/**
		 * @return Returns the absolute path of the Shado.Image with disk name replaced
		 * with "file:/"
		 */
		public String getImageAbsolutePath() {
			return String.valueOf(file.toURI());
		}

		@Override
		public void draw(Canvas c) {

			GraphicsContext g = c.getGraphicsContext2D();
			g.drawImage(img, position.x, position.y, dimensions.width, dimensions.height);

			// Handle events by calling the draw in Shado.Shape
			super.draw(c);
		}

		/**
		 * Changes the position of the shape
		 *
		 * @param pos The new position
		 * @return Returns the calling object
		 */
		@Override
		public Image setPosition(final Vertex pos) {
			this.position.x = pos.x;
			this.position.y = pos.y;
			return this;
		}

		/**
		 * Changes the dimensions of the shape
		 *
		 * @param d The new dimensions
		 * @return Returns the calling object
		 */
		@Override
		public Image setDimensions(final Dimension d) {
			this.dimensions.width = d.width;
			this.dimensions.height = d.height;
			img = new javafx.scene.image.Image(source, dimensions.width, dimensions.height, false, true);
			return this;
		}

		/**
		 * Changes the source of the drawn image
		 *
		 * @param path The new path of the image
		 * @return Returns the calling object
		 */
		public Image setSource(String path) {
			file = new File(path);
			img = new javafx.scene.image.Image(String.valueOf(file.toURI()));
			return this;
		}

		@Override
		public double area() {
			return dimensions.width * dimensions.width;
		}
	}
}
