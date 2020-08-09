/**
 *
 */
package com.engin.components;

import com.engin.GameObject;
import com.engin.math.Vector;

public final class RigidBody extends EntityComponent {

	public double mass;
	public Vector velocity;
	public Vector acceleration;
	public Vector force;

	public RigidBody(GameObject obj) {
		super(obj);
	}
}
