/**
 *
 */

package com.editor.html;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HTMLElement {

	protected String tag;
	protected String innerHTML;
	protected String id;
	protected Set<String> classes;
	protected Set<CSSProperty> css;
	protected Set<HTMLAttribute> attributes;
	protected List<HTMLElement> children;
	protected int indentation;

	public HTMLElement(String tag, String innerHTML) {
		this.tag = tag;
		this.classes = new HashSet<>();
		this.css = new HashSet<>();
		this.attributes = new HashSet<>();
		this.innerHTML = innerHTML;
		this.children = new ArrayList<>();

		this.id = "__element" + (int) (Math.random() * Long.MAX_VALUE);
	}

	public HTMLElement(String tag) {
		this(tag, "");
	}

	public HTMLElement() {
		this("p", "");
	}

	public String render() {

		StringBuilder att = new StringBuilder();    // For attributes
		StringBuilder cls = new StringBuilder();    // For classes
		StringBuilder sty = new StringBuilder();    // For styles
		StringBuilder chi = new StringBuilder();    // For children nodes

		// Compute the indentation
		String identation_str = "";
		for (int i = 0; i < indentation; i++)
			identation_str += "\t";

		// Children
		for (var child : children)
			chi.append(child.render());

		// Add this to the innerHTML
		this.innerHTML += chi.toString();

		// Build classes
		for (var _class : classes)
			cls.append(_class).append(' ');

		// Build attributes
		for (var _att : attributes)
			att.append(_att.attribute).append("=\"").append(_att.value).append("\" ");

		// Build styles
		for (var style : css)
			sty.append(style.attribute).append(": ").append(style.value).append("; ");

		// Build result
		return String.format("\n%s<%s id=\"%s\" class=\"%s\" style=\"%s\" %s>\n%s%s\n%s</%s>",
				identation_str, tag, id, cls.toString(), sty.toString(), att.toString(), identation_str, innerHTML, identation_str, tag);
	}

	public HTMLElement addClass(String className) {
		classes.add(className);
		return this;
	}

	public HTMLElement addStyle(CSSProperty property) {

		// Check if the property exists
		for (CSSProperty prop : css) {
			// If yes then replace the previous one
			if (prop.attribute.equals(property.attribute)) {
				css.remove(prop);
				css.add(property);
				return this;
			}
		}

		// No match found so add
		css.add(property);

		return this;
	}

	public HTMLElement addStyle(String prop, String val) {
		this.addStyle(new CSSProperty(prop, val));
		return this;
	}

	public HTMLElement addAttribute(HTMLAttribute attribute) {
		// Check if the attribute exists
		for (HTMLAttribute att : attributes) {
			// If yes then replace the previous one
			if (att.attribute.equals(att.attribute)) {
				attributes.remove(att);
				attributes.add(attribute);
			}
		}
		return this;
	}

	public HTMLElement addChild(HTMLElement child) {
		child.indentation = this.indentation + 1;
		children.add(child);
		return this;
	}

	/**
	 * Change HTML element ID
	 */
	public HTMLElement setID(String id) {
		this.id = id;
		return this;
	}

	/**
	 * @return Returns the ID of the element
	 */
	public String getID() {
		return id;
	}

	public boolean hasClass(String _class) {
		return classes.contains(_class);
	}

	public String getTag() {
		return tag;
	}
}
