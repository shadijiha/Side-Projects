/**
 * A living game object is an object that has an HP bar, can take damage and deal damage
 */

package com.game.objects;

import com.game.objects.info.DamageInfo;
import com.game.objects.interfaces.ICollidable;

public abstract class LivingObject extends GameObject implements ICollidable {

	public static final byte AD = 0;
	public static final byte AP = 1;
	public static final byte ARMOR = 2;
	public static final byte MR = 3;
	public static final byte HP = 4;
	public static final byte MAX_HP = 5;
	public static final byte HP_REGEN = 6;
	public static final byte ENERGY = 7;
	public static final byte ENERGY_REGEN = 8;
	public static final byte MAX_ENERGY = 9;

	protected float ad;
	protected float ap;

	protected float armor;
	protected float mr;

	protected float hp;
	protected float maxHp;
	protected float hpRegen;

	protected float energy;
	protected float maxEnergy;
	protected float energyRegen;

	protected LivingObject(String name) {
		super(name);
	}

	/**
	 * Damages the current object by the attack damage of the source
	 *
	 * @param info The information about the damage
	 */
	public abstract void damage(DamageInfo info);

	/**
	 * Gets a specific stat of the living object
	 *
	 * @param stat The stat to get based on static variables
	 * @return Returns the stat requested
	 */
	public float getStat(byte stat) {
		switch (stat) {
			case AD:
				return ad;
			case AP:
				return ap;
			case ARMOR:
				return armor;
			case MR:
				return mr;
			case HP:
				return hp;
			case MAX_HP:
				return maxHp;
			case HP_REGEN:
				return hpRegen;
			case ENERGY:
				return energy;
			case ENERGY_REGEN:
				return energyRegen;
			case MAX_ENERGY:
				return maxEnergy;
			default:
				return 0.0f;
		}
	}
}
