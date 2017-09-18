package com.sowpb.sow.graphic;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL32.*;
import static org.lwjgl.opengl.GL40.*;

import java.io.IOException;
import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import com.sowpb.sow.util.Logger;
import com.sowpb.sow.util.Utils;

public class Shader {

	private static final Logger logger = new Logger("Render");

	enum ShaderType {
		VERTEX, FRAGMENT, GEOMETRY, TESS_CONTROL, TESS_EVALUATION
	}

	/**
	 * The shader program consists of different types of shader.
	 */
	private int prog;

	/**
	 * The vertex part of the shader if exists.
	 */
	private int vs;

	/**
	 * The fragment part of the shader if exists.
	 */
	private int fs;

	/**
	 * The geometric part of the shader if exists.
	 */
	private int gs;

	/**
	 * The tessellation parts of the shader if exists.
	 */
	private int tcs, tes;

	private int attr = 0;

	private Shader(String file, ShaderType type) {
		prog = glCreateProgram();
		switch (type) {
		case VERTEX:
			loadVertexShader(file);
			break;

		case FRAGMENT:
			loadFragmentShader(file);
			break;

		case GEOMETRY:
			loadGeometricShader(file);
			break;

		case TESS_CONTROL:
			loadTessControllingShader(file);
			break;

		case TESS_EVALUATION:
			loadTessEvaluatingShader(file);
			break;

		default:
			break;
		}

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

	public void loadVertexShader(String file) {
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
		glAttachShader(prog, vs);
		glBindAttribLocation(prog, attr++, "vertices");
	}

	public void loadFragmentShader(String file) {
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
		glAttachShader(prog, fs);
		glBindAttribLocation(prog, attr++, "textures");
	}

	public void loadGeometricShader(String file) {
		gs = glCreateShader(GL_GEOMETRY_SHADER);
		try {
			glShaderSource(gs, Utils.readFileToSingleString(file + ".gs"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		glCompileShader(gs);
		if (glGetShaderi(gs, GL_COMPILE_STATUS) != 1) {
			logger.error("ERROR AT LOADING A OPENGL GEOMETRIC SHADER!");
			logger.error(glGetShaderInfoLog(gs));
		}
		glAttachShader(prog, gs);
		glBindAttribLocation(prog, attr++, "geometries");
	}

	public void loadTessControllingShader(String file) {
		tcs = glCreateShader(GL_TESS_CONTROL_SHADER);
		try {
			glShaderSource(tcs, Utils.readFileToSingleString(file + ".tcs"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		glCompileShader(tcs);
		if (glGetShaderi(tcs, GL_COMPILE_STATUS) != 1) {
			logger.error("ERROR AT LOADING A OPENGL TESSELLATION CONTROLLING SHADER!");
			logger.error(glGetShaderInfoLog(tcs));
		}
		glAttachShader(prog, tcs);
		glBindAttribLocation(prog, attr++, "tess_control");
	}

	public void loadTessEvaluatingShader(String file) {
		tes = glCreateShader(GL_TESS_EVALUATION_SHADER);
		try {
			glShaderSource(tes, Utils.readFileToSingleString(file + ".tes"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		glCompileShader(tes);
		if (glGetShaderi(tes, GL_COMPILE_STATUS) != 1) {
			logger.error("ERROR AT LOADING A OPENGL TESSELLATION EVALUATING SHADER!");
			logger.error(glGetShaderInfoLog(tes));
		}
		glAttachShader(prog, tes);
		glBindAttribLocation(prog, attr++, "tess_evaluation");
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
		glDetachShader(prog, gs);
		glDetachShader(prog, tcs);
		glDetachShader(prog, tes);
		glDeleteShader(vs);
		glDeleteShader(fs);
		glDeleteShader(gs);
		glDeleteShader(tcs);
		glDeleteShader(tes);
		glDeleteProgram(prog);
		super.finalize();
	}

}
