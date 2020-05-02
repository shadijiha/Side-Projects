/**
 *
 */

package datatypes;

public final class uint16_t extends uint_t {
	public static final short MAX_VALUE = Short.MAX_VALUE;
	public static final short MIN_VALUE = 0;

	private short value = 0;

	public uint16_t(short value) {
		this.value = value < 0 ? 0 : value;
	}

	public uint16_t() {
		this.value = 0;
	}

	/**
	 * Returns the value of the specified number as an {@code int}.
	 *
	 * @return the numeric value represented by this object after conversion
	 * to type {@code int}.
	 */
	@Override
	public int intValue() {
		return value;
	}

	/**
	 * Returns the value of the specified number as a {@code long}.
	 *
	 * @return the numeric value represented by this object after conversion
	 * to type {@code long}.
	 */
	@Override
	public long longValue() {
		return value;
	}

	/**
	 * Returns the value of the specified number as a {@code float}.
	 *
	 * @return the numeric value represented by this object after conversion
	 * to type {@code float}.
	 */
	@Override
	public float floatValue() {
		return value;
	}

	/**
	 * Returns the value of the specified number as a {@code double}.
	 *
	 * @return the numeric value represented by this object after conversion
	 * to type {@code double}.
	 */
	@Override
	public double doubleValue() {
		return value;
	}

	public uint16_t(final uint16_t other) {
		this.value = other.value;
	}

	public uint16_t increment(short val) {
		this.value += val;
		this.value = this.value < 0 ? 0 : this.value;
		return this;
	}

	public uint16_t increment() {
		return increment((short) 1);
	}

	@Override
	public String toString() {
		return value + "";
	}

	public short value() {
		return this.value;
	}

	public short toShort() {
		return value();
	}

	public uint16_t set(short value) {
		this.value = value < 0 ? 0 : value;
		return this;
	}

	/**
	 * Compares this object with the specified object for order.  Returns a
	 * negative integer, zero, or a positive integer as this object is less
	 * than, equal to, or greater than the specified object.
	 * @param o the object to be compared.
	 * @return a negative integer, zero, or a positive integer as this object
	 * is less than, equal to, or greater than the specified object.
	 * @throws NullPointerException if the specified object is null
	 * @throws ClassCastException   if the specified object's type prevents it
	 *                              from being compared to this object.
	 */
	@Override
	public int compareTo(Number o) {
		return Short.compare(value, o.shortValue());
	}
}
