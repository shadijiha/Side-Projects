package com.main;

import com.editor.Editor;
import com.editor.html.CSSProperty;
import com.editor.html.HTMLDocument;
import com.editor.html.HTMLElement;

import java.io.FileInputStream;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		// write your code here
		//final String html = readHTML("index.html");
		var document = new HTMLDocument();
		document.add(
				new HTMLElement("h1", "Hello from Shado editor")
						.addStyle(new CSSProperty("color", "green")));

		//System.out.println(document.querySelector("body"));
		//document.querySelector("body").get(0).addStyle("background-color", "lightblue");

		System.out.println(document.render());

		Editor editor = new Editor(document);
	}

	public static String readHTML(String path) {

		try {
			StringBuilder builder = new StringBuilder();
			Scanner scanner = new Scanner(new FileInputStream(path));

			while (scanner.hasNextLine()) {
				builder.append(scanner.nextLine()).append("\n");
			}

			scanner.close();

			return builder.toString();

		} catch (Exception e) {
			return null;
		}

	}
}
