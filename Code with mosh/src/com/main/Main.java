package com.main;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) throws IOException {
		// write your code here
		int[] data = readVideo("test.avi");
		var image = createVideo(data);

		var window = createWindow(image.getWidth(null), image.getHeight(null));

		window.add(new JPanel() {

			@Override
			protected void paintComponent(Graphics g) {
				g.drawImage(image, 0, 0, null);
			}
		});

		window.pack();
	}

	public static int[] readVideo(String path) throws IOException {
		File video = new File(path);
		List<Integer> data = new ArrayList<>();

		BufferedReader reader = new BufferedReader(new FileReader(video));

		int d;
		while ((d = reader.read()) != -1 && data.size() < 400_000) {
			if (d <= 255)
				data.add(d);
		}

		reader.close();

		// Convert to array
		int array[] = new int[data.size()];
		for (int i = 0; i < array.length; i++)
			array[i] = data.get(i);

		return array;
	}

	public static Image createVideo(int data[]) {

		var resolution = new Dimension(640, 480);
		int pixelCount = resolution.width * resolution.height;
		float framesInData = data.length / (float) pixelCount;

		// First frame
		int frameData[] = new int[pixelCount];
		for (int i = 0; i < frameData.length; i++)
			frameData[i] = data[i];

		// Construct Image
		Image image = new BufferedImage(resolution.width, resolution.height, BufferedImage.TYPE_INT_ARGB);
		var g = image.getGraphics();

		int y = 0;
		for (int i = 0; i < frameData.length; i += 3) {
			g.setColor(new Color(frameData[i], frameData[i + 1], frameData[i + 2]));
			g.drawRect(i % resolution.width, y, 1, 1);

			if (i % resolution.width == 0)
				y++;
		}

		g.dispose();

		return image;
	}

	public static JFrame createWindow(int width, int height) {
		JFrame frame = new JFrame();

		frame.setPreferredSize(new Dimension(width, height));
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		return frame;
	}
}
