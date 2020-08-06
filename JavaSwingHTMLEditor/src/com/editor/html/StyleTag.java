/**
 *
 */
package com.editor.html;

import java.util.*;

public class StyleTag extends HTMLElement {

	private Map<String, Set<CSSProperty>> components;
	private List<String> declarations;

	public StyleTag() {
		super("style");

		components = new HashMap<>();
		declarations = new ArrayList<>();
	}

	@Override
	public String render() {

		StringBuilder builder = new StringBuilder();

		for (String s : declarations)
			builder.append(declarations).append("\n\n");

		// Now add all attrubites
		for (Map.Entry<String, Set<CSSProperty>> entry : components.entrySet()) {

			StringBuilder temp = new StringBuilder();
			temp.append(entry.getKey()).append("\t{\n");

			for (CSSProperty prop : entry.getValue())
				temp.append(prop.attribute).append(": ").append(prop.value).append(";\n");

			temp.append("}\n\n");

			builder.append(temp.toString());
		}

		return builder.toString();
	}

	@Deprecated
	@Override
	public HTMLElement addChild(HTMLElement child) {
		throw new RuntimeException("Cannot add child to <style> element");
	}

	/**
	 * Add a class/id/etc name with a property
	 * @param cssAttribute The name of the class,id, etc
	 * @param prop  The css property
	 *
	 * @apiNote
	 * if you want to add:	.red	{color: red;}
	 * you type: StyleTagObject.add(".red", new CSSProperty("color", "red"));
	 *
	 * if you want to re-add another property to your "red" class, simply type:
	 * StyleTagObject.add("red", new CSSProperty("font-size", "16px"));
	 * and it will automatically be appended
	 */
	public void add(String cssAttribute, CSSProperty prop) {

		cssAttribute = cssAttribute.trim();

		// If it already exists
		if (components.containsKey(cssAttribute)) {
			components.get(cssAttribute).add(new CSSProperty(prop));
			return;
		}

		// If it doesn't exist
		components.put(cssAttribute, new HashSet<>());
		components.get(cssAttribute).add(new CSSProperty(prop));
	}

	/**
	 * add a whole css block the the <code>style</code> tag. Example
	 *
	 * #top	{
	 *     color: green;
	 *     font-size: 14px;
	 *     font-family: "Arial";
	 * }
	 *
	 * @param wholeCSSBlock The block as string
	 */
	public void add(String wholeCSSBlock) {

		// If block starts with "@" when simply just put it in the declarations
		if (wholeCSSBlock.startsWith("@")) {
			declarations.add(wholeCSSBlock);
			return;
		}

		// Otherwise parse the block
		String tokens[] = wholeCSSBlock.split("[{}]");

		try {

			String code[] = tokens[1].trim().split(";");
			for (String line : code) {
				String[] prop = line.split(":");

				add(tokens[0].trim(), new CSSProperty(prop[0].trim(), prop[1].trim()));
			}

		} catch (ArrayIndexOutOfBoundsException e) {
			throw new RuntimeException("An error has occurred while parsing CSS block " + wholeCSSBlock + "\n\nUnexpected " + e.getCause().toString());
		}

	}

	/**
	 *
	 * @param attribute The attribute. Example ".red", "#top", div
	 * @return Returns a Set of the CSS properties of that attribute
	 */
	public Set<CSSProperty> get(String attribute) {
		return components.get(attribute);
	}

	@Deprecated
	@Override
	public HTMLElement addClass(String className) {
		throw new RuntimeException("Cannot add child to <style> element");
	}

	@Deprecated
	@Override
	public HTMLElement addStyle(CSSProperty property) {
		throw new RuntimeException("Cannot add child to <style> element");
	}

	@Deprecated
	@Override
	public HTMLElement addStyle(String prop, String val) {
		throw new RuntimeException("Cannot add child to <style> element");
	}
}
