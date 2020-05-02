/**
 *
 */

package dataStructures;

import java.util.*;

public class Stack<T> {

	private LinkedList<T> list;

	public Stack() {
		list = new LinkedList<>();
	}

	public void push(T object) {
		list.add(object);
	}

	public T pop() {
		T obj = list.getLast();
		list.remove(obj);
		return obj;
	}

	public T peek() {
		return list.getLast();
	}

	public int size() {
		return list.size();
	}

	public boolean isEmpty() {
		return list.size() == 0;
	}
}
