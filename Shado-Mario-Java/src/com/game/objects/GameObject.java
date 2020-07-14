/**
 *
 */
package com.game.objects;

public abstract class GameObject {

	protected final String name;
	protected final long id;

	protected GameObject(String name) {
		this.name = name;
		this.id = (long) (Math.random() * Long.MAX_VALUE);
	}

	public String getName() {
		return name;
	}

	public long getId() {
		return id;
	}
}
