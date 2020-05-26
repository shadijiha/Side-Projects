/**
 *
 */

package com.shado.core;

import com.shado.gfx.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.util.List;
import java.util.*;

public class Window {

	private JFrame frame;
	private BufferedImage image;
	private Canvas canvas;
	private BufferStrategy bs;
	private Graphics g;

	private List<Text> textBuffer = new ArrayList<>();

	public Window(GameContainer gc) {
		image = new BufferedImage(gc.getWidth(), gc.getHeight(), BufferedImage.TYPE_INT_RGB);
		canvas = new Canvas();
		Dimension s = new Dimension((int) (gc.getWidth() * gc.getScale()), (int) (gc.getHeight() * gc.getScale()));
		canvas.setPreferredSize(s);
		canvas.setMaximumSize(s);
		canvas.setMinimumSize(s);

		frame = new JFrame(gc.getTitle());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(canvas, BorderLayout.CENTER);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);

		canvas.createBufferStrategy(2);
		bs = canvas.getBufferStrategy();
		g = bs.getDrawGraphics();
	}

	public void update() {
		g.drawImage(image, 0, 0, canvas.getWidth(), canvas.getHeight(), null);

		// Draw text
		// TODO: make this parallel
		for (Text t : textBuffer) {
			g.setFont(t.getFont());

			// Convert Shado.gfx.color to java.awt.Color
			String colorStr = Integer.toHexString(t.getColor());
			java.awt.Color c = new java.awt.Color(
					Integer.valueOf(colorStr.substring(2, 4), 16),
					Integer.valueOf(colorStr.substring(4, 6), 16),
					Integer.valueOf(colorStr.substring(6, 8), 16),
					Integer.valueOf(colorStr.substring(0, 2), 16));

			g.setColor(c);
			g.drawString(t.getText(), t.getX(), t.getY());
		}

		bs.show();

		// Clear the textBuffer list
		textBuffer.clear();
	}

	/**
	 * Adds a Text to draw to the screen in the Queue (This method should not be called by the client)
	 * @param t The text to add
	 */
	public void addText(Text t) {
		textBuffer.add(t);
	}

	public Canvas getCanvas() {
		return canvas;
	}

	public BufferedImage getImage() {
		return image;
	}

	public JFrame getFrame() {
		return frame;
	}
}
