/**
 *
 */
package com.engin;

import com.engin.components.EntityComponent;
import com.engin.components.Transform;

import java.util.HashSet;
import java.util.Set;

public class GameObject {

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
	public void addComponent(EntityComponent component) {
		components.add(component);
	}

	/**
	 * Removes a component from the Game object <<<<<<< HEAD
	 *
	 * @param <T> The component class to remove
	 * @return Returns the deleted component
	 */
	@Deprecated
	public <T extends EntityComponent> T removeComponent() {
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

	public EntityComponent removeComponent(Class<?> _class) {
		for (EntityComponent e : components) {
			EntityComponent temp = null;
			if (e.getClass() == _class)
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
	public <T extends EntityComponent> T getComponent() {
		for (EntityComponent e : components) {
			try {
				T result = (T) e;
				return result;
			} catch (ClassCastException ignored) {
			}
		}
		return null;
	}

	public EntityComponent getComponent(Class<?> _class) {
		for (EntityComponent e : components)
			if (e.getClass() == _class)
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
	 * Changes the name of the object <<<<<<< HEAD
	 *
	 * ======= >>>>>>> d87143804e46f8a3c741b4772cf11ffa313b795a
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
	 * Finds if the object has a specific tag <<<<<<< HEAD
	 *
	 * ======= >>>>>>> d87143804e46f8a3c741b4772cf11ffa313b795a
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
}
