/**
 *
 */

package com.editor.html;

import com.editor.*;

import java.util.*;

public class HTMLElement implements IRenderable {

	protected String tag;
	protected String innerHTML;
	protected String content;
	protected String id;
	protected Set<String> classes;
	protected Set<CSSProperty> css;
	protected Set<HTMLAttribute> attributes;
	protected List<HTMLElement> children;
	protected int indentation;

	public HTMLElement(String tag, String content) {
		this.tag = tag;
		this.classes = new HashSet<>();
		this.css = new HashSet<>();
		this.attributes = new HashSet<>();
		this.content = content;
		this.innerHTML = content;
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
		this.innerHTML = content();
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

	public HTMLElement[] getChildNodes() {
		return getChildNodesHelper(new ArrayList<HTMLElement>(), this).toArray(HTMLElement[]::new);
	}

	private List<HTMLElement> getChildNodesHelper(List<HTMLElement> list, HTMLElement element) {

		if (element.children.size() == 0) {
		} else {
			list.addAll(element.children);
			for (HTMLElement temp : element.children)
				getChildNodesHelper(list, temp);

		}
		return list;
	}

	/**
	 * Change HTML element ID
	 */
	public HTMLElement setID(String id) {
		this.id = id;
		return this;
	}

	public HTMLElement setContent(String c) {
		this.content = c;
		return this;
	}

	/**
	 * @return Returns the ID of the element
	 */
	public String id() {
		return id;
	}

	public String tag() {
		return tag;
	}

	public String innerHTML() {
		return innerHTML;
	}

	public String content() {
		return content;
	}

	public boolean hasClass(String _class) {
		return classes.contains(_class);
	}

	/**
	 * Indicates whether some other object is "equal to" this one.
	 * <p>
	 * The {@code equals} method implements an equivalence relation
	 * on non-null object references:
	 * <ul>
	 * <li>It is <i>reflexive</i>: for any non-null reference value
	 *     {@code x}, {@code x.equals(x)} should return
	 *     {@code true}.
	 * <li>It is <i>symmetric</i>: for any non-null reference values
	 *     {@code x} and {@code y}, {@code x.equals(y)}
	 *     should return {@code true} if and only if
	 *     {@code y.equals(x)} returns {@code true}.
	 * <li>It is <i>transitive</i>: for any non-null reference values
	 *     {@code x}, {@code y}, and {@code z}, if
	 *     {@code x.equals(y)} returns {@code true} and
	 *     {@code y.equals(z)} returns {@code true}, then
	 *     {@code x.equals(z)} should return {@code true}.
	 * <li>It is <i>consistent</i>: for any non-null reference values
	 *     {@code x} and {@code y}, multiple invocations of
	 *     {@code x.equals(y)} consistently return {@code true}
	 *     or consistently return {@code false}, provided no
	 *     information used in {@code equals} comparisons on the
	 *     objects is modified.
	 * <li>For any non-null reference value {@code x},
	 *     {@code x.equals(null)} should return {@code false}.
	 * </ul>
	 * <p>
	 * The {@code equals} method for class {@code Object} implements
	 * the most discriminating possible equivalence relation on objects;
	 * that is, for any non-null reference values {@code x} and
	 * {@code y}, this method returns {@code true} if and only
	 * if {@code x} and {@code y} refer to the same object
	 * ({@code x == y} has the value {@code true}).
	 * <p>
	 * Note that it is generally necessary to override the {@code hashCode}
	 * method whenever this method is overridden, so as to maintain the
	 * general contract for the {@code hashCode} method, which states
	 * that equal objects must have equal hash codes.
	 *
	 * @param obj the reference object with which to compare.
	 * @return {@code true} if this object is the same as the obj
	 * argument; {@code false} otherwise.
	 * @see #hashCode()
	 * @see HashMap
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null || obj.getClass() != this.getClass()) {
			return false;
		} else {
			var o = (HTMLElement) obj;
			return o.tag.equals(tag) && o.id.equals(id) && o.content.equals(content) &&
					o.classes.equals(classes) && o.css.equals(css) &&
					o.attributes.equals(attributes) && o.children.equals(children);
		}
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
}
