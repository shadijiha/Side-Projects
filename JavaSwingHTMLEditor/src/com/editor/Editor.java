/**
 *
 */
package com.editor;

import com.editor.html.*;

import javax.swing.*;
import java.awt.*;

public class Editor extends JFrame {

	private RenderPanel renderPanel;

	private HTMLDocument currentDocuement;

	/**
	 *
	 */
	public Editor(HTMLDocument currentDocuement) {

		this.renderPanel = new RenderPanel(this, currentDocuement);
		this.currentDocuement = currentDocuement;

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {

				JFrame frame = new JFrame("HTML editor");

				frame.setPreferredSize(new Dimension(1280, 720));
				frame.setLayout(new GridLayout());
				frame.add(renderPanel);
				frame.pack();
				frame.setVisible(true);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setLocationRelativeTo(null);
			}
		});

		//JEditorPane ed1 = new JEditorPane("text/html", html);
		//ed1.setFocusable(false);
		//frame.add(ed1);
	}

	public void submit(HTMLDocument doc) {
		this.currentDocuement = doc;
		this.renderPanel.send(doc);
	}
}
