package cn.stateofwar.sowr.graphic.ogl;

import cn.stateofwar.sowr.graphic.ogl.Shader.ShaderType;

/**
 * Moderator of built-in shaders.
 */
public class ShaderBus {

	/** Shader for rendering objects with raw color. */
	public static Shader vertex_raw_color_2d, fragment_raw_color_2d;

	/** Shader for rendering objects with raw textures. */
	public static Shader vertex_raw_texture_2d, fragment_raw_texture_2d;

	/**
	 * Initialize the shaders.
	 */
	public static void init() {
		vertex_raw_color_2d = Shader.createShader("sowr/graphic/shader/raw_color_2d.vsh", ShaderType.VERTEX, true);
		fragment_raw_color_2d = Shader.createShader("sowr/graphic/shader/raw_color_2d.fsh", ShaderType.FRAGMENT, true);

		vertex_raw_texture_2d = Shader.createShader("sowr/graphic/shader/raw_texture_2d.vsh", ShaderType.VERTEX, true);
		fragment_raw_texture_2d = Shader.createShader("sowr/graphic/shader/raw_texture_2d.fsh", ShaderType.FRAGMENT,
				true);
	}

	/**
	 * Clean up the shaders.
	 */
	public static void abrogate() {
		vertex_raw_color_2d.abrogate();
		fragment_raw_color_2d.abrogate();

		vertex_raw_texture_2d.abrogate();
		fragment_raw_texture_2d.abrogate();
	}

}
