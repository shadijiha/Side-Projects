/**
 * 
 */
package rendererEngin;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author shadi
 *
 */
public abstract class Scene {

	private static final List<Scene> allScenes = new ArrayList<>();

	private String name;
	private final int zIndex;
	private boolean hidden;

	private boolean hasInitialized;

	public Scene(String name, int z_index) {
		this.name = name;
		this.zIndex = z_index;
		this.hidden = false;

		allScenes.add(this);
		allScenes.sort(new Comparator<Scene>() {

			@Override
			public int compare(Scene o1, Scene o2) {
				// TODO Auto-generated method stub
				return Integer.compare(o1.zIndex, o2.zIndex);
			}
		});
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
	public abstract void init(Loader loader);

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
	public abstract void draw(Renderer renderer);

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

	public final boolean hasInitialized() {
		return hasInitialized;
	}

	public final void inited() {
		hasInitialized = true;
	}

	public static final List<Scene> allScenes() {
		return List.copyOf(allScenes);
	}
}
