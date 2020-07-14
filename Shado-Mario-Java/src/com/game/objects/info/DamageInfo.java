/**
 * Objects of this class hold the information about the damage a Living object takes
 */
package com.game.objects.info;

import com.game.objects.LivingObject;

public class DamageInfo {

	public static final byte TRUE = 0;
	public static final byte PHYSICAL = 1;
	public static final byte MAGIC = 2;

	public final float amount;
	public final LivingObject source;
	public final byte type;
	public final String description;

	public DamageInfo(LivingObject source, float amount, byte type, String description) {
		this.source = source;
		this.amount = amount;
		this.type = type;
		this.description = description;
	}

	public DamageInfo(LivingObject source) {
		this(source, source.getStat(LivingObject.AD), PHYSICAL, "Basic Attack");
	}
}
