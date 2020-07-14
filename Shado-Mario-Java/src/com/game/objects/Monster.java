/**
 *
 */
package com.game.objects;

import com.engin.shapes.Texture;
import com.game.objects.info.DamageInfo;
import com.game.objects.interfaces.ICollidable;
import com.game.objects.interfaces.IDrawable;
import com.game.objects.interfaces.IMovable;

import java.awt.*;

public class Monster extends LivingObject implements IDrawable, IMovable {

	private int x;
	private int y;
	private int width;
	private int height;

	private Texture texture;

	public Monster(int x, int y) {
		super("monster");

		this.x = x;
		this.y = y;

		width = 50;
		height = 150;

		texture = new Texture("Images/monster.png", width, height);
	}

	/**
	 * Evaluates if an object is colliding another object
	 *
	 * @param other The other object to evaluate with
	 * @return Returns true if the 2 objects are in collision
	 */
	@Override
	public boolean collides(ICollidable other) {
		return (this.x < other.getX() + other.getWidth() &&
				this.x + this.width > other.getX() &&
				this.y < other.getY() + other.getHeight() &&
				this.y + this.height > other.getY());
	}

	/**
	 * Draws the object to the screen
	 *
	 * @param g The graphics context
	 */
	@Override
	public void draw(Graphics g) {
		texture.setPosition(x, y).draw(g);
	}

	/**
	 * Damages the current object by the attack damage of the source
	 *
	 * @param info The information about the damage
	 */
	@Override
	public void damage(DamageInfo info) {

		// Computer armour reduction
		float armor_reduction = 0.0f;
		if (armor >= 0)
			armor_reduction = 100 / (armor + 100);
		else
			armor_reduction = 2 - (100 / (100 - armor));

		// Computer MR reduction
		float mr_reduction = 0.0f;
		if (mr >= 0)
			mr_reduction = 100 / (mr + 100);
		else
			mr_reduction = 2 - (100 / (100 - mr));

		// Decide what damage type it is
		if (info.type == DamageInfo.PHYSICAL)
			hp -= (1 - armor_reduction) * info.amount;
		else if (info.type == DamageInfo.MAGIC)
			hp -= (1 - mr_reduction) * info.amount;
		else if (info.type == DamageInfo.PHYSICAL)
			hp -= info.amount;
		else
			throw new RuntimeException("Invalid damage type!");
	}

	/**
	 * Move int x, y by a certain amount
	 *
	 * @param x The x to move by
	 * @param y The y to move by
	 */
	@Override
	public void move(int x, int y) {
		this.x += x;
		this.y += y;
	}

	/**
	 * @return Returns the position of the object in X
	 */
	@Override
	public int getX() {
		return x;
	}

	/**
	 * @return Returns the position of the object in Y
	 */
	@Override
	public int getY() {
		return y;
	}

	/**
	 * @return Returns the width of the object
	 */
	@Override
	public int getWidth() {
		return width;
	}

	/**
	 * @return Returns the height of the object
	 */
	@Override
	public int getHeight() {
		return height;
	}
}
