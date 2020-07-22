/**
 *
 */

package com.graph;

import com.engin.Renderer;
import com.engin.Scene;

import java.awt.*;
import java.awt.event.MouseEvent;

public class EventTest extends Scene {

	private Tile[] tiles;
	private int gridWidth;
	private int gridHeight;

	private int currentX = -1;
	private int currentY = -1;

	private Tile selected;

	public EventTest() {
		super("Event test");
	}

	@Override
	public void init(Renderer r) {
		gridWidth = r.getWindow().getWidth() / Tile.DIMENSION;
		gridHeight = r.getWindow().getHeight() / Tile.DIMENSION;

		tiles = new Tile[gridWidth * gridHeight];

		for (int y = 0; y < gridHeight; y++) {
			for (int x = 0; x < gridWidth; x++) {
				tiles[y * gridWidth + x] = new Tile(x, y);
			}
		}
	}

	@Override
	public void update(long dt) {

	}

	@Override
	public void draw(Graphics g) {

		for (Tile tile : tiles) {
			if (tile.i == currentX && tile.j == currentY)
				selected = tile;

			if (selected == tile)
				g.setColor(Color.RED);
			else
				g.setColor(Color.WHITE);
			tile.draw(g);
		}

	}

	/**
	 * Invoked when the mouse cursor has been moved onto a component
	 * but no buttons have been pushed.
	 *
	 * @param e the event to be processed
	 */
	@Override
	public void mouseMoved(MouseEvent e) {
		currentX = e.getX() / Tile.DIMENSION;
		currentY = e.getY() / Tile.DIMENSION;
	}

	/**
	 * Invoked when the mouse button has been clicked (pressed
	 * and released) on a component.
	 *
	 * @param e the event to be processed
	 */
	@Override
	public void mouseClicked(MouseEvent e) {

		if (selected.isRevealed())
			return;

		// Reveal tile
		selected.reveal();
		// if it is a 0 then reveal all card with a random number
		if (selected.value == 0) {
			int num = (int) (Math.random() * 9) + 1; // Number to reveal
			for (Tile tile : tiles)
				if (tile.value == num)
					tile.reveal();
		}
	}
}
