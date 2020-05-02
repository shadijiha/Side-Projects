/**
 *
 */

package datatypes;

public abstract class uint_t extends Number implements Comparable<Number> {

	protected boolean isNegative(long val) {
		return val < 0;
	}

	public abstract Object increment();

	public abstract String toString();

	/**
	 * Determine if the calling number is equals to the passed one
	 *
	 * @param o The other number
	 * @return Returns true of the numbers are equal
	 */
	public boolean equals(Number o) {
		return compareTo(o) == 0;
	}

	/**
	 * Determine if the calling number is less than the passed one
	 *
	 * @param o The other number
	 * @return Returns true of the calling number is less than the passed one
	 */
	public boolean lessThan(Number o) {
		return compareTo(o) < 0;
	}

	/**
	 * Determine if the calling number is greater than the passed one
	 *
	 * @param o The other number
	 * @return Returns true of the calling number is greater than the passed one
	 */
	public boolean graterThan(Number o) {
		return compareTo(o) > 0;
	}
}
