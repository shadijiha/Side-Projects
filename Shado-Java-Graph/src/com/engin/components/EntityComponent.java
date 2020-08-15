/**
 *
 */
package com.engin.components;

import com.engin.GameObject;

public abstract class EntityComponent {

	protected boolean enabled;
	protected final GameObject gameObject;

	protected EntityComponent(GameObject obj) {
		gameObject = obj;
	}

}
