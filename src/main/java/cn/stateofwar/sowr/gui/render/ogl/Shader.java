package cn.stateofwar.sowr.gui.render.ogl;

import static org.lwjgl.opengl.GL45.*;

import java.io.IOException;

import cn.stateofwar.sowr.util.Logger;
import cn.stateofwar.sowr.util.Utils;

/**
 * An OpenGL shader.
 */
public class Shader {

	private static final Logger LOGGER = new Logger("Render");

	/**
	 * Types of shaders. <i>Computing shader is not included.</i>
	 */
	public static enum ShaderType {
		VERTEX("vertex"), FRAGMENT("fragment"), GEOMETRY("geometry"), TESS_CONTROL("tess_control"),
		TESS_EVALUATION("tess_evaluation"), UNKNOWN("unknown");

		/** Name of the shader type. */
		private String name;

		private ShaderType(String _name) {
			name = _name;
		}

		public String getName() {
			return name;
		}

		/**
		 * Convert an OpenGL constant to a shader name.
		 */
		public static String gl2n(int name) {
			return gl2t(name).getName();
		}

		/**
		 * Convert an OpenGL constant to a shader type.
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
		 * Convert a shader name to an OpenGL constant.
		 */
		public static int n2gl(String name) {
			return t2gl(n2t(name));
		}

		/**
		 * Convert a shader name to a shader type.
		 */
		public static ShaderType n2t(String name) {
			for (ShaderType e : ShaderType.values())
				if (e.getName().equals(name))
					return e;

			return UNKNOWN;
		}

		/**
		 * Convert a shader type to an OpenGL constant.
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

	}

	/** ID of the shader. */
	private final int id;

	/** Type of the shader. */
	private ShaderType type;

	private Shader(int _id, ShaderType _type) {
		id = _id;
		type = _type;
	}

	/**
	 * Get the ID of the shader.
	 * 
	 * @return ID of the shader.
	 */
	public int getID() {
		return id;
	}

	/**
	 * Get the type of the shader.
	 * 
	 * 
	 * @return Type of the shader.
	 */
	public ShaderType getType() {
		return type;
	}

	/**
	 * Delete the shader and release spaces.
	 */
	public void abrogate() {
		glDeleteShader(id);
	}

	/**
	 * Create an OpenGL shader from file.
	 * 
	 * @param path       Path of the shader source file.
	 * 
	 * @param type       Type of the shader.
	 * 
	 * @param in_archive If the shader source file is in the jar archive.
	 * 
	 * @return Shader created.
	 */
	public static Shader createShader(String path, ShaderType type, boolean in_archive) {
		switch (type) {
		case VERTEX:
			return in_archive ? loadVertexShaderA(path) : loadVertexShader(path);

		case FRAGMENT:
			return in_archive ? loadFragmentShaderA(path) : loadFragmentShader(path);

		case GEOMETRY:
			return in_archive ? loadGeometricShaderA(path) : loadGeometricShader(path);

		case TESS_CONTROL:
			return in_archive ? loadTessControllingShaderA(path) : loadTessControllingShader(path);

		case TESS_EVALUATION:
			return in_archive ? loadTessEvaluatingShaderA(path) : loadTessEvaluatingShader(path);

		default:
			LOGGER.error("Failed to create a  " + type.getName() + " shader with file " + path + '!');
			return null;
		}
	}

	/**
	 * Load a vertex shader of a source file.
	 * 
	 * @param path Path of the shader source file.
	 * 
	 * @return Vertex shader created.
	 */
	private static Shader loadVertexShader(String path) {
		int shader = glCreateShader(GL_VERTEX_SHADER);
		try {
			glShaderSource(shader, Utils.readFileToString(path));
		} catch (IOException e) {
			LOGGER.error("Error at loading a shader source!" + Utils.nl() + e.getLocalizedMessage());
		}

		glCompileShader(shader);
		if (glGetShaderi(shader, GL_COMPILE_STATUS) == GL_FALSE) {
			LOGGER.error("Error at compiling an OpenGL vertex shader!");
			LOGGER.error(glGetShaderInfoLog(shader));
		}

		return new Shader(shader, ShaderType.VERTEX);
	}

	/**
	 * Load a vertex shader in the archive.
	 * 
	 * @param path Path of the resource file in archive.
	 * 
	 * @return Vertex shader created.
	 */
	private static Shader loadVertexShaderA(String path) {
		int shader = glCreateShader(GL_VERTEX_SHADER);

		try {
			glShaderSource(shader, Utils.listToString(Utils.getResAsStrings(path), true));
		} catch (IOException e) {
			LOGGER.error("Error at loading a shader source!" + Utils.nl() + e.getLocalizedMessage());
		}

		glCompileShader(shader);
		if (glGetShaderi(shader, GL_COMPILE_STATUS) == GL_FALSE) {
			LOGGER.error("Error at compiling an OpenGL vertex shader!");
			LOGGER.error(glGetShaderInfoLog(shader));
		}

		return new Shader(shader, ShaderType.VERTEX);
	}

	/**
	 * Load a fragment shader of a source file.
	 * 
	 * @param path Path of the shader source file.
	 * 
	 * @return Fragment shader created.
	 */
	private static Shader loadFragmentShader(String path) {
		int shader = glCreateShader(GL_FRAGMENT_SHADER);
		try {
			glShaderSource(shader, Utils.readFileToString(path));
		} catch (IOException e) {
			LOGGER.error("Error at loading a shader source!" + Utils.nl() + e.getLocalizedMessage());
		}

		glCompileShader(shader);
		if (glGetShaderi(shader, GL_COMPILE_STATUS) != 1) {
			LOGGER.error("Error at compiling an OpenGL fragment shader!");
			LOGGER.error(glGetShaderInfoLog(shader));
		}

		return new Shader(shader, ShaderType.FRAGMENT);
	}

	/**
	 * Load a fragment shader in the archive.
	 * 
	 * @param path Path of the resource file in archive.
	 * 
	 * @return Fragment shader created.
	 */
	private static Shader loadFragmentShaderA(String path) {
		int shader = glCreateShader(GL_FRAGMENT_SHADER);
		try {
			glShaderSource(shader, Utils.listToString(Utils.getResAsStrings(path), true));
		} catch (IOException e) {
			LOGGER.error("Error at loading a shader source!" + Utils.nl() + e.getLocalizedMessage());
		}

		glCompileShader(shader);
		if (glGetShaderi(shader, GL_COMPILE_STATUS) == GL_FALSE) {
			LOGGER.error("Error at compiling an OpenGL fragment shader!");
			LOGGER.error(glGetShaderInfoLog(shader));
		}

		return new Shader(shader, ShaderType.FRAGMENT);
	}

	/**
	 * Load a geometric shader of a source file.
	 * 
	 * @param path Path of the shader source file.
	 * 
	 * @return Geometric shader created.
	 */
	private static Shader loadGeometricShader(String path) {
		int shader = glCreateShader(GL_GEOMETRY_SHADER);
		try {
			glShaderSource(shader, Utils.readFileToString(path));
		} catch (IOException e) {
			LOGGER.error("Error at loading a shader source!" + Utils.nl() + e.getLocalizedMessage());
		}

		glCompileShader(shader);
		if (glGetShaderi(shader, GL_COMPILE_STATUS) == GL_FALSE) {
			LOGGER.error("Error at compiling an OpenGL geometric shader!");
			LOGGER.error(glGetShaderInfoLog(shader));
		}

		return new Shader(shader, ShaderType.GEOMETRY);
	}

	/**
	 * Load a geometric shader in the archive.
	 * 
	 * @param path Path of the resource file in archive.
	 * 
	 * @return Geometric shader created.
	 */
	private static Shader loadGeometricShaderA(String path) {
		int shader = glCreateShader(GL_GEOMETRY_SHADER);

		try {
			glShaderSource(shader, Utils.listToString(Utils.getResAsStrings(path), true));
		} catch (IOException e) {
			LOGGER.error("Error at loading a shader source!" + Utils.nl() + e.getLocalizedMessage());
		}

		glCompileShader(shader);
		if (glGetShaderi(shader, GL_COMPILE_STATUS) == GL_FALSE) {
			LOGGER.error("Error at compiling an OpenGL geometric shader!");
			LOGGER.error(glGetShaderInfoLog(shader));
		}

		return new Shader(shader, ShaderType.GEOMETRY);
	}

	/**
	 * Load a tessellation controlling shader of a source file.
	 * 
	 * @param path Path of the shader source file.
	 * 
	 * @return Tessellation controlling shader created.
	 */
	private static Shader loadTessControllingShader(String path) {
		int shader = glCreateShader(GL_TESS_CONTROL_SHADER);

		try {
			glShaderSource(shader, Utils.readFileToString(path));
		} catch (IOException e) {
			LOGGER.error("Error at loading a shader source!" + Utils.nl() + e.getLocalizedMessage());
		}

		glCompileShader(shader);
		if (glGetShaderi(shader, GL_COMPILE_STATUS) == GL_FALSE) {
			LOGGER.error("Error at compiling an OpenGL tessellation controlling shader!");
			LOGGER.error(glGetShaderInfoLog(shader));
		}

		return new Shader(shader, ShaderType.TESS_CONTROL);
	}

	/**
	 * Load a tessellation controlling shader in the archive.
	 * 
	 * @param path Path of the resource file in archive.
	 * 
	 * @return Tessellation controlling shader created.
	 */
	private static Shader loadTessControllingShaderA(String path) {
		int shader = glCreateShader(GL_TESS_CONTROL_SHADER);

		try {
			glShaderSource(shader, Utils.listToString(Utils.getResAsStrings(path), true));
		} catch (IOException e) {
			LOGGER.error("Error at loading a shader source!" + Utils.nl() + e.getLocalizedMessage());
		}

		glCompileShader(shader);
		if (glGetShaderi(shader, GL_COMPILE_STATUS) == GL_FALSE) {
			LOGGER.error("Error at compiling an OpenGL tessellation controlling shader!");
			LOGGER.error(glGetShaderInfoLog(shader));
		}

		return new Shader(shader, ShaderType.TESS_CONTROL);
	}

	/**
	 * Load a tessellation evaluating shader of a source file.
	 * 
	 * @param path Path of the shader source file.
	 * 
	 * @return Tessellation evaluating shader created.
	 */
	private static Shader loadTessEvaluatingShader(String path) {
		int shader = glCreateShader(GL_TESS_EVALUATION_SHADER);

		try {
			glShaderSource(shader, Utils.readFileToString(path));
		} catch (IOException e) {
			LOGGER.error("Error at loading a shader source!" + Utils.nl() + e.getLocalizedMessage());
		}

		glCompileShader(shader);
		if (glGetShaderi(shader, GL_COMPILE_STATUS) == GL_FALSE) {
			LOGGER.error("Error at compiling an OpenGL tessellation evaluating shader!");
			LOGGER.error(glGetShaderInfoLog(shader));
		}

		return new Shader(shader, ShaderType.TESS_EVALUATION);
	}

	/**
	 * Load a tessellation evaluating shader in the archive.
	 * 
	 * @param path Path of the resource file in archive.
	 * 
	 * @return Tessellation evaluating shader created.
	 */
	private static Shader loadTessEvaluatingShaderA(String path) {
		int shader = glCreateShader(GL_TESS_EVALUATION_SHADER);

		try {
			glShaderSource(shader, Utils.listToString(Utils.getResAsStrings(path), true));
		} catch (IOException e) {
			LOGGER.error("Error at loading a shader source!" + Utils.nl() + e.getLocalizedMessage());
		}

		glCompileShader(shader);
		if (glGetShaderi(shader, GL_COMPILE_STATUS) == GL_FALSE) {
			LOGGER.error("Error at compiling an OpenGL tessellation evaluating shader!");
			LOGGER.error(glGetShaderInfoLog(shader));
		}

		return new Shader(shader, ShaderType.TESS_EVALUATION);
	}

}
