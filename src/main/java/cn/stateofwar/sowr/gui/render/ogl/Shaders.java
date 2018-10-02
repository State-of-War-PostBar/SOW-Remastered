package cn.stateofwar.sowr.gui.render.ogl;

import cn.stateofwar.sowr.gui.render.ogl.Shader.ShaderType;

/**
 * Pre-built shaders for shader programs.
 */
public class Shaders {

	public static Shader VERTEX_RAW;

	public static Shader FRAGMENT_RAW;

	public static Shader VERTEX_SAMPLERLESS_TEXTURE;

	public static Shader FRAGMENT_SAMPLERLESS_TEXTURE;

	/**
	 * Initialize the shaders.
	 */
	public static void init() {
		VERTEX_RAW = Shader.createShader("sowr/gui/render/shader/raw.glsl_vertex", ShaderType.VERTEX, true);
		FRAGMENT_RAW = Shader.createShader("sowr/gui/render/shader/raw.glsl_fragment", ShaderType.FRAGMENT, true);

		VERTEX_SAMPLERLESS_TEXTURE = Shader.createShader("sowr/gui/render/shader/samplerless_texture.glsl_vertex",
				ShaderType.VERTEX, true);
		FRAGMENT_SAMPLERLESS_TEXTURE = Shader.createShader("sowr/gui/render/shader/samplerless_texture.glsl_fragment",
				ShaderType.FRAGMENT, true);
	}

	/**
	 * Delete the shaders.
	 */
	public static void abrogate() {
		VERTEX_RAW.abrogate();
		FRAGMENT_RAW.abrogate();

		VERTEX_SAMPLERLESS_TEXTURE.abrogate();
		FRAGMENT_SAMPLERLESS_TEXTURE.abrogate();
	}

}
