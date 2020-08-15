/**
 *
 */
package com.engin;

import com.engin.components.*;

import java.util.*;

public final class GameObject {

	private String name;
	private final Set<String> tags;
	private final int id;
	private final Set<EntityComponent> components;

	public GameObject(String name) {
		this.name = name;
		this.tags = new HashSet<>();
		this.id = Util.random(0, Integer.MAX_VALUE);
		this.components = new HashSet<>();

		components.add(new Transform(this));
	}

	/**
	 * Adds a new Component to the object
	 *
	 * @param component the new component to add
	 */
	public final void addComponent(EntityComponent component) {
		components.add(component);
	}

	/**
	 * Removes a component from the Game object
	 *
	 * @param <T> The component class to remove
	 * @return Returns the deleted component
	 */
	@Deprecated
	public final <T extends EntityComponent> T removeComponent() {
		for (EntityComponent e : components) {
			try {
				T result = (T) e;
				components.remove(result);
				return result;
			} catch (ClassCastException ignored) {
			}
		}
		return null;
	}

	public final EntityComponent removeComponent(Class<?> _class) {
		for (EntityComponent e : components) {
			EntityComponent temp = null;
			if (e.getClass() == _class || e.getClass().getSuperclass() == _class)
				temp = e;
			components.remove(temp);
			return temp;
		}
		return null;
	}

	/**
	 * Finds a component of the object and returns it
	 *
	 * @param <T> The class of the component to find
	 * @return Returns null if no component was found or The component if it was
	 *         found
	 */
	@Deprecated
	public final <T extends EntityComponent> T getComponent() {
		T result = null;
		for (EntityComponent e : components) {
			try {
				result = (T) e;
				return result;
			} catch (ClassCastException ignored) {
			}
		}
		return result;
	}

	public final EntityComponent getComponent(Class<?> _class) {
		for (EntityComponent e : components)
			if (e.getClass() == _class || e.getClass().getSuperclass() == _class)
				return e;
		return null;
	}

	/**
	 * @return Initializes and return a game object
	 */
	public static GameObject instantiate() {
		return new GameObject("Untitled");
	}

	/**
	 * Changes the name of the object
	 *
	 * @param name The new name
	 */
	public final void setName(String name) {
		this.name = name;
	}

	/**
	 * @return Returns the name of the object
	 */
	public final String getName() {
		return this.name;
	}

	/**
	 * @return Returns the id of the object
	 */
	public final int getId() {
		return this.id;
	}

	/**
	 * Finds if the object has a specific tag
	 *
	 * @param tag the tag to search
	 * @returns Returns true if the object is tagged as such
	 */
	public final boolean hasTag(String tag) {
		for (String s : tags)
			if (s.equals(tag))
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
	public final String toString() {
		return name + "\t" + tags.toString() + "\t" + components.toString();
	}

	final Set<EntityComponent> getComponents() {
		return new HashSet<>(components);
	}
}
