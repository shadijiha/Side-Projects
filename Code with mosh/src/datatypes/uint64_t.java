/**
 *
 */

package datatypes;

public final class uint64_t extends uint_t {
	public static final long MAX_VALUE = Long.MAX_VALUE;
	public static final long MIN_VALUE = 0;

	private long value = 0;

	public uint64_t(long value) {
		this.value = Math.max(value, 0);
	}

	public uint64_t() {
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
		return (int) value;
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

	public uint64_t(final uint64_t other) {
		this.value = other.value;
	}

	public uint64_t increment(long val) {
		this.value = Math.max(this.value + val, 0);
		return this;
	}

	public uint64_t increment() {
		return increment(1);
	}

	@Override
	public String toString() {
		return value + "";
	}

	public long value() {
		return this.value;
	}

	public long toLong() {
		return value();
	}

	public uint64_t set(long value) {
		this.value = Math.max(value, 0);
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
		return Long.compare(value, o.longValue());
	}
}
