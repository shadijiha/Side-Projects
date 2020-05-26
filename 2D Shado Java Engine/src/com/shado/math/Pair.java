/**
 *
 */

package com.shado.math;

public class Pair<T, E> implements Cloneable {

	public T first;
	public E second;

	public Pair(T first, E second) {
		this.first = first;
		this.second = second;
	}

	private Pair(final Pair<T, E> other) {
		this.first = other.first;
		this.second = other.second;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || o.getClass() != this.getClass())
			return false;
		else {
			var p = (Pair<T, E>) o;
			return p.first.equals(this.first) && p.second.equals(this.second);
		}
	}

	@Override
	public Pair<T, E> clone() {
		try {
			var p = (Pair<T, E>) super.clone();
			p.first = this.first;
			p.second = this.second;

			return p;
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}
}
