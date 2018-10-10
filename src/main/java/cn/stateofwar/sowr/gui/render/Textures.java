package cn.stateofwar.sowr.gui.render;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Manager for all the textures.
 */
public class Textures {

	/** Textures storage. */
	private static Map<String, Texture> textures = new HashMap<>();

	/**
	 * Register a texture.
	 * 
	 * @param name    Internal name for the texture.
	 * 
	 * @param texture Texture to register.
	 * 
	 * @return Instance of the texture.
	 */
	public static Texture register(String name, Texture texture) {
		textures.put(name, texture);
		return textures.get(name);
	}

	/**
	 * Register an unloaded texture.
	 * 
	 * @param name Internal name for the texture.
	 * 
	 * @param path Path of the texture.
	 * 
	 * @return Instance of the texture.
	 */
	public static Texture register(String name, String path) {
		textures.put(name, Texture.loadTexture(path));
		return textures.get(name);
	}

	/**
	 * Get a texture.
	 * 
	 * @return Texture by the name.
	 */
	public static Texture get(String name) {
		return textures.get(name);
	}

	/**
	 * Abrogate all the textures.
	 */
	public static void abrogate() {
		for (Entry<String, Texture> e : textures.entrySet())
			e.getValue().delete();
	}

	/**
	 * Initialize default textures.
	 */
	public static void init() {

	}

}
