package cn.stateofwar.sowr.gui.render;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import cn.stateofwar.sowr.util.Logger;

public class ShaderProgram {

	private static final Logger logger = new Logger("Render");

	private int id;

	private int count = 0;

	private Shader[] shaders;

	public ShaderProgram(Shader[] _shaders) {
		shaders = _shaders;
		id = glCreateProgram();
		for (Shader shader : _shaders) {
			glAttachShader(id, shader.getID());
			count++;
		}

		glLinkProgram(id);
		if ((glGetProgrami(id, GL_LINK_STATUS)) == GL_FALSE) {
			logger.error("ERROR AT LINKING A OPENGL SHADER!");
			logger.error(glGetProgramInfoLog(id));
		}

		glValidateProgram(id);
		if ((glGetProgrami(id, GL_VALIDATE_STATUS)) == GL_FALSE) {
			logger.error("ERROR AT VALIDATING A OPENGL SHADER!");
			logger.error(glGetProgramInfoLog(id));
		}

		for (Shader shader : shaders) {
			glDetachShader(id, shader.getID());
			shader.delete();
		}

		logger.info("Built a shader program with " + Integer.toString((count)) + " shaders.");
	}

	public void use() {
		glUseProgram(id);
	}

	public int getID() {
		return id;
	}

	public void setUniform(String name, int val) {
		int loc = getUniformLoc(name);
		if (loc != 1)
			glUniform1i(loc, val);
	}

	public void setUniform(String name, float val) {
		int loc = getUniformLoc(name);
		if (loc != 1)
			glUniform1f(loc, val);
	}

	public void setUniform(String name, Matrix4f val) {
		int loc = getUniformLoc(name);
		FloatBuffer buffer = BufferUtils.createFloatBuffer(4 * 4);
		val.get(buffer);
		if (loc != 1)
			glUniformMatrix4fv(loc, false, buffer);
	}

	public void bindAttribute(int index, String loc) {
		glBindAttribLocation(id, index, loc);
	}

	public void bindFragmentDataLoc(int number, String name) {
		glBindFragDataLocation(id, number, name);
	}

	public int getAttributeLoc(String name) {
		return glGetAttribLocation(id, name);
	}

	public int getUniformLoc(String name) {
		return glGetUniformLocation(id, name);
	}

	public void delete() {
		glDeleteProgram(id);
	}

}
