package com.main;

import com.editor.*;
import com.editor.html.*;

import java.io.*;
import java.util.*;

public class Main {

	public static void main(String[] args) throws HTMLElementNotFoundException {

		// write your code here
		var document = new HTMLDocument();
		document.add(
				new HTMLElement("h1", "Hello from Shado editor")
						.addStyle(new CSSProperty("color", "green")));

		document.add(
				new HTMLElement("p", "This is a test of multiple elements in the editor")
						.addStyle(new CSSProperty("font-family", "Arial")));


		var body = document.querySelector("body").get(0);
		body.addStyle("background-color", "lightblue");

		body.setContent("This is body content");


		Editor editor = new Editor(document);
		editor.select(body);
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
