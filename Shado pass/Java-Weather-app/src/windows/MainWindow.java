/**
 *
 */

package windows;

import driver.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.*;

public class MainWindow extends JFrame {

	private static final int WIDTH = 1280;
	private static final int HEIGHT = 720;

	private JTextField cityInputTextField;
	private JButton getInfoButton;
	private JPanel rootPanel;
	private JTextPane textPane1;

	public MainWindow() {
		super("Test");
		setPreferredSize(new Dimension(WIDTH, HEIGHT));

		setContentPane(rootPanel);

		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);

		init();

	}

	private void init() {

		// Clear the text field onClick
		cityInputTextField.addMouseListener(new MouseAdapter() {
			/**
			 * {@inheritDoc}
			 *
			 * @param e
			 */
			@Override
			public void mouseClicked(MouseEvent e) {
				((JTextField) e.getSource()).setText("");
			}
		});

		// Get weather data and display it
		getInfoButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				var data = Main.getWeatherData(cityInputTextField.getText().trim().toLowerCase());

				try {
					var text = data.exceptionally(err -> "Could not get weather data!").get();
					var temprature = Main.parserWeatherData(text);

					textPane1.setText(Integer.toString((int) (temprature.get() - 273)) + "°C");

				} catch (InterruptedException | ExecutionException ex) {
					ex.printStackTrace();
				}
			}
		});
	}
}
