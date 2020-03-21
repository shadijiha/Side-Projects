package driver; /**
 *
 */

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import shado.core.geometry.Circle;
import shado.core.geometry.Rectangle;
import shado.core.render.Camera;
import shado.core.render.Layer;
import shado.core.render.Renderer2D;
import shado.core.util.Vec3;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {

		primaryStage.setTitle("Shado Shapes Percentage");
		Group root = new Group();
		Canvas canvas = new Canvas(1920, 1080);
		GraphicsContext gc = canvas.getGraphicsContext2D();

		// clear the canvas and Draw shapes
		Camera camera = new Camera(-1.6f, 1.6f, -0.9f, 0.9f);
		camera.setPosition(new Vec3(0.5, 0.5, 0.0f));

		Renderer2D render = new Renderer2D(canvas, camera);
		Layer layer = new Layer("first");

		Rectangle rect = new Rectangle(0.25f, 0.25f, 0.5f, 0.5f);

		Circle cir = new Circle(0.10f, 0.10f, 0.05f);

		layer.add(rect);
		layer.add(cir);
		render.submit(layer);

		new AnimationTimer() {
			public void handle(long now) {
				render.clear();
				render.render();

			}
		}.start();

		root.getChildren().add(canvas);
		Scene scene = new Scene(root);

		// TEMPORARY
		scene.setOnKeyPressed(event -> {

			float offsetX = 0.0f;
			float offsetY = 0.0f;
			float angle = 0.0f;

			var key = event.getCode();
			if (key == KeyCode.D)
				offsetX += 0.01;
			if (key == KeyCode.A)
				offsetX -= 0.01;
			if (key == KeyCode.W)
				offsetY -= 0.01f;
			if (key == KeyCode.S)
				offsetY += 0.01f;
			if (key == KeyCode.LEFT)
				angle -= 0.01f;
			if (key == KeyCode.RIGHT)
				angle += 0.01f;
		});


		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
