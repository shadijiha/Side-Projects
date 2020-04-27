/**
 *
 */

package windows;

import resources.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.*;

public class MainWindow extends JFrame {

	private JLabel numberOfTask;
	private JPanel mainPanel;
	private JScrollPane dataPane;

	private List<TodoTask> tasks;

	public MainWindow() {
		super("Shado TODO app");

		setPreferredSize(new Dimension(1280, 720));

		setContentPane(mainPanel);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);

		tasks = new ArrayList<TodoTask>();
		init();
	}

	private void init() {

		// Load TODOs
		tasks = TodoTask.loadFromFile();

		// If error
		if (tasks == null) {
			JOptionPane.showMessageDialog(this, "Could not load TODO tasks from " + TodoTask.DEFAULT_FILENAME + ". (IO error)");
			tasks = new ArrayList<>();
		}

		// Show how many items there are
		numberOfTask.setText("You have " + tasks.size() + " tasks");

		for (TodoTask t : tasks) {
			//mainPanel.add(t.render());
		}


	}

}
