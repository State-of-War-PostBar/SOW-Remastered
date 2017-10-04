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

	public static enum ShaderType {
		VERTEX("Vertex"), FRAGMENT("Fragment"), GEOMETRY("Geometry"), TESS_CONTROL("Tess_Control"), TESS_EVALUATION(
				"Tess_evaluation");

		private String name;

		private ShaderType(String n) {
			name = n;
		}

		public String getName() {
			return name;
		}

		/**
		 * Parse an OpenGL GLuint name to String names.
		 */
		static String glShaderName(int shader) {
			switch (glGetShaderi(shader, GL_SHADER_TYPE)) {
			case GL_VERTEX_SHADER:
				return VERTEX.getName();
			case GL_FRAGMENT_SHADER:
				return FRAGMENT.getName();
			case GL_GEOMETRY_SHADER:
				return GEOMETRY.getName();
			case GL_TESS_CONTROL_SHADER:
				return TESS_CONTROL.getName();
			case GL_TESS_EVALUATION_SHADER:
				return TESS_EVALUATION.getName();
			default:
				return null;
			}
		}
	}

	/**
	 * Create an OpenGL shader.
	 */
	public static int createShader(String fn, ShaderType type) {
		switch (type) {
		case VERTEX:
			return loadVertexShader(fn);

		case FRAGMENT:
			return loadFragmentShader(fn);

		case GEOMETRY:
			return loadGeometricShader(fn);

		case TESS_CONTROL:
			return loadTessControllingShader(fn);

		case TESS_EVALUATION:
			return loadTessEvaluatingShader(fn);

		default:
			logger.error("Failed to create a  " + type.getName() + " shader with file" + fn + '!');
			return -1;
		}
	}

	/**
	 * Create an OpenGL shader program.
	 */
	public static int createProgram(int[] shaders) {
		int prog = glCreateProgram();
		int attr = 0;

		for (int shader : shaders) {
			glAttachShader(prog, shader);
			glBindAttribLocation(prog, attr++, ShaderType.glShaderName(shader));
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

		return prog;
	}

	/**
	 * Bind an OpenGL program to current rendering.
	 */
	public static void bindProgram(int prog) {
		glUseProgram(prog);
	}

	public static int loadVertexShader(String file) {
		int vs = glCreateShader(GL_VERTEX_SHADER);
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

		return vs;
	}

	public static int loadFragmentShader(String file) {
		int fs = glCreateShader(GL_FRAGMENT_SHADER);
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

		return fs;
	}

	public static int loadGeometricShader(String file) {
		int gs = glCreateShader(GL_GEOMETRY_SHADER);
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

		return gs;
	}

	public static int loadTessControllingShader(String file) {
		int tcs = glCreateShader(GL_TESS_CONTROL_SHADER);
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

		return tcs;
	}

	public static int loadTessEvaluatingShader(String file) {
		int tes = glCreateShader(GL_TESS_EVALUATION_SHADER);
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

		return tes;
	}

	public static void setUniformI(int prog, String name, int val) {
		int loc = glGetUniformLocation(prog, name);
		if (loc != -1) {
			glUniform1i(loc, val);
		}
	}

	public static void setUniformF(int prog, String name, float val) {
		int loc = glGetUniformLocation(prog, name);
		if (loc != -1) {
			glUniform1f(loc, val);
		}
	}

	public static void setUniformM4f(int prog, String name, Matrix4f val) {
		int loc = glGetUniformLocation(prog, name);
		FloatBuffer buffer = BufferUtils.createFloatBuffer(4 * 4);
		val.get(buffer);
		if (loc != -1) {
			glUniformMatrix4fv(loc, false, buffer);
		}
	}
}
