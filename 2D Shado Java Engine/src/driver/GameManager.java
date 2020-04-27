/**
 *
 */

package driver;

import com.shado.core.AbstractGame;
import com.shado.core.GameContainer;
import com.shado.core.Renderer2D;
import com.shado.gfx.Image;
import com.shado.gfx.Light;
import com.shado.shapes.Rectangle;
import com.shado.shapes.Shape;

import java.util.ArrayList;

public class GameManager extends AbstractGame {

	private Image image;
	private Image background;
	private Light light;

	private ArrayList<Shape> shapes = new ArrayList<>();

	public GameManager() {
		super("", 1280, 720, 1.0f);
		image = new Image("Resources/wood.png");
		image.setLightBlock(Light.FULL_BLOCK);
		background = new Image("Resources/Untitled.png");
		light = new Light(250, 0xffffffff);

		shapes.add(new Rectangle(220, 200, 180, 90).setFill(0xffffff00));
	}

	public static void main(String[] args) {
		// write your code here
		GameContainer gc = new GameContainer(new GameManager());
		gc.start();
		gc.run();
	}

	@Override
	public void update(GameContainer gc, float dt) {
	}

	@Override
	public void render(GameContainer gc, Renderer2D r) {
		//r.setAmbientColor(0xff000000);

		r.drawLight(light, gc.getInput().getMouseX(), gc.getInput().getMouseY());

		r.drawImage(background, 0, 0);
		r.drawImage(image, 100, 100);
		r.fillEllipse(gc.getWidth() / 2, gc.getHeight() / 2, 100, 50, 0xffff0000, Light.FULL_BLOCK);

		for (final Shape s : shapes)
			s.draw(r);
	}
}
