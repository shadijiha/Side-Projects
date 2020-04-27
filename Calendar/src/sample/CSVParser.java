/**
 *
 */

package sample;

import javafx.scene.paint.Color;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CSVParser {

	private List<String> data;

	public CSVParser(String path) {
		File file = new File(path);
		data = new ArrayList<String>();

		try {
			Scanner scan = new Scanner(file);

			while (scan.hasNextLine()) {
				data.add(scan.nextLine());
			}

			scan.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void loadEvents() {

		for (String line : data) {

			String[] tokens = line.split(",");

			try {
				new Event(tokens[0].trim(),
						Event.Day.parseDay(tokens[1].trim()),
						Double.parseDouble(tokens[2].trim()),
						Double.parseDouble(tokens[3].trim()),
						hex2Rgb(tokens[4].trim()), tokens[5].trim());
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}


		}


	}

	private static Color hex2Rgb(String colorStr) {
		return new Color(
				(float) Integer.valueOf(colorStr.substring(1, 3), 16) / 255,
				(float) Integer.valueOf(colorStr.substring(3, 5), 16) / 255,
				(float) Integer.valueOf(colorStr.substring(5, 7), 16) / 255, 1);
	}
}
