package models;

import textures.ModelTexture;

/**
 * 
 * @author shadi
 *
 */
public final class TexturedModel {

	private final RawModel model;
	private final ModelTexture texture;

	public TexturedModel(RawModel model, ModelTexture texture) {
		this.model = model;
		this.texture = texture;
	}

	public final RawModel getModel() {
		return model;
	}

	public final ModelTexture getTexture() {
		return texture;
	}

}