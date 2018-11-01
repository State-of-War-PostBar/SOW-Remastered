package cn.stateofwar.sowr.graphic.render;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/** Moderator for loaded textures. */
public class TextureBus {

  /** Stored textures. */
  private static Map<String, Texture> textures = new HashMap<>();

  /**
   * Register a texture.
   *
   * @param name Name of the texture.
   * @param texture The texture to register.
   */
  public static void register(String name, Texture texture) {
    textures.put(name, texture);
  }

  /**
   * Get a texture.
   *
   * @param name Name of the texture.
   * @return The texture requested.
   */
  public static Texture get(String name) {
    if (textures.containsKey(name)) return textures.get(name);
    return textures.get("credit.dk");
  }

  /** Initialize default textures. */
  public static void init() {
    register("credit.dk", Texture2D.loadTextureA("sowr/graphic/texture/credit/credit_dk.png"));
    register("credit.tax", Texture2D.loadTextureA("sowr/graphic/texture/credit/credit_tax.png"));
  }

  /** Clean up the textures. */
  public static void abrogate() {
    for (Entry<String, Texture> entry : textures.entrySet()) entry.getValue().delete();
  }

}
