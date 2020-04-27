/**
 *
 */

package com.shado.core;

import com.shado.gfx.Image;

public class ImageRequest {

	public Image image;
	public int z_depth;
	public int x;
	public int y;

	public ImageRequest(Image image, int z_depth, int x, int y) {
		this.image = image;
		this.z_depth = z_depth;
		this.x = x;
		this.y = y;
	}
}
