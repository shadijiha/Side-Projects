/**
 *
 */
package com.editor;

import javax.swing.*;
import java.awt.*;

public class EditorPanel extends JPanel {

	public EditorPanel(JFrame frame) {
		super();

		setPreferredSize(new Dimension((int) (frame.getWidth() * (1 - RenderPanel.RENDER_PANEL_SIZE)), frame.getHeight()));
	}
}
