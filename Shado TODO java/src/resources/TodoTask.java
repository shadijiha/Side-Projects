/**
 *
 */

package resources;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.List;
import java.util.*;

public class TodoTask implements Serializable {

	public static final String DEFAULT_FILENAME = "todos";
	private static final long serialVersionUID = 61446141894281535L;

	private Date due;
	private String description;
	private SerializableColor color;
	private long id;

	public TodoTask(String description, Date due, SerializableColor color) {
		this.due = due;
		this.description = description;
		this.color = color;
		this.id = (long) (Math.random() * Long.MAX_VALUE);
	}

	public static List<TodoTask> loadFromFile(String filename) {

		// Load Async
		List<TodoTask> list = new ArrayList<>();

		boolean cont = true;
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {

			while (cont) {
				Object obj = null;
				try {
					obj = ois.readObject();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
					return null;
				}
				if (obj != null)
					list.add((TodoTask) obj);
				else
					cont = false;
			}

			ois.close();
		} catch (EOFException e) {
			return list;
		} catch (IOException e) {

			// Create the file if it doesn't exist
			ObjectOutputStream ois = null;
			try {
				ois = new ObjectOutputStream(new FileOutputStream(filename));
				ois.close();

				// Restart
				loadFromFile(filename);
			} catch (IOException ex) {
				return null;
			}
		}

		return list;
	}

	public static List<TodoTask> loadFromFile() {
		return loadFromFile(DEFAULT_FILENAME);
	}

	public static boolean writeToFile(TodoTask task, String filename) {

		// Load previous content of the file
		List<TodoTask> list = loadFromFile(filename);

		// Add the object to add to the list
		list.add(task);

		// Write all objects to the file
		ObjectOutputStream stream = null;
		try {
			stream = new ObjectOutputStream(new FileOutputStream(filename));

			for (TodoTask e : list)
				stream.writeObject(e);

			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static boolean writeToFile(TodoTask task) {
		return writeToFile(task, DEFAULT_FILENAME);
	}

	public JPanel render() {

		JPanel panel = new JPanel(new FlowLayout());

		JLabel description_label = new JLabel(description);
		JLabel date_label = new JLabel(due.toString());
		JLabel color_label = new JLabel("X");

		color_label.setBackground(color);
		color_label.setForeground(color);

		panel.add(description_label);
		panel.add(date_label);
		panel.add(color_label);

		return panel;
	}

	// Override methodes
	@Override
	public boolean equals(Object o) {
		if (o == null || o.getClass() != getClass()) {
			return false;
		} else {
			TodoTask task = (TodoTask) o;
			return task.due.equals(due) && task.description.equals(description) && task.color.equals(color);
		}
	}

	@Override
	public String toString() {
		return "TodoTask{" +
				"due=" + due +
				", description='" + description + '\'' +
				", color=" + color +
				", id=" + id +
				'}';
	}

	public Date getDue() {
		return due;
	}

	public void setDue(Date due) {
		this.due = due;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public SerializableColor getColor() {
		return color;
	}

	public void setColor(SerializableColor color) {
		this.color = color;
	}

	public long getId() {
		return id;
	}
}
