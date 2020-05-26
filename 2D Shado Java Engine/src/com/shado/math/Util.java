package com.shado.math;

import java.util.*;

public class Util {

	/***
	 * Gives a random number from a specified range
	 *
	 * @param min The minimum number of the range
	 * @param max The maximum number of the range
	 *
	 * @return Returns a random number >= min and <= max
	 * */
	public static double random(double min, double max) {
		return Math.random() * (max - min) + min;
	}

	/***
	 * Gives a random number from a specified range
	 *
	 * @param min The minimum number of the range
	 * @param max The maximum number of the range
	 *
	 * @return Returns a random number >= min and <= max
	 * */
	public static int random(int min, int max) {
		return (int) random((double) min, (double) max);
	}

	/***
	 * Gives the name of the passed object
	 *
	 * @param obj The object you want to get its name
	 *
	 * @return Returns the name of the passed object
	 * */
	public static String getObjectName(Object obj) {
		String[] temp = obj.getClass().getName().split("\\.");
		return temp[temp.length - 1];
	}

	/***
	 * Prints stuff to the console
	 *
	 * @param args The things you want to print to the console
	 * */
	public static <T> void print(@SuppressWarnings("unchecked") T... args) {
		for (T arg : args)
			System.out.print(arg + " ");
		System.out.println();
	}

	/***
	 * Gives the current hour
	 *
	 * @return Returns the current hour
	 * */
	public static int getHour() {
		Date date = new Date();   // given date
		Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
		calendar.setTime(date);   // assigns calendar to given date
		return calendar.get(Calendar.HOUR_OF_DAY); // gets hour in 24h format
	}
}
