package com.ecosystem;

import com.engin.*;
import com.engin.logger.*;

import java.io.*;
import java.util.*;

/**
 *
 */
public final class StatCollector {

	private final List<Object> Xaxes;
	private final List<Object> Yaxes;
	private final List<Long> time;

	private int elements;
	private final String name;

	public StatCollector(String name) {
		Xaxes = new ArrayList<>();
		Yaxes = new ArrayList<>();
		time = new ArrayList<>();

		this.name = name;
	}

	public StatCollector() {
		this(Util.randomString(10, false));
	}

	public void addToGraph(Object x, Object y) {
		Xaxes.add(x);
		Yaxes.add(y);
		time.add(new Date().getTime());

		elements++;
	}

	/**
	 * exports the data to a csv file and clears the buffer
	 */
	public void exportToCSV() {
		try {
			PrintWriter writer = new PrintWriter(new FileOutputStream(name + ".csv"));

			for (int i = 0; i < Xaxes.size(); i++) {
				writer.printf("%s, %s\n", Xaxes.get(i).toString(), Yaxes.get(i).toString());
			}

			writer.close();
		} catch (Exception e) {
			Debug.error(e);
		}
	}

	public int size() {
		return elements;
	}
}
