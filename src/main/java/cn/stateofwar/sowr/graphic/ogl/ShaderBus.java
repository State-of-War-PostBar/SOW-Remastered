package cn.stateofwar.sowr.graphic.ogl;

import cn.stateofwar.sowr.graphic.ogl.Shader.ShaderType;

public class ShaderBus {

	public static Shader vertex_raw_color_2d;
	public static Shader fragment_raw_color_2d;

	public static Shader vertex_raw_texture_2d;
	public static Shader fragment_raw_texture_2d;

	public static void init() {
		vertex_raw_color_2d = Shader.createShader("sowr/graphic/shader/raw_color_2d.vs", ShaderType.VERTEX, true);
		fragment_raw_color_2d = Shader.createShader("sowr/graphic/shader/raw_color_2d.fs", ShaderType.FRAGMENT, true);

		vertex_raw_texture_2d = Shader.createShader("sowr/graphic/shader/raw_texture_2d.vs", ShaderType.VERTEX, true);
		fragment_raw_texture_2d = Shader.createShader("sowr/graphic/shader/raw_texture_2d.fs", ShaderType.FRAGMENT,
				true);
	}

	public static void abrogate() {
		vertex_raw_color_2d.abrogate();
		fragment_raw_color_2d.abrogate();

		vertex_raw_texture_2d.abrogate();
		fragment_raw_texture_2d.abrogate();
	}

}
