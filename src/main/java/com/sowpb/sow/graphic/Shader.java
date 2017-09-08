package com.sowpb.sow.graphic;

import static org.lwjgl.opengl.GL20.*;

import java.io.IOException;
import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import com.sowpb.sow.util.Logger;
import com.sowpb.sow.util.Utils;

public class Shader {

	private static final Logger logger = new Logger("Client/Render");

	/**
	 * The ID of the shader program.
	 */
	private int prog;

	/**
	 * Vertex part of the shader.
	 */
	private int vs;

	/**
	 * Fragment part of the shader.
	 */
	private int fs;

	public Shader(String file) {
		prog = glCreateProgram();
		vs = glCreateShader(GL_VERTEX_SHADER);
		try {
			glShaderSource(vs, Utils.readFileToSingleString(file + ".vs"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		glCompileShader(vs);
		if (glGetShaderi(vs, GL_COMPILE_STATUS) != 1) {
			logger.error("ERROR AT LOADING A OPENGL VERTEX SHADER!");
			logger.error(glGetShaderInfoLog(vs));
		}

		fs = glCreateShader(GL_FRAGMENT_SHADER);
		try {
			glShaderSource(fs, Utils.readFileToSingleString(file + ".fs"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		glCompileShader(fs);
		if (glGetShaderi(fs, GL_COMPILE_STATUS) != 1) {
			logger.error("ERROR AT LOADING A OPENGL FRAGMENT SHADER!");
			logger.error(glGetShaderInfoLog(fs));
		}

		glAttachShader(prog, vs);
		glAttachShader(prog, fs);

		int att = 0;

		glBindAttribLocation(prog, att++, "vertices");
		glBindAttribLocation(prog, att++, "textures");

		glLinkProgram(prog);
		if ((glGetProgrami(prog, GL_LINK_STATUS)) != 1) {
			logger.error("ERROR AT LINKING A OPENGL SHADER!");
			logger.error(glGetProgramInfoLog(prog));
		}
		glValidateProgram(prog);
		if ((glGetProgrami(prog, GL_VALIDATE_STATUS)) != 1) {
			logger.error("ERROR AT VALIDATING A OPENGL SHADER!");
			logger.error(glGetProgramInfoLog(prog));
		}
	}

	/**
	 * Bind the current shader.
	 */
	public void bind() {
		glUseProgram(prog);
	}

	public void setUniformI(String name, int val) {
		int loc = glGetUniformLocation(prog, name);
		if (loc != -1) {
			glUniform1i(loc, val);
		}
	}

	public void setUniformF(String name, float val) {
		int loc = glGetUniformLocation(prog, name);
		if (loc != -1) {
			glUniform1f(loc, val);
		}
	}

	public void setUniformM4f(String name, Matrix4f val) {
		int loc = glGetUniformLocation(prog, name);
		FloatBuffer buffer = BufferUtils.createFloatBuffer(4 * 4);
		val.get(buffer);
		if (loc != -1) {
			glUniformMatrix4fv(loc, false, buffer);
		}
	}

	protected void finalize() throws Throwable {
		glDetachShader(prog, vs);
		glDetachShader(prog, fs);
		glDeleteShader(vs);
		glDeleteShader(fs);
		glDeleteProgram(prog);
		super.finalize();
	}

}
