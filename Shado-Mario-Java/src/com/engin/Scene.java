package com.engin;

import java.awt.*;

public abstract class Scene {

	private final long id;
	private final String name;
	private int zIndex;

	public Scene(String name, int z_index) {
		this.name = name;
		this.id = (long) (Math.random() * Long.MAX_VALUE);
		this.zIndex = z_index;
	}

	public Scene(String name) {
		this(name, 0);
	}

	public abstract void init();

	public abstract void update(final long dt);

	public abstract void draw(final Graphics g);

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getzIndex() {
		return zIndex;
	}
}
