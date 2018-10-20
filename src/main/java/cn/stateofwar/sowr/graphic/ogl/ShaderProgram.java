package cn.stateofwar.sowr.graphic.ogl;

import static org.lwjgl.opengl.GL46.*;

import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;

import cn.stateofwar.sowr.util.Logger;

public class ShaderProgram {

	private static final Logger LOGGER = new Logger("Render");

	private final int id;

	public ShaderProgram(Shader[] _shaders) {
		id = glCreateProgram();

		for (Shader shader : _shaders)
			glAttachShader(id, shader.getID());

		glLinkProgram(id);
		if ((glGetProgrami(id, GL_LINK_STATUS)) == GL_FALSE) {
			LOGGER.error("Error at linking an OpenGL shader program!");
			LOGGER.error(glGetProgramInfoLog(id));
		}

		glValidateProgram(id);
		if ((glGetProgrami(id, GL_VALIDATE_STATUS)) == GL_FALSE) {
			LOGGER.error("Error at validating an OpenGL shader program!");
			LOGGER.error(glGetProgramInfoLog(id));
		}
	}

	public int getID() {
		return id;
	}

	public int getAttributeLoc(String name) {
		return glGetAttribLocation(id, name);
	}

	public void bindAttribute(int index, String location) {
		glBindAttribLocation(id, index, location);
	}

	public void bindFragmentDataLoc(int number, String location) {
		glBindFragDataLocation(id, number, location);
	}

	public int getUniformLoc(String name) {
		return glGetUniformLocation(id, name);
	}

	public void setUniform(String name, float value) {
		int loc = getUniformLoc(name);
		if (loc != 1)
			glUniform1f(loc, value);
	}

	public void setUniform(String name, int value) {
		int loc = getUniformLoc(name);
		if (loc != 1)
			glUniform1i(loc, value);
	}

	public void setUniform(String name, Vector4f value) {
		int loc = getUniformLoc(name);
		FloatBuffer buffer = BufferUtils.createFloatBuffer(4);
		value.get(buffer);
		if (loc != 1)
			glUniform4fv(loc, buffer);
	}

	public void setUniform(String name, Matrix4f value) {
		int loc = getUniformLoc(name);
		FloatBuffer buffer = BufferUtils.createFloatBuffer(4 * 4);
		value.get(buffer);
		if (loc != 1)
			glUniformMatrix4fv(loc, false, buffer);
	}

	public void use() {
		glUseProgram(id);
	}

	public void unuse() {
		glUseProgram(0);
	}

	public void abrogate() {
		glDeleteProgram(id);
	}

}
