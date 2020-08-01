/**
 *
 */
package com.editor;

import com.editor.html.*;

import javax.swing.*;
import java.awt.*;

public class RenderPanel extends JEditorPane {

	public final static float RENDER_PANEL_SIZE = 0.8f;

	private HTMLDocument document;

	/**
	 *
	 */
	public RenderPanel(JFrame frame, HTMLDocument document) {
		super("text/html", document.render());

		this.document = document;

		this.setPreferredSize(new Dimension((int) (frame.getWidth() * RENDER_PANEL_SIZE), frame.getHeight()));
		this.setFocusable(false);
		this.setVisible(true);
	}

	protected void send(HTMLDocument document) {
		this.document = document;
		this.setText(document.render());
		this.repaint();
		this.setVisible(true);
	}
}
