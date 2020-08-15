/**
 *
 */
package com.engin.components;

import com.engin.GameObject;

public abstract class Script extends EntityComponent {

	protected Script(GameObject obj) {
		super(obj);
	}

	public abstract void run(float dt);
}
