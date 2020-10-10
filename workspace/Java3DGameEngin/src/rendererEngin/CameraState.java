/**
 * 
 */
package rendererEngin;

import entities.Camera;

/**
 * @author shadi This class stores the Position, rotation of the camera
 * 
 *         Immutible class used to prevent recalculation of the ViewMatrix when
 *         the camera has not changed stat
 */
public class CameraState {

	public final float x;
	public final float y;
	public final float z;

	public final float xRotation;
	public final float yRotation;
	public final float zRotation;

	public CameraState(final Camera cam) {
		var pos = cam.getPosition();
		x = pos.x;
		y = pos.y;
		z = pos.z;

		var rot = cam.getRotation();
		xRotation = rot.x;
		yRotation = rot.y;
		zRotation = rot.z;
	}

	/**
	 * Compares 2 camera states, They are equal if they have the same position and
	 * rotation
	 * 
	 * @param o The other camera state
	 * @return Returns true if they are equal false otherwise
	 */
	public boolean equals(final CameraState o) {
		if (this == o)
			return true;
		return x == o.x && y == o.y && z == o.z && xRotation == o.xRotation && yRotation == o.yRotation
				&& zRotation == o.zRotation;
	}

}
