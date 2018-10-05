package cn.stateofwar.sowr.gui.render.ogl;

import cn.stateofwar.sowr.gui.render.ogl.Shader.ShaderType;

/**
 * Pre-built shaders for shader programs.
 */
public class Shaders {

	/** Vertex shader for raw models. */
	public static Shader vertex_raw;

	/** Fragment shader for raw models. */
	public static Shader fragment_raw;

	/** Vertex shader for textured models. */
	public static Shader vertex_sampleless_texture;

	/** Fragment shader for textured models. */
	public static Shader fragment_sampleless_texture;

	/**
	 * Initialize the shaders.
	 */
	public static void init() {
		vertex_raw = Shader.createShader("sowr/gui/render/shader/raw.glsl_vertex", ShaderType.VERTEX, true);
		fragment_raw = Shader.createShader("sowr/gui/render/shader/raw.glsl_fragment", ShaderType.FRAGMENT, true);

		vertex_sampleless_texture = Shader.createShader("sowr/gui/render/shader/samplerless_texture.glsl_vertex",
				ShaderType.VERTEX, true);
		fragment_sampleless_texture = Shader.createShader("sowr/gui/render/shader/samplerless_texture.glsl_fragment",
				ShaderType.FRAGMENT, true);
	}

	/**
	 * Delete the shaders.
	 */
	public static void abrogate() {
		vertex_raw.abrogate();
		fragment_raw.abrogate();

		vertex_sampleless_texture.abrogate();
		fragment_sampleless_texture.abrogate();
	}

}
