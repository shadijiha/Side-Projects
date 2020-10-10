/**
 * 
 */
package rendererEngin;

import entities.Camera;

/**
 * @author shadi
 *
 */
public abstract class Scene {

	private final long id;
	private String name;
	protected int zIndex;
	private boolean hidden;

	public Scene(String name, int z_index) {
		this.name = name;
		this.zIndex = z_index;
		this.hidden = false;
		this.id = (long) (Math.random() * Long.MAX_VALUE);
	}

	public Scene(String name) {
		this(name, 0);
	}

	public Scene() {
		this("");
		this.name = getClass().getName();
	}

	/**
	 * Executes code once when the scene is initialized
	 * 
	 * @param display
	 */
	public abstract void init(Loader loader, Camera defaultCamera);

	/**
	 * Updates the scene
	 * 
	 * @param dt The time between 2 frames
	 */
	public abstract void update(float dt);

	/**
	 * Draws elements to the screen
	 * 
	 * @param renderer The openGL context renderer
	 */
	public abstract void draw(Renderer r);

	public final int getZindex() {
		return zIndex;
	}

	public final String getName() {
		return this.name;
	}

	public final boolean isHidden() {
		return hidden;
	}

	public final void hide() {
		hidden = true;
	}

	public final void show() {
		hidden = false;
	}

	public final long getId() {
		// TODO Auto-generated method stub
		return this.id;
	}
}
