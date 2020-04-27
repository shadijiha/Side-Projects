package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import logger.Logger;

import java.net.URISyntaxException;

public class Main extends Application {

	public static final Logger LOGGER = new Logger(false);
	private final long[] frameTimes = new long[100];
	private int frameTimeIndex = 0;

	public static void main(String[] args) {
		launch(args);

		try {
			LOGGER.close();
		} catch (Exception e) {
			System.out.print(e.getMessage() + " ");
			e.printStackTrace();
		}
	}

	private static void initialize() throws URISyntaxException {
//		new Event("COMP-233 Lec.", Event.Day.Monday, 13.25, 14.5, Color.BLUE, "https://concordia-ca.zoom.us/j/4352778372");
//		new Event("COMP-233 Lec.", Event.Day.Wednesday, 13.25, 14.5, Color.BLUE, "https://concordia-ca.zoom.us/j/4352778372");
//
//		new Event("ENGR-233 Tut.", Event.Day.Friday, 14.25, 16.00, Color.RED, "https://mail.google.com");
//
//		new Event("COMP-249 Lab.", Event.Day.Friday, 12.167, 13.167, Color.GREEN, "https://zoom.us/j/7372780497");
//		new Event("COMP-249 Tut.", Event.Day.Monday, 20.5, 21.167, Color.GREEN, "https://us04web.zoom.us/j/721775166");
//		new Event("COMP-249 Tut.", Event.Day.Monday, 21.167, 21.0 + (40.0 / 60.0), Color.GREEN, "https://us04web.zoom.us/j/594199564");

		CSVParser loader = new CSVParser("events.csv");
		loader.loadEvents();
	}

	@Override
	public void start(Stage primaryStage) {

		// Turn on debug mode
		LOGGER.setDebugModeTo(false);

		primaryStage.setTitle("Simple 2D game :)");
		Group root = new Group();
		Canvas canvas = new Canvas(1280, 720);
		GraphicsContext gc = canvas.getGraphicsContext2D();

		// Register mouse position
		canvas.setOnMouseMoved(event -> {
			sample.Mouse.setX(event.getX());
			sample.Mouse.setY(event.getY());
		});

		canvas.setOnMouseClicked(event -> {
			sample.Mouse.setLastClick(event.getX(), event.getY());
		});

		try {
			initialize();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		// clear the canvas and Draw shapes
		new AnimationTimer() {
			public void handle(long now) {
				gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

				DrawComponent.render(canvas);

			}
		}.start();


		root.getChildren().add(canvas);
		Scene scene = new Scene(root);

		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
