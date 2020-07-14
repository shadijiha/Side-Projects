package driver;

import com.shado.core.*;
import com.shado.gfx.*;
import com.shado.math.*;

public class Main extends AbstractGame {

    private Light light;

    public Main()   {
        super("Game", 1280, 720, 1.0f);

        light = new Light(200, 0xffffffff);
    }

    public static void main(String[] args) {
	// write your code here
        GameContainer game = new GameContainer(new Main());
        game.start();
        game.run();
    }

    @Override
    public void update(GameContainer gc, float dt) {
    }

    @Override
    public void render(GameContainer gc, Renderer2D r) {

        r.drawImage();
        r.fillRect(100, 100, 50, 75, 0xff0000ff, Light.FULL_BLOCK);

        r.drawLight(light, gc.getInput().getMouseX(), gc.getInput().getMouseY());
    }
}
