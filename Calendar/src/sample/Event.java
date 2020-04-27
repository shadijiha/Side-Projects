/**
 *
 */

package sample;

import javafx.scene.paint.Color;

import java.awt.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class Event {

	public static final List<Event> allEvents = new ArrayList<>();

	enum Day {
		Monday(0), Tuesday(1), Wednesday(2), Thursday(3), Friday(4), Saturday(5), Sunday(6);

		int num;

		Day(int i) {
			num = i;
		}

		int value() {
			return num;
		}

		static Day parseDay(String d) {
			if (d.equals("Monday"))
				return Monday;
			else if (d.equals("Tuesday"))
				return Tuesday;
			else if (d.equals("Wednesday"))
				return Wednesday;
			else if (d.equals("Thursday"))
				return Thursday;
			else if (d.equals("Friday"))
				return Friday;
			else if (d.equals("Saturday"))
				return Saturday;
			else if (d.equals("Sunday"))
				return Sunday;
			else
				return null;
		}
	}

	public float from;
	public float to;
	public Color color;
	public Day day;
	public String event;
	public URI link;

	public Event(String event, Day day, double from, double to, Color color, String link) throws URISyntaxException {
		this.event = event;
		this.day = day;
		this.from = (float) from;
		this.to = (float) to;
		this.link = new URI(link);
		this.color = color;
		allEvents.add(this);
	}

	public boolean openLink() {
		Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
		if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
			try {
				desktop.browse(this.link);
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}
}
