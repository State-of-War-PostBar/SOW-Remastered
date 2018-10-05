package cn.stateofwar.sowr.gui;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import cn.stateofwar.sowr.gui.render.Texture;

/**
 * Manager for all the textures.
 */
public class TextureManager {

	/** The textures storage. */
	public static Map<String, Texture> textures = new HashMap<>();

	/**
	 * Register a texture.
	 * 
	 * @param name    Internal name for the texture.
	 * 
	 * @param texture Texture to register.
	 */
	public static void regTexture(String name, Texture texture) {
		textures.put(name, texture);
	}

	/**
	 * Register an unloaded texture.
	 * 
	 * @param name Internal name for the texture.
	 * 
	 * @param path Path of the texture.
	 */
	public static void registerTexture(String name, String path) {
		textures.put(name, Texture.loadTexture(path));
	}

	/**
	 * Abrogate all the textures.
	 */
	public static void abrogateTextures() {
		for (Entry<String, Texture> e : textures.entrySet())
			e.getValue().delete();
	}

}
