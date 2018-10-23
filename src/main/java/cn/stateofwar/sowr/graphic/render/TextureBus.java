package cn.stateofwar.sowr.graphic.render;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class TextureBus {

	private static Map<String, Texture> textures = new HashMap<>();

	public static void register(String name, Texture texture) {
		textures.put(name, texture);
	}

	public static Texture get(String name) {
		return textures.get(name);
	}

	public static void init() {
		register("credit.dk", Texture2D.loadTextureA("sowr/graphic/texture/credit/credit_dk.png"));
		register("credit.tax", Texture2D.loadTextureA("sowr/graphic/texture/credit/credit_tax.png"));
	}

	public static void abrogate() {
		for (Entry<String, Texture> entry : textures.entrySet())
			entry.getValue().delete();
	}

}
