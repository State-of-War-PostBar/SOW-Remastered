package cn.stateofwar.sowr.graphic.ogl;

import cn.stateofwar.sowr.graphic.ogl.Shader.ShaderType;

public class ShaderBus {

	public static Shader vertex_raw_color;

	public static Shader fragment_raw_color;

	public static void init() {
		vertex_raw_color = Shader.createShader("sowr/graphic/shader/raw_color.vs", ShaderType.VERTEX, true);
		fragment_raw_color = Shader.createShader("sowr/graphic/shader/raw_color.fs", ShaderType.FRAGMENT, true);
	}

	public static void abrogate() {
		vertex_raw_color.abrogate();
		fragment_raw_color.abrogate();
	}

}
