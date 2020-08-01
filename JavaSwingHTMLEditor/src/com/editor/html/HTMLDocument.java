/**
 *
 */
package com.editor.html;

import com.editor.*;

import java.util.*;

public class HTMLDocument implements IRenderable {


	//private final List<HTMLElement> elements;
	private String title;

	private final HTMLElement html;
	private final HTMLElement head;
	private final HTMLElement body;
	private final StyleTag style;

	public HTMLDocument(String title) {
		//this.elements = new ArrayList<>();
		this.title = title;

		this.html = new HTMLElement("html");
		this.head = new HTMLElement("head");
		this.body = new HTMLElement("body");
		this.style = new StyleTag();

		html.addChild(this.head);
		html.addChild(this.body);
		head.addChild(this.style);
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

	public List<HTMLElement> querySelector(String query) throws HTMLElementNotFoundException {

		if (query.startsWith("#"))
			return List.of(getElementById(query.substring(1, query.length() - 1)));
		else if (query.startsWith("."))
			return getElementsByClassName(query.substring(1, query.length() - 1));
		else {
			List<HTMLElement> result = new ArrayList<>();

			// Search the the document
			var all_nodes = html.getChildNodes();
			for (var e : all_nodes)
				if (e.tag().equals(query))
					result.add(e);

			if (result.isEmpty())
				throw new HTMLElementNotFoundException();

			return result;
		}
	}

	public HTMLElement getElementById(String id) throws HTMLElementNotFoundException {

		// Search the the document
		var allNodes = html.getChildNodes();
		for (var e : allNodes)
			if (e.id().equals(id))
				return e;

		throw new HTMLElementNotFoundException();
	}

	public List<HTMLElement> getElementsByClassName(String className) throws HTMLElementNotFoundException {
		List<HTMLElement> result = new ArrayList<>();

		// Search the the document
		var all_nodes = html.getChildNodes();
		for (var e : all_nodes)
			if (e.hasClass(className))
				result.add(e);

		if (result.isEmpty())
			throw new HTMLElementNotFoundException();

		return result;
	}

	public boolean contains(HTMLElement element) {
		for (var e : html.getChildNodes())
			if (e.equals(element))
				return true;
		return false;
	}

	/**
	 * Returns a string representation of the object. In general, the
	 * {@code toString} method returns a string that
	 * "textually represents" this object. The result should
	 * be a concise but informative representation that is easy for a
	 * person to read.
	 * It is recommended that all subclasses override this method.
	 * <p>
	 * The {@code toString} method for class {@code Object}
	 * returns a string consisting of the name of the class of which the
	 * object is an instance, the at-sign character `{@code @}', and
	 * the unsigned hexadecimal representation of the hash code of the
	 * object. In other words, this method returns a string equal to the
	 * value of:
	 * <blockquote>
	 * <pre>
	 * getClass().getName() + '@' + Integer.toHexString(hashCode())
	 * </pre></blockquote>
	 *
	 * @return a string representation of the object.
	 */
	@Override
	public String toString() {
		return this.render();
	}

	public HTMLElement getBody() {
		return body;
	}

	public StyleTag getStyle() {
		return this.style;
	}
}
