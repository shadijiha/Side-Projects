/**
 *
 */

package datatypes;

public final class uint8_t extends uint_t {
	public static final byte MAX_VALUE = Byte.MAX_VALUE;
	public static final byte MIN_VALUE = 0;

	private byte value = 0;

	public uint8_t(byte value) {
		this.value = value < 0 ? 0 : value;
	}

	public uint8_t() {
		this.value = 0;
	}

	public uint8_t(final uint8_t other) {
		this.value = other.value;
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

	/**
	 * Increments the calling object by the value
	 * @param val The value to add
	 * @return Returns the calling number
	 */
	public uint8_t increment(byte val) {
		this.value += val;
		this.value = this.value < 0 ? 0 : this.value;
		return this;
	}

	/**
	 * Increments the calling number by "1"
	 * @return Returns the calling object
	 */
	public uint8_t increment() {
		return increment((byte) 1);
	}

	@Override
	public String toString() {
		return value + "";
	}

	public byte value() {
		return this.value;
	}

	public byte toByte() {
		return value();
	}

	public uint8_t set(byte value) {
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
		return Byte.compare(value, o.byteValue());
	}
}
