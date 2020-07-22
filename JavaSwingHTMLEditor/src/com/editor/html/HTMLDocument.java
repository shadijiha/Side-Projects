/**
 *
 */
package com.editor.html;

import java.util.ArrayList;
import java.util.List;

public class HTMLDocument {

	private final List<HTMLElement> elements;
	private String title;

	private HTMLElement html;
	private HTMLElement head;
	private HTMLElement body;

	public HTMLDocument(String title) {
		this.elements = new ArrayList<>();
		this.title = title;

		this.html = new HTMLElement("html");
		this.head = new HTMLElement("head");
		this.body = new HTMLElement("body");

		html.addChild(this.head);
		html.addChild(this.body);

		head.addChild(new HTMLElement("title", this.title));
	}

	public HTMLDocument() {
		this("TITLE");
	}

	public String render() {
		return html.render();
	}

	public void add(HTMLElement e) {
		//elements.add(e);
		body.addChild(e);
	}

	public List<HTMLElement> querySelector(String query) {

		if (query.startsWith("#"))
			return List.of(getElementById(query.substring(1, query.length() - 1)));
		else if (query.startsWith("."))
			return getElementsByClassName(query.substring(1, query.length() - 1));
		else {
			List<HTMLElement> result = new ArrayList<>();

			for (var e : elements)
				if (e.getTag().equals(query))
					result.add(e);

			return result;
		}
	}

	public HTMLElement getElementById(String id) {
		for (var e : elements)
			if (e.getID().equals(id))
				return e;
		return null;
	}

	public List<HTMLElement> getElementsByClassName(String className) {
		List<HTMLElement> result = new ArrayList<>();

		for (var e : elements)
			if (e.hasClass(className))
				result.add(e);

		return result;
	}
}
