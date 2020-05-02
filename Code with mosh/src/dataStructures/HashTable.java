/**
 *
 */

package dataStructures;

import datatypes.*;

public class HashTable<T> {

	private T[] data;
	private uint32_t length;

	public HashTable() {
		data = (T[]) new Object[10];
		length = new uint32_t(0);
	}

	public void insert(T e) {
		length.increment(1);
	}

	private int hash(Object o) {
		return Math.abs(o.hashCode()) % data.length;
	}

}
