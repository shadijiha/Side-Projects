/**
 *
 */

package com.shado.core;

import com.shado.gfx.Text;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

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
		for (Text t : textBuffer) {
			g.setFont(t.getFont());
			g.setColor(t.getColor());
			g.drawString(t.getText(), t.getX(), t.getY());
		}

		bs.show();

		//TODO: should propebly cleat the textBuffer list
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
