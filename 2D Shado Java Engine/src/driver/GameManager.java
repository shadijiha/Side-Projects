/**
 *
 */

package driver;

import com.shado.core.*;
import com.shado.gfx.Color;
import com.shado.gfx.Image;
import com.shado.gfx.*;

import java.awt.*;

public class GameManager extends AbstractGame {

	private Image image;
	private Image background;
	private Light light;
	private Light light2;

	public GameManager() {
		super("", 1280, 720, 1.0f);
		image = new Image("Resources/wood.png");
		image.setLightBlock(Light.FULL_BLOCK);

		background = new Image("Resources/Untitled.png");


		light = new Light(250, 0xffffffff);
		light2 = new Light(200, Color.CYAN);
	}

	public static void main(String[] args) {
		// write your code here
		GameContainer gc = new GameContainer(new GameManager());
		gc.start();
		gc.run();
	}

	@Override
	public void init(GameContainer gc) {
		//gc.getRenderer().setAmbientColor(0xffffffff);

	}

	@Override
	public void update(GameContainer gc, float dt) {

		gc.getWindow().getFrame().setTitle("FPS: " + gc.getFramerate());
	}

	@Override
	public void render(GameContainer gc, Renderer2D r) {

		r.drawImage(background, 0, 0);
		r.drawLight(light, gc.getInput().getMouseX(), gc.getInput().getMouseY());

		r.drawText("FPS: " + gc.getFramerate(), 40, 40, new Font("Arial", Font.PLAIN, 32), Color.WHITE);

		r.drawImage(image, 100, 100);
		r.fillEllipse(gc.getWidth() / 2, gc.getHeight() / 2, 100, 50, 0xffff0000, Light.FULL_BLOCK);

		r.drawLight(light2, 200, 200);

	}
}
