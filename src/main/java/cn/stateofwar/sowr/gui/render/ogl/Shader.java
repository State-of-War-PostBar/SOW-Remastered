package cn.stateofwar.sowr.gui.render.ogl;

import static org.lwjgl.opengl.GL45.*;

import java.io.IOException;

import cn.stateofwar.sowr.util.Logger;
import cn.stateofwar.sowr.util.Utils;

/**
 * An OpenGL shader.
 */
public class Shader {

	private static final Logger logger = new Logger("Render");

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
	 * Get the ID of this shader.
	 * 
	 * @return ID of this shader.
	 */
	public int getID() {
		return id;
	}

	/**
	 * Get the type of this shader.
	 * 
	 * 
	 * @return Type of this shader.
	 */
	public ShaderType getType() {
		return type;
	}

	/**
	 * Delete this shader and release spaces.
	 */
	public void abrogate() {
		glDeleteShader(id);
	}

	/**
	 * Create an OpenGL shader from file.
	 * 
	 * @param path    Path of the shader source file.
	 * 
	 * @param type    Type of the shader.
	 * 
	 * @param archive If the shader source file is in the jar archive.
	 * 
	 * @return Shader created.
	 */
	public static Shader createShader(String path, ShaderType type, boolean archive) {
		switch (type) {
		case VERTEX:
			return archive ? loadVertexShaderA(path) : loadVertexShader(path);

		case FRAGMENT:
			return archive ? loadFragmentShaderA(path) : loadFragmentShader(path);

		case GEOMETRY:
			return archive ? loadGeometricShaderA(path) : loadGeometricShader(path);

		case TESS_CONTROL:
			return archive ? loadTessControllingShaderA(path) : loadTessControllingShader(path);

		case TESS_EVALUATION:
			return archive ? loadTessEvaluatingShaderA(path) : loadTessEvaluatingShader(path);

		default:
			logger.error("Failed to create a  " + type.getName() + " shader with file " + path + '!');
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
		int vs = glCreateShader(GL_VERTEX_SHADER);
		try {
			glShaderSource(vs, Utils.readFileToString(path));
		} catch (IOException e) {
			logger.error("Error at loading a shader source!" + Utils.nl() + e.getLocalizedMessage());
		}

		glCompileShader(vs);
		if (glGetShaderi(vs, GL_COMPILE_STATUS) == GL_FALSE) {
			logger.error("Error at compiling an OpenGL vertex shader!");
			logger.error(glGetShaderInfoLog(vs));
		}

		return new Shader(vs, ShaderType.VERTEX);
	}

	/**
	 * Load a vertex shader in the archive.
	 * 
	 * @param path Path of the resource file in archive.
	 * 
	 * @return Vertex shader created.
	 */
	private static Shader loadVertexShaderA(String path) {
		int vs = glCreateShader(GL_VERTEX_SHADER);

		try {
			glShaderSource(vs, Utils.listToString(Utils.getResAsStrings(path), true));
		} catch (IOException e) {
			logger.error("Error at loading a shader source!" + Utils.nl() + e.getLocalizedMessage());
		}

		glCompileShader(vs);
		if (glGetShaderi(vs, GL_COMPILE_STATUS) == GL_FALSE) {
			logger.error("Error at compiling an OpenGL vertex shader!");
			logger.error(glGetShaderInfoLog(vs));
		}

		return new Shader(vs, ShaderType.VERTEX);
	}

	/**
	 * Load a fragment shader of a source file.
	 * 
	 * @param path Path of the shader source file.
	 * 
	 * @return Fragment shader created.
	 */
	private static Shader loadFragmentShader(String path) {
		int fs = glCreateShader(GL_FRAGMENT_SHADER);
		try {
			glShaderSource(fs, Utils.readFileToString(path));
		} catch (IOException e) {
			logger.error("Error at loading a shader source!" + Utils.nl() + e.getLocalizedMessage());
		}

		glCompileShader(fs);
		if (glGetShaderi(fs, GL_COMPILE_STATUS) != 1) {
			logger.error("Error at compiling an OpenGL fragment shader!");
			logger.error(glGetShaderInfoLog(fs));
		}

		return new Shader(fs, ShaderType.FRAGMENT);
	}

	/**
	 * Load a fragment shader in the archive.
	 * 
	 * @param path Path of the resource file in archive.
	 * 
	 * @return Fragment shader created.
	 */
	private static Shader loadFragmentShaderA(String path) {
		int fs = glCreateShader(GL_FRAGMENT_SHADER);
		try {
			glShaderSource(fs, Utils.listToString(Utils.getResAsStrings(path), true));
		} catch (IOException e) {
			logger.error("Error at loading a shader source!" + Utils.nl() + e.getLocalizedMessage());
		}

		glCompileShader(fs);
		if (glGetShaderi(fs, GL_COMPILE_STATUS) == GL_FALSE) {
			logger.error("Error at compiling an OpenGL fragment shader!");
			logger.error(glGetShaderInfoLog(fs));
		}

		return new Shader(fs, ShaderType.FRAGMENT);
	}

	/**
	 * Load a geometric shader of a source file.
	 * 
	 * @param path Path of the shader source file.
	 * 
	 * @return Geometric shader created.
	 */
	private static Shader loadGeometricShader(String path) {
		int gs = glCreateShader(GL_GEOMETRY_SHADER);
		try {
			glShaderSource(gs, Utils.readFileToString(path));
		} catch (IOException e) {
			logger.error("Error at loading a shader source!" + Utils.nl() + e.getLocalizedMessage());
		}

		glCompileShader(gs);
		if (glGetShaderi(gs, GL_COMPILE_STATUS) == GL_FALSE) {
			logger.error("Error at compiling an OpenGL geometric shader!");
			logger.error(glGetShaderInfoLog(gs));
		}

		return new Shader(gs, ShaderType.GEOMETRY);
	}

	/**
	 * Load a geometric shader in the archive.
	 * 
	 * @param path Path of the resource file in archive.
	 * 
	 * @return Geometric shader created.
	 */
	private static Shader loadGeometricShaderA(String path) {
		int gs = glCreateShader(GL_GEOMETRY_SHADER);

		try {
			glShaderSource(gs, Utils.listToString(Utils.getResAsStrings(path), true));
		} catch (IOException e) {
			logger.error("Error at loading a shader source!" + Utils.nl() + e.getLocalizedMessage());
		}

		glCompileShader(gs);
		if (glGetShaderi(gs, GL_COMPILE_STATUS) == GL_FALSE) {
			logger.error("Error at compiling an OpenGL geometric shader!");
			logger.error(glGetShaderInfoLog(gs));
		}

		return new Shader(gs, ShaderType.GEOMETRY);
	}

	/**
	 * Load a tessellation controlling shader of a source file.
	 * 
	 * @param path Path of the shader source file.
	 * 
	 * @return Tessellation controlling shader created.
	 */
	private static Shader loadTessControllingShader(String path) {
		int tcs = glCreateShader(GL_TESS_CONTROL_SHADER);

		try {
			glShaderSource(tcs, Utils.readFileToString(path));
		} catch (IOException e) {
			logger.error("Error at loading a shader source!" + Utils.nl() + e.getLocalizedMessage());
		}

		glCompileShader(tcs);
		if (glGetShaderi(tcs, GL_COMPILE_STATUS) == GL_FALSE) {
			logger.error("Error at compiling an OpenGL tessellation controlling shader!");
			logger.error(glGetShaderInfoLog(tcs));
		}

		return new Shader(tcs, ShaderType.TESS_CONTROL);
	}

	/**
	 * Load a tessellation controlling shader in the archive.
	 * 
	 * @param path Path of the resource file in archive.
	 * 
	 * @return Tessellation controlling shader created.
	 */
	private static Shader loadTessControllingShaderA(String path) {
		int tcs = glCreateShader(GL_TESS_CONTROL_SHADER);

		try {
			glShaderSource(tcs, Utils.listToString(Utils.getResAsStrings(path), true));
		} catch (IOException e) {
			logger.error("Error at loading a shader source!" + Utils.nl() + e.getLocalizedMessage());
		}

		glCompileShader(tcs);
		if (glGetShaderi(tcs, GL_COMPILE_STATUS) == GL_FALSE) {
			logger.error("Error at compiling an OpenGL tessellation controlling shader!");
			logger.error(glGetShaderInfoLog(tcs));
		}

		return new Shader(tcs, ShaderType.TESS_CONTROL);
	}

	/**
	 * Load a tessellation evaluating shader of a source file.
	 * 
	 * @param path Path of the shader source file.
	 * 
	 * @return Tessellation evaluating shader created.
	 */
	private static Shader loadTessEvaluatingShader(String path) {
		int tes = glCreateShader(GL_TESS_EVALUATION_SHADER);

		try {
			glShaderSource(tes, Utils.readFileToString(path));
		} catch (IOException e) {
			logger.error("Error at loading a shader source!" + Utils.nl() + e.getLocalizedMessage());
		}

		glCompileShader(tes);
		if (glGetShaderi(tes, GL_COMPILE_STATUS) == GL_FALSE) {
			logger.error("Error at compiling an OpenGL tessellation evaluating shader!");
			logger.error(glGetShaderInfoLog(tes));
		}

		return new Shader(tes, ShaderType.TESS_EVALUATION);
	}

	/**
	 * Load a tessellation evaluating shader in the archive.
	 * 
	 * @param path Path of the resource file in archive.
	 * 
	 * @return Tessellation evaluating shader created.
	 */
	private static Shader loadTessEvaluatingShaderA(String path) {
		int tes = glCreateShader(GL_TESS_EVALUATION_SHADER);

		try {
			glShaderSource(tes, Utils.listToString(Utils.getResAsStrings(path), true));
		} catch (IOException e) {
			logger.error("Error at loading a shader source!" + Utils.nl() + e.getLocalizedMessage());
		}

		glCompileShader(tes);
		if (glGetShaderi(tes, GL_COMPILE_STATUS) == GL_FALSE) {
			logger.error("Error at compiling an OpenGL tessellation evaluating shader!");
			logger.error(glGetShaderInfoLog(tes));
		}

		return new Shader(tes, ShaderType.TESS_EVALUATION);
	}

}
