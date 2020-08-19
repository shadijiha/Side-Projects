/**
 *
 */
package com.engin.components;

import com.engin.GameObject;
import com.engin.math.Vector2f;

public final class RigidBody extends EntityComponent {

	public double mass;
	public Vector2f velocity;
	public Vector2f acceleration;
	public Vector2f force;

	public RigidBody(GameObject obj) {
		super(obj);
	}
}
