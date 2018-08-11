package cn.stateofwar.sowr.gui.render;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL32.*;
import static org.lwjgl.opengl.GL40.*;

import java.io.IOException;

import cn.stateofwar.sowr.util.Logger;
import cn.stateofwar.sowr.util.Utils;

public class Shader {

	private static final Logger logger = new Logger("Render");

	private int id;

	private ShaderType type;

	private Shader(int _id, ShaderType _type) {
		id = _id;
		type = _type;
	}

	public int getID() {
		return id;
	}

	public ShaderType getType() {
		return type;
	}

	public void delete() {
		glDeleteShader(id);
	}

	/**
	 * Create a shader.
	 * 
	 * @param fp        Path of the shader source file.
	 * @param type      Type of the shader.
	 * @param inArchive If the source file is in the jar.
	 */
	public static Shader createShader(String fp, ShaderType type, boolean inArchive) {
		switch (type) {
		case VERTEX:
			return inArchive ? loadVertexShaderA(fp) : loadVertexShader(fp);

		case FRAGMENT:
			return inArchive ? loadFragmentShaderA(fp) : loadFragmentShader(fp);

		case GEOMETRY:
			return inArchive ? loadGeometricShaderA(fp) : loadGeometricShader(fp);

		case TESS_CONTROL:
			return inArchive ? loadTessControllingShaderA(fp) : loadTessControllingShader(fp);

		case TESS_EVALUATION:
			return inArchive ? loadTessEvaluatingShaderA(fp) : loadTessEvaluatingShader(fp);

		default:
			logger.error("Failed to create a  " + type.getName() + " shader with file " + fp + '!');
			return null;
		}
	}

	private static Shader loadVertexShader(String fp) {
		int vs = glCreateShader(GL_VERTEX_SHADER);
		try {
			glShaderSource(vs, Utils.readFileToString(fp));
		} catch (IOException e) {
			e.printStackTrace();
		}

		glCompileShader(vs);
		if (glGetShaderi(vs, GL_COMPILE_STATUS) == GL_FALSE) {
			logger.error("ERROR AT LOADING AN OPENGL VERTEX SHADER!");
			logger.error(glGetShaderInfoLog(vs));
		}

		logger.info("Created an OpenGL vertex shader.");

		return new Shader(vs, ShaderType.VERTEX);
	}

	private static Shader loadVertexShaderA(String fp) {
		int vs = glCreateShader(GL_VERTEX_SHADER);
		try {
			glShaderSource(vs, Utils.listToString(Utils.getResAsStrings(fp)));
		} catch (IOException e) {
			e.printStackTrace();
		}

		glCompileShader(vs);
		if (glGetShaderi(vs, GL_COMPILE_STATUS) == GL_FALSE) {
			logger.error("ERROR AT LOADING AN OPENGL VERTEX SHADER!");
			logger.error(glGetShaderInfoLog(vs));
		}

		logger.info("Created an OpenGL vertex shader.");

		return new Shader(vs, ShaderType.VERTEX);
	}

	private static Shader loadFragmentShader(String fp) {
		int fs = glCreateShader(GL_FRAGMENT_SHADER);
		try {
			glShaderSource(fs, Utils.readFileToString(fp));
		} catch (IOException e) {
			e.printStackTrace();
		}

		glCompileShader(fs);
		if (glGetShaderi(fs, GL_COMPILE_STATUS) != 1) {
			logger.error("ERROR AT LOADING AN OPENGL FRAGMENT SHADER!");
			logger.error(glGetShaderInfoLog(fs));
		}

		logger.info("Created an OpenGL fragment shader.");

		return new Shader(fs, ShaderType.FRAGMENT);
	}

	private static Shader loadFragmentShaderA(String fp) {
		int fs = glCreateShader(GL_FRAGMENT_SHADER);
		try {
			glShaderSource(fs, Utils.listToString(Utils.getResAsStrings(fp)));
		} catch (IOException e) {
			e.printStackTrace();
		}

		glCompileShader(fs);
		if (glGetShaderi(fs, GL_COMPILE_STATUS) == GL_FALSE) {
			logger.error("ERROR AT LOADING AN OPENGL FRAGMENT SHADER!");
			logger.error(glGetShaderInfoLog(fs));
		}

		logger.info("Created an OpenGL fragment shader.");

		return new Shader(fs, ShaderType.FRAGMENT);
	}

	private static Shader loadGeometricShader(String fp) {
		int gs = glCreateShader(GL_GEOMETRY_SHADER);
		try {
			glShaderSource(gs, Utils.readFileToString(fp));
		} catch (IOException e) {
			e.printStackTrace();
		}

		glCompileShader(gs);
		if (glGetShaderi(gs, GL_COMPILE_STATUS) == GL_FALSE) {
			logger.error("ERROR AT LOADING AN OPENGL GEOMETRIC SHADER!");
			logger.error(glGetShaderInfoLog(gs));
		}

		logger.info("Created an OpenGL geometry shader.");

		return new Shader(gs, ShaderType.GEOMETRY);
	}

	private static Shader loadGeometricShaderA(String fp) {
		int gs = glCreateShader(GL_GEOMETRY_SHADER);
		try {
			glShaderSource(gs, Utils.listToString(Utils.getResAsStrings(fp)));
		} catch (IOException e) {
			e.printStackTrace();
		}

		glCompileShader(gs);
		if (glGetShaderi(gs, GL_COMPILE_STATUS) == GL_FALSE) {
			logger.error("ERROR AT LOADING AN OPENGL GEOMETRIC SHADER!");
			logger.error(glGetShaderInfoLog(gs));
		}

		logger.info("Created an OpenGL geometry shader.");

		return new Shader(gs, ShaderType.GEOMETRY);
	}

	private static Shader loadTessControllingShader(String fp) {
		int tcs = glCreateShader(GL_TESS_CONTROL_SHADER);
		try {
			glShaderSource(tcs, Utils.readFileToString(fp));
		} catch (IOException e) {
			e.printStackTrace();
		}

		glCompileShader(tcs);
		if (glGetShaderi(tcs, GL_COMPILE_STATUS) == GL_FALSE) {
			logger.error("ERROR AT LOADING AN OPENGL TESSELLATION CONTROLLING SHADER!");
			logger.error(glGetShaderInfoLog(tcs));
		}

		logger.info("Created an OpenGL tessellation controlling shader.");

		return new Shader(tcs, ShaderType.TESS_CONTROL);
	}

	private static Shader loadTessControllingShaderA(String fp) {
		int tcs = glCreateShader(GL_TESS_CONTROL_SHADER);
		try {
			glShaderSource(tcs, Utils.listToString(Utils.getResAsStrings(fp)));
		} catch (IOException e) {
			e.printStackTrace();
		}

		glCompileShader(tcs);
		if (glGetShaderi(tcs, GL_COMPILE_STATUS) == GL_FALSE) {
			logger.error("ERROR AT LOADING AN OPENGL TESSELLATION CONTROLLING SHADER!");
			logger.error(glGetShaderInfoLog(tcs));
		}

		logger.info("Created an OpenGL tessellation controlling shader.");

		return new Shader(tcs, ShaderType.TESS_CONTROL);
	}

	private static Shader loadTessEvaluatingShader(String fp) {
		int tes = glCreateShader(GL_TESS_EVALUATION_SHADER);
		try {
			glShaderSource(tes, Utils.readFileToString(fp));
		} catch (IOException e) {
			e.printStackTrace();
		}

		glCompileShader(tes);
		if (glGetShaderi(tes, GL_COMPILE_STATUS) == GL_FALSE) {
			logger.error("ERROR AT LOADING AN OPENGL TESSELLATION EVALUATING SHADER!");
			logger.error(glGetShaderInfoLog(tes));
		}

		logger.info("Created an OpenGL tessellation evaluating shader.");

		return new Shader(tes, ShaderType.TESS_EVALUATION);
	}

	private static Shader loadTessEvaluatingShaderA(String fp) {
		int tes = glCreateShader(GL_TESS_EVALUATION_SHADER);
		try {
			glShaderSource(tes, Utils.listToString(Utils.getResAsStrings(fp)));
		} catch (IOException e) {
			e.printStackTrace();
		}

		glCompileShader(tes);
		if (glGetShaderi(tes, GL_COMPILE_STATUS) == GL_FALSE) {
			logger.error("ERROR AT LOADING AN OPENGL TESSELLATION EVALUATING SHADER!");
			logger.error(glGetShaderInfoLog(tes));
		}

		logger.info("Created an OpenGL tessellation evaluating shader.");

		return new Shader(tes, ShaderType.TESS_EVALUATION);
	}

	public static enum ShaderType {
		VERTEX("vertex"), FRAGMENT("fragment"), GEOMETRY("geometry"), TESS_CONTROL("tess_control"),
		TESS_EVALUATION("tess_evaluation"), UNKNOWN("unknown");

		private String name;

		ShaderType(String _name) {
			name = _name;
		}

		public String getName() {
			return name;
		}

		/**
		 * Convert a regular name to a ShaderType.
		 */
		public static ShaderType n2t(String name) {
			for (ShaderType e : ShaderType.values())
				if (e.getName().equals(name)) {
					return e;
				}
			return UNKNOWN;
		}

		/**
		 * Convert a regular name to an OpenGL constant.
		 */
		public static int n2gl(String name) {
			return t2gl(n2t(name));
		}

		/**
		 * Convert a ShaderType to an OpenGL constant.
		 */
		public static int t2gl(ShaderType type) {
			switch (type) {
			case VERTEX:
				return GL_VERTEX_SHADER;
			case FRAGMENT:
				return GL_FRAGMENT_SHADER;
			case GEOMETRY:
				return GL_GEOMETRY_SHADER;
			case TESS_CONTROL:
				return GL_TESS_CONTROL_SHADER;
			case TESS_EVALUATION:
				return GL_TESS_EVALUATION_SHADER;
			default:
				return 0;
			}
		}

		/**
		 * Convert an OpenGL constant to a ShaderType.
		 */
		public static ShaderType gl2t(int name) {
			switch (name) {
			case GL_VERTEX_SHADER:
				return VERTEX;
			case GL_FRAGMENT_SHADER:
				return FRAGMENT;
			case GL_GEOMETRY_SHADER:
				return GEOMETRY;
			case GL_TESS_CONTROL_SHADER:
				return TESS_CONTROL;
			case GL_TESS_EVALUATION_SHADER:
				return TESS_EVALUATION;
			default:
				return UNKNOWN;
			}
		}

		/**
		 * Convert an OpenGL constant to a regular name.
		 */
		public static String gl2n(int name) {
			return gl2t(name).getName();
		}

	}

}
