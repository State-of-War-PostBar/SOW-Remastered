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

		ShaderType(String _name) {
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
	 * @param fp        Path of the shader source file.
	 * 
	 * @param type      Type of the shader.
	 * 
	 * @param inArchive If the shader source file is in the archive.
	 * 
	 * @return The shader created.
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

	/**
	 * Load a vertex shader of a source file.
	 * 
	 * @param fp Path of the shader source file.
	 * 
	 * @return The vertex shader created.
	 */
	private static Shader loadVertexShader(String fp) {
		int vs = glCreateShader(GL_VERTEX_SHADER);
		try {
			glShaderSource(vs, Utils.readFileToString(fp));
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
	 * @param fp Path of the resource file in archive.
	 * 
	 * @return The vertex shader created.
	 */
	private static Shader loadVertexShaderA(String fp) {
		int vs = glCreateShader(GL_VERTEX_SHADER);

		try {
			glShaderSource(vs, Utils.listToString(Utils.getResAsStrings(fp), true));
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
	 * @param fp Path of the shader source file.
	 * 
	 * @return The fragment shader created.
	 */
	private static Shader loadFragmentShader(String fp) {
		int fs = glCreateShader(GL_FRAGMENT_SHADER);
		try {
			glShaderSource(fs, Utils.readFileToString(fp));
		} catch (IOException e) {
			logger.error("Error at loading a shader source!" + Utils.nl() + e.getLocalizedMessage());
		}

		glCompileShader(fs);
		if (glGetShaderi(fs, GL_COMPILE_STATUS) != 1) {
			logger.error("Error at compiling an OpenGL Fragment shader!");
			logger.error(glGetShaderInfoLog(fs));
		}

		return new Shader(fs, ShaderType.FRAGMENT);
	}

	/**
	 * Load a fragment shader in the archive.
	 * 
	 * @param fp Path of the resource file in archive.
	 * 
	 * @return The fragment shader created.
	 */
	private static Shader loadFragmentShaderA(String fp) {
		int fs = glCreateShader(GL_FRAGMENT_SHADER);
		try {
			glShaderSource(fs, Utils.listToString(Utils.getResAsStrings(fp), true));
		} catch (IOException e) {
			logger.error("Error at loading a shader source!" + Utils.nl() + e.getLocalizedMessage());
		}

		glCompileShader(fs);
		if (glGetShaderi(fs, GL_COMPILE_STATUS) == GL_FALSE) {
			logger.error("Error at compiling an OpenGL Fragment shader!");
			logger.error(glGetShaderInfoLog(fs));
		}

		return new Shader(fs, ShaderType.FRAGMENT);
	}

	/**
	 * Load a geometric shader of a source file.
	 * 
	 * @param fp Path of the shader source file.
	 * 
	 * @return The geometric shader created.
	 */
	private static Shader loadGeometricShader(String fp) {
		int gs = glCreateShader(GL_GEOMETRY_SHADER);
		try {
			glShaderSource(gs, Utils.readFileToString(fp));
		} catch (IOException e) {
			logger.error("Error at loading a shader source!" + Utils.nl() + e.getLocalizedMessage());
		}

		glCompileShader(gs);
		if (glGetShaderi(gs, GL_COMPILE_STATUS) == GL_FALSE) {
			logger.error("Error at compiling an OpenGL Geometric shader!");
			logger.error(glGetShaderInfoLog(gs));
		}

		return new Shader(gs, ShaderType.GEOMETRY);
	}

	/**
	 * Load a geometric shader in the archive.
	 * 
	 * @param fp Path of the resource file in archive.
	 * 
	 * @return The geometric shader created.
	 */
	private static Shader loadGeometricShaderA(String fp) {
		int gs = glCreateShader(GL_GEOMETRY_SHADER);

		try {
			glShaderSource(gs, Utils.listToString(Utils.getResAsStrings(fp), true));
		} catch (IOException e) {
			logger.error("Error at loading a shader source!" + Utils.nl() + e.getLocalizedMessage());
		}

		glCompileShader(gs);
		if (glGetShaderi(gs, GL_COMPILE_STATUS) == GL_FALSE) {
			logger.error("Error at compiling an OpenGL Geometric shader!");
			logger.error(glGetShaderInfoLog(gs));
		}

		return new Shader(gs, ShaderType.GEOMETRY);
	}

	/**
	 * Load a tessellation controlling shader of a source file.
	 * 
	 * @param fp Path of the shader source file.
	 * 
	 * @return The tessellation controlling shader created.
	 */
	private static Shader loadTessControllingShader(String fp) {
		int tcs = glCreateShader(GL_TESS_CONTROL_SHADER);

		try {
			glShaderSource(tcs, Utils.readFileToString(fp));
		} catch (IOException e) {
			logger.error("Error at loading a shader source!" + Utils.nl() + e.getLocalizedMessage());
		}

		glCompileShader(tcs);
		if (glGetShaderi(tcs, GL_COMPILE_STATUS) == GL_FALSE) {
			logger.error("Error at compiling an OpenGL Tessellation Controlling shader!");
			logger.error(glGetShaderInfoLog(tcs));
		}

		return new Shader(tcs, ShaderType.TESS_CONTROL);
	}

	/**
	 * Load a tessellation controlling shader in the archive.
	 * 
	 * @param fp Path of the resource file in archive.
	 * 
	 * @return The tessellation controlling shader created.
	 */
	private static Shader loadTessControllingShaderA(String fp) {
		int tcs = glCreateShader(GL_TESS_CONTROL_SHADER);

		try {
			glShaderSource(tcs, Utils.listToString(Utils.getResAsStrings(fp), true));
		} catch (IOException e) {
			logger.error("Error at loading a shader source!" + Utils.nl() + e.getLocalizedMessage());
		}

		glCompileShader(tcs);
		if (glGetShaderi(tcs, GL_COMPILE_STATUS) == GL_FALSE) {
			logger.error("Error at compiling an OpenGL Tessellation Controlling shader!");
			logger.error(glGetShaderInfoLog(tcs));
		}

		return new Shader(tcs, ShaderType.TESS_CONTROL);
	}

	/**
	 * Load a tessellation evaluating shader of a source file.
	 * 
	 * @param fp Path of the shader source file.
	 * 
	 * @return The tessellation evaluating shader created.
	 */
	private static Shader loadTessEvaluatingShader(String fp) {
		int tes = glCreateShader(GL_TESS_EVALUATION_SHADER);

		try {
			glShaderSource(tes, Utils.readFileToString(fp));
		} catch (IOException e) {
			logger.error("Error at loading a shader source!" + Utils.nl() + e.getLocalizedMessage());
		}

		glCompileShader(tes);
		if (glGetShaderi(tes, GL_COMPILE_STATUS) == GL_FALSE) {
			logger.error("Error at compiling an OpenGL Tessellation Evaluating shader!");
			logger.error(glGetShaderInfoLog(tes));
		}

		return new Shader(tes, ShaderType.TESS_EVALUATION);
	}

	/**
	 * Load a tessellation evaluating shader in the archive.
	 * 
	 * @param fp Path of the resource file in archive.
	 * 
	 * @return The tessellation evaluating shader created.
	 */
	private static Shader loadTessEvaluatingShaderA(String fp) {
		int tes = glCreateShader(GL_TESS_EVALUATION_SHADER);

		try {
			glShaderSource(tes, Utils.listToString(Utils.getResAsStrings(fp), true));
		} catch (IOException e) {
			logger.error("Error at loading a shader source!" + Utils.nl() + e.getLocalizedMessage());
		}

		glCompileShader(tes);
		if (glGetShaderi(tes, GL_COMPILE_STATUS) == GL_FALSE) {
			logger.error("Error at compiling an OpenGL Tessellation Evaluating shader!");
			logger.error(glGetShaderInfoLog(tes));
		}

		return new Shader(tes, ShaderType.TESS_EVALUATION);
	}

}
