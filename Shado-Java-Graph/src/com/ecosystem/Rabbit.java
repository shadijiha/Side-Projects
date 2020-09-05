/**
 *
 */
package com.ecosystem;

import com.engin.*;
import com.engin.math.*;
import com.engin.shapes.Rectangle;

import java.awt.*;
import java.util.List;
import java.util.*;

public final class Rabbit {

	private static final List<Rabbit> allRabbits = Collections.synchronizedList(new ArrayList<Rabbit>());

	public static final StatCollector maleCollector = new StatCollector("male");
	public static final StatCollector femaleCollector = new StatCollector("female");
	private static int totalMales = 0;
	private static int totalFemale = 0;

	public final static byte MALE = 0;
	public final static byte FEMALE = 1;

	// General control enviroment variable
	public static final float THIRST_CHANGE = 0.05f;
	public static final float HUNGER_CHANGE = 0.03f;
	public static final float HORNEYNESS_CHANGE = 0.1f;
	public static final float CHANCE_TO_GET_PREGNANT = 0.5F;
	public static final float MIN_HORNEYNESS_FOR_REPRODUCTION = 0.5f;
	public static final long PREGNANCY_DURATION = 200;    // in ms

	private final Renderer renderer;

	private float thirst = 1.0f;
	private float hunger = 1.0f;
	private float horneiness = 0.0f;
	private final byte gender;

	// For female
	private boolean isPregnant;
	private long birthTime;    // Represents the time in millis when the female should give birth

	private final int visionRange = 100;    // In pixels
	private final float speed = 25f;

	// For moving stuff
	private double distance;
	private Vector2f direction;
	private Vector2f position;
	private Vector2f start;
	private Vector2f end;
	private boolean moving;

	public Rabbit(Renderer r) {
		gender = Math.random() > 0.5 ? MALE : FEMALE;
		renderer = r;

		start = new Vector2f(
				(float) (Math.random() * (renderer.getWidth() - 50)),
				(float) (Math.random() * (renderer.getHeight() - 50))
		);

		position = new Vector2f(start);

		direction = new Vector2f();

		end = new Vector2f(
				(float) (Math.random() * (renderer.getWidth() - 50)),
				(float) (Math.random() * (renderer.getHeight() - 50)));

		moving = false;

		headTo(end);

		allRabbits.add(this);
	}

	public void update(float dt) {

		// Update stats
		thirst -= THIRST_CHANGE * dt;
		hunger -= HUNGER_CHANGE * dt;
		horneiness += HORNEYNESS_CHANGE * dt;

		if (thirst > 1.0f) thirst = 1.0f;
		if (hunger > 1.0f) hunger = 1.0f;
		if (horneiness > 1.0f) horneiness = 1.0f;

		if (thirst < 0.0f) thirst = 0.0f;
		if (hunger < 0.0f) hunger = 0.0f;
		if (horneiness < 0.0f) horneiness = 0.0f;

		// See if there's an opposite gender nearby nearby
		for (Rabbit other : allRabbits) {
			if (this.horneiness >= MIN_HORNEYNESS_FOR_REPRODUCTION && this.gender != other.gender
					&& !this.isPregnant && !other.isPregnant) {

				// See if its in the vision range
				var dist = position.distance(other.position);

				if (dist <= visionRange) {
					headTo(other.position);
				}
				if (dist <= 5.0f) {
					// They met
					Rabbit.reproduce(this, other);
				}
			}
		}

		// See if its due for birth
		if (isPregnant && System.currentTimeMillis() >= birthTime) {
			giveBirth();
		}

		// Move the rabbit
		if (moving) {
			var delta = new Vector2f(direction).mult(speed).mult(dt);
			position.x += delta.x;
			position.y += delta.y;

			if (Vector2f.distance(start, position) >= distance) {
				position = end;
				moving = false;

				// Go to another direction
				headTo();
			}
		}
	}

	public void giveBirth() {
		if (this.gender != FEMALE) {
			return;
		}

		// Child
		var child = new Rabbit(renderer);

		// Collect stats
		if (child.gender == MALE) {
			totalMales++;
			maleCollector.addToGraph(System.currentTimeMillis(), totalMales);
		} else {
			totalFemale++;
			femaleCollector.addToGraph(System.currentTimeMillis(), totalFemale);
		}

		isPregnant = false;
		birthTime = 0L;
	}

	public void draw(Graphics g) {

		var c = g.getColor();

		g.setColor(Color.ORANGE);
		g.fillOval((int) position.x, (int) position.y, 10, 10);

		g.setColor(c);

		// Draw vision range
		var t = g.getColor();
		g.setColor(new Color(150, 150, 150, 100));
		g.fillOval((int) (position.x - visionRange / 2.0f), (int) (position.y - visionRange / 2.0f), visionRange, visionRange);
		g.setColor(t);

		drawStats(g);
	}

	private void drawStats(Graphics g2) {

		Graphics2D g = (Graphics2D) g2;

		final int BAR_HEIGHT = 15;
		final int BAR_WIDTH = 50;

		// Draw horniness bar
		var horneyBarBG = new Rectangle((int) position.x - 10, (int) position.y - 15, BAR_WIDTH, BAR_HEIGHT);
		horneyBarBG.setFill(Color.WHITE);
		horneyBarBG.draw(g);

		var horneyBar = new Rectangle((int) horneyBarBG.getPosition().x, (int) horneyBarBG.getPosition().y, (int) (BAR_WIDTH * horneiness), BAR_HEIGHT);
		if (gender == MALE) {
			horneyBar.setFill(Color.BLUE);
		} else {
			horneyBar.setFill(Color.PINK);
		}
		horneyBar.draw(g);


		// Hunger
		var hungerBarBG = new Rectangle((int) horneyBarBG.getPosition().x, (int) horneyBarBG.getPosition().y - BAR_HEIGHT, BAR_WIDTH, BAR_HEIGHT);
		hungerBarBG.setFill(Color.WHITE);
		hungerBarBG.draw(g);

		var hungerBar = new Rectangle((int) hungerBarBG.getPosition().x, (int) hungerBarBG.getPosition().y, (int) (BAR_HEIGHT * hunger), BAR_HEIGHT);
		hungerBar.setFill(Color.ORANGE);
		hungerBar.draw(g);


		// Thirst
		var thirstBarBG = new Rectangle((int) hungerBarBG.getPosition().x, (int) hungerBarBG.getPosition().y - BAR_HEIGHT, BAR_WIDTH, BAR_HEIGHT);
		thirstBarBG.setFill(Color.WHITE);
		thirstBarBG.draw(g);

		var thirstBar = new Rectangle((int) thirstBarBG.getPosition().x, (int) thirstBarBG.getPosition().y, (int) (BAR_WIDTH * thirst), BAR_HEIGHT);
		thirstBar.setFill(Color.RED);
		thirstBar.draw(g);
	}

	public void headTo(float x, float y) {

		start = new Vector2f(position);
		end = new Vector2f(x, y);

		distance = Vector2f.distance(start, end);
		var temp = new Vector2f(end).sub(start);
		direction = Vector2f.normalize(temp);
		position = new Vector2f(start);

		moving = true;
	}

	public void headTo(ICoordinates2F c) {
		headTo(c.getX(), c.getY());
	}

	public void headTo() {
		headTo((float) Math.random() * renderer.getWidth(), (float) Math.random() * renderer.getHeight());
	}

	public static void reproduce(Rabbit r1, Rabbit r2) {

		Rabbit male = r1.gender == MALE ? r1 : r2;
		Rabbit female = r1.gender == FEMALE ? r1 : r2;

		male.horneiness = 0f;
		female.horneiness = 0f;

		if (Math.random() <= CHANCE_TO_GET_PREGNANT) {
			female.isPregnant = true;
			female.birthTime = System.currentTimeMillis() + PREGNANCY_DURATION;
		}

		male.headTo();
		female.headTo();
	}

	public ImmutableVec2f getPosition() {
		return position.toImmutableVector();
	}

	public ImmutableVec2f getDirection() {
		return direction.toImmutableVector();
	}

	public String toString() {
		return String.format("Pos: %s, Dir: %s, Pregnant? %s", position, direction, isPregnant);
	}

	public static int getRabbitCount() {
		return allRabbits.size();
	}

	static List<Rabbit> getAllRabbits() {
		return allRabbits;
	}
}
