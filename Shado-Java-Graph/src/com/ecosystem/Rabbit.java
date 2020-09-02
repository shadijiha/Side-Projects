/**
 *
 */
package com.ecosystem;

import com.engin.Renderer;
import com.engin.math.ICoordinates2F;
import com.engin.math.ImmutableVec2f;
import com.engin.math.Vector2f;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public final class Rabbit {

	private static final List<Rabbit> allRabbits = new ArrayList<>();

	public final static byte MALE = 0;
	public final static byte FEMALE = 1;

	// General control enviroment variable
	public static final float THIRST_CHANGE = 0.05f;
	public static final float HUNGER_CHANGE = 0.03f;
	public static final float HORNEYNESS_CHANGE = 0.1f;
	public static final float CHANCE_TO_GET_PREGNANT = 0.5F;
	public static final float MIN_HORNEYNESS_FOR_REPRODUCTION = 0.1f;
	public static final long PREGNANCY_DURATION = 5000;    // in ms

	private final Renderer renderer;

	private float thirst = 1.0f;
	private float hunger = 1.0f;
	private float horneiness = 0.0f;
	private final byte gender;

	// For female
	private boolean isPregnant;
	private long birthTime;    // Represents the time in millis when the female should give birth

	private final int visionRange = 50;    // In pixels
	private final float speed = 0.5f;

	private final Vector2f position;
	private final Vector2f velocity;
	private final Vector2f heading;

	public Rabbit(Renderer r) {
		gender = Math.random() > 0.5 ? MALE : FEMALE;
		renderer = r;

		position = new Vector2f(
				(float) (Math.random() * (renderer.getWidth() - 50)),
				(float) (Math.random() * (renderer.getHeight() - 50))
		);

		velocity = new Vector2f();

		heading = new Vector2f(
				(float) (Math.random() * (renderer.getWidth() - 50)),
				(float) (Math.random() * (renderer.getHeight() - 50)));

		headTo(heading);


		allRabbits.add(this);
	}

	public void update() {

		// Update stats
		thirst -= THIRST_CHANGE;
		hunger -= HUNGER_CHANGE;
		horneiness += HORNEYNESS_CHANGE;

		// Update position
		position.x += velocity.x;
		position.y += velocity.y;

		// See if there's an opposit gender nearby nearby
		for (Rabbit other : allRabbits) {
			if (this.horneiness >= MIN_HORNEYNESS_FOR_REPRODUCTION && this.gender != other.gender
					&& !this.isPregnant && !other.isPregnant) {

				// See if its in the vision range
				var distance = position.distance(other.position);
				if (distance <= visionRange) {
					headTo(other.position);
				} else if (distance <= 5) {
					// They met
					Rabbit.reproduce(this, other);
				}
			}
		}

		// See if its due for birth
		if (isPregnant && System.currentTimeMillis() >= birthTime) {
			giveBirth();
		}

	}

	public void giveBirth() {
		if (this.gender != FEMALE) {
			return;
		}

		// Child
		new Rabbit(renderer);


		isPregnant = false;
		birthTime = 0L;
	}

	public void draw(Graphics g) {

		var c = g.getColor();

		g.setColor(Color.ORANGE);
		g.fillOval((int) position.x, (int) position.y, 10, 10);

		g.setColor(c);
	}

	public void headTo(float x, float y) {

		if (x <= 0)
			x = 1;
		if (y <= 0)
			y = 1;

		heading.x = x;
		heading.y = y;

		double angle = Math.atan2(y, x);

		velocity.x = -(float) (speed * Math.cos(angle));
		velocity.y = -(float) (speed * Math.sin(angle));
	}

	public void headTo(ICoordinates2F c) {
		headTo(c.getX(), c.getY());
	}

	public static final void reproduce(Rabbit r1, Rabbit r2) {

		Rabbit male = r1.gender == MALE ? r1 : r2;
		Rabbit female = r1.gender == FEMALE ? r1 : r2;

		male.horneiness = 0f;
		female.horneiness = 0f;

		if (Math.random() <= CHANCE_TO_GET_PREGNANT) {
			female.isPregnant = true;
			female.birthTime = System.currentTimeMillis() + PREGNANCY_DURATION;
		}

	}

	public ImmutableVec2f getPosition() {
		return position.toImmutableVector();
	}

	public ImmutableVec2f getVelocity() {
		return velocity.toImmutableVector();
	}
}
