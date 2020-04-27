/**
 *
 */

package sample;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import shapes.Shado;

import java.util.Calendar;

import static sample.Event.allEvents;

public abstract class DrawComponent {

	public static final int HOURS_TO_SHOW = 14;
	public static final int DAYS_TO_SHOW = 5;

	public static void render(Canvas c) {

		//==================================
		double width = c.getWidth();
		double height = c.getHeight();
		int offsetY = 20; // px;

		double dayWidth = width / DAYS_TO_SHOW;
		double eventHeight = height / HOURS_TO_SHOW;    // Show 14 hours

		// Draw days with events
		for (int i = 0; i < width; i += dayWidth) {
			new Shado.Rectangle(i, offsetY, dayWidth, c.getHeight() + offsetY).setFill(Color.WHITE).draw(c);
		}


		for (final Event e : allEvents) {
			c.getGraphicsContext2D().setGlobalAlpha(0.5);

			var rect = new Shado.Rectangle(e.day.value() * dayWidth,
					((e.from - 9) * eventHeight) + offsetY,
					dayWidth,
					(e.to - e.from) * eventHeight)
					.setFill(e.color);
			rect.onClick(shape -> {
				e.openLink();
			});

			rect.draw(c);

			c.getGraphicsContext2D().setGlobalAlpha(1);

			new Shado.Text(e.event + "\n" + e.from + "-" + e.to,
					rect.getPosition().x + rect.getDimensions().width / 3,
					rect.getPosition().y + rect.getDimensions().height / 2)
					.setFont(new Font("Arial", 16))
					.draw(c);
		}


		// Draw lines each 2 hours from 9 AM to 9 PM
		int counter = 0;

		for (int y = 0; y < height; y += eventHeight) {
			new Shado.Line(0, y + offsetY, width, y + offsetY).draw(c);
			new Shado.Text(((counter + 9) % 12) + ":00", 5, y + offsetY).draw(c);
			counter += 1;
		}

		// Draw current time line
		int hours = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
		float minutes = Calendar.getInstance().get(Calendar.MINUTE);
		float total_time = hours + (minutes / 60);

		float current_time_y = (float) ((total_time - 9) * eventHeight + offsetY);
		new Shado.Line(0, current_time_y, width, current_time_y).setFill(Color.RED).setStroke(Color.RED).setLineWidth(3).draw(c);

	}
}
