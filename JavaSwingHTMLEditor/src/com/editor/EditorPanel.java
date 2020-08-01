/**
 *
 */
package com.editor;

import com.editor.html.*;

import javax.swing.*;
import java.awt.*;

public class EditorPanel extends JPanel implements IRenderable {

	private HTMLElement selected;

	public EditorPanel(JFrame frame) {
		super();

		this.setPreferredSize(new Dimension((int) (frame.getWidth() * (1 - RenderPanel.RENDER_PANEL_SIZE)), frame.getHeight()));
		this.setLayout(new FlowLayout());
		this.setVisible(true);
	}

	public String render() {
		if (selected == null)
			throw new IllegalStateException("Cannot render the Editor panel if no selected item");

		this.removeAll();

		// Textfield
		JLabel innerHTML_label = new JLabel("inner HTML content");
		JTextField innerHTML = new JTextField(selected.content());

		this.add(innerHTML_label);
		this.add(innerHTML);

		return null;
	}

	public EditorPanel select(HTMLElement e) {
		this.selected = e;
		return this;
	}
}
