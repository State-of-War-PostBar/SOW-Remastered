package cn.stateofwar.sowr.gui.render;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL32.*;
import static org.lwjgl.opengl.GL40.*;

import java.io.IOException;

import cn.stateofwar.sowr.util.Logger;
import cn.stateofwar.sowr.util.Utils;

/**
 * An OpenGL shader.
 */
public class Shader {

	private static final Logger logger = new Logger("Render");

	/**
	 * ID of this shader.
	 */
	private int id;

	/**
	 * Type of this shader.
	 */
	private ShaderType type;

	public Shader(int _id, ShaderType _type) {
		id = _id;
		type = _type;
	}

	public int getID() {
		return id;
	}

	public ShaderType getType() {
		return type;
	}

	/**
	 * Delete this shader.
	 */
	public void delete() {
		glDeleteShader(id);
	}

	/**
	 * Create an OpenGL shader.
	 * 
	 * @param fp        The path of the shader source file.
	 * 
	 * @param type      The type of the shader.
	 * 
	 * @param inArchive If the shader source file is in the archive.
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
			logger.error("Failed to create a  " + type.getName() + " shader with file" + fp + '!');
			return null;
		}
	}

	/**
	 * Load a vertex shader of a source file.
	 * 
	 * @param fp The path of the shader source file.
	 */
	private static Shader loadVertexShader(String fp) {
		int vs = glCreateShader(GL_VERTEX_SHADER);
		try {
			glShaderSource(vs, Utils.readFileToString(fp));
		} catch (IOException e) {
			e.printStackTrace();
		}

		glCompileShader(vs);
		if (glGetShaderi(vs, GL_COMPILE_STATUS) != 1) {
			logger.error("ERROR AT LOADING A OPENGL VERTEX SHADER!");
			logger.error(glGetShaderInfoLog(vs));
		}

		logger.info("Successfully created a OpenGL vertex shader.");

		return new Shader(vs, ShaderType.VERTEX);
	}

	/**
	 * Load a vertex shader in the archive.
	 * 
	 * @param fp The path of the resource file in archive.
	 */
	private static Shader loadVertexShaderA(String fp) {
		int vs = glCreateShader(GL_VERTEX_SHADER);
		try {
			glShaderSource(vs, Utils.listToString(Utils.getResAsStrings(fp)));
		} catch (IOException e) {
			e.printStackTrace();
		}

		glCompileShader(vs);
		if (glGetShaderi(vs, GL_COMPILE_STATUS) != 1) {
			logger.error("ERROR AT LOADING A OPENGL VERTEX SHADER!");
			logger.error(glGetShaderInfoLog(vs));
		}

		logger.info("Successfully created a OpenGL vertex shader.");

		return new Shader(vs, ShaderType.VERTEX);
	}

	/**
	 * Load a fragment shader of a source file.
	 * 
	 * @param fp The path of the shader source file.
	 */
	private static Shader loadFragmentShader(String fp) {
		int fs = glCreateShader(GL_FRAGMENT_SHADER);
		try {
			glShaderSource(fs, Utils.readFileToString(fp));
		} catch (IOException e) {
			e.printStackTrace();
		}

		glCompileShader(fs);
		if (glGetShaderi(fs, GL_COMPILE_STATUS) != 1) {
			logger.error("ERROR AT LOADING A OPENGL FRAGMENT SHADER!");
			logger.error(glGetShaderInfoLog(fs));
		}

		logger.info("Successfully created a OpenGL fragment shader.");

		return new Shader(fs, ShaderType.FRAGMENT);
	}

	/**
	 * Load a fragment shader in the archive.
	 * 
	 * @param fp The path of the resource file in archive.
	 */
	private static Shader loadFragmentShaderA(String fp) {
		int fs = glCreateShader(GL_FRAGMENT_SHADER);
		try {
			glShaderSource(fs, Utils.listToString(Utils.getResAsStrings(fp)));
		} catch (IOException e) {
			e.printStackTrace();
		}

		glCompileShader(fs);
		if (glGetShaderi(fs, GL_COMPILE_STATUS) != 1) {
			logger.error("ERROR AT LOADING A OPENGL FRAGMENT SHADER!");
			logger.error(glGetShaderInfoLog(fs));
		}

		logger.info("Successfully created a OpenGL fragment shader.");

		return new Shader(fs, ShaderType.FRAGMENT);
	}

	/**
	 * Load a geometric shader of a source file.
	 * 
	 * @param fp The path of the shader source file.
	 */
	private static Shader loadGeometricShader(String fp) {
		int gs = glCreateShader(GL_GEOMETRY_SHADER);
		try {
			glShaderSource(gs, Utils.readFileToString(fp));
		} catch (IOException e) {
			e.printStackTrace();
		}

		glCompileShader(gs);
		if (glGetShaderi(gs, GL_COMPILE_STATUS) != 1) {
			logger.error("ERROR AT LOADING A OPENGL GEOMETRIC SHADER!");
			logger.error(glGetShaderInfoLog(gs));
		}

		logger.info("Successfully created a OpenGL geometry shader.");

		return new Shader(gs, ShaderType.GEOMETRY);
	}

	/**
	 * Load a geometric shader in the archive.
	 * 
	 * @param fp The path of the resource file in archive.
	 */
	private static Shader loadGeometricShaderA(String fp) {
		int gs = glCreateShader(GL_GEOMETRY_SHADER);
		try {
			glShaderSource(gs, Utils.listToString(Utils.getResAsStrings(fp)));
		} catch (IOException e) {
			e.printStackTrace();
		}

		glCompileShader(gs);
		if (glGetShaderi(gs, GL_COMPILE_STATUS) != 1) {
			logger.error("ERROR AT LOADING A OPENGL GEOMETRIC SHADER!");
			logger.error(glGetShaderInfoLog(gs));
		}

		logger.info("Successfully created a OpenGL geometry shader.");

		return new Shader(gs, ShaderType.GEOMETRY);
	}

	/**
	 * Load a tessellation controlling shader of a source file.
	 * 
	 * @param fp The path of the shader source file.
	 */
	private static Shader loadTessControllingShader(String fp) {
		int tcs = glCreateShader(GL_TESS_CONTROL_SHADER);
		try {
			glShaderSource(tcs, Utils.readFileToString(fp));
		} catch (IOException e) {
			e.printStackTrace();
		}

		glCompileShader(tcs);
		if (glGetShaderi(tcs, GL_COMPILE_STATUS) != 1) {
			logger.error("ERROR AT LOADING A OPENGL TESSELLATION CONTROLLING SHADER!");
			logger.error(glGetShaderInfoLog(tcs));
		}

		logger.info("Successfully created a OpenGL tessellation controlling shader.");

		return new Shader(tcs, ShaderType.TESS_CONTROL);
	}

	/**
	 * Load a fragment shader in the archive.
	 * 
	 * @param fp The path of the resource file in archive.
	 */
	private static Shader loadTessControllingShaderA(String fp) {
		int tcs = glCreateShader(GL_TESS_CONTROL_SHADER);
		try {
			glShaderSource(tcs, Utils.listToString(Utils.getResAsStrings(fp)));
		} catch (IOException e) {
			e.printStackTrace();
		}

		glCompileShader(tcs);
		if (glGetShaderi(tcs, GL_COMPILE_STATUS) != 1) {
			logger.error("ERROR AT LOADING A OPENGL TESSELLATION CONTROLLING SHADER!");
			logger.error(glGetShaderInfoLog(tcs));
		}

		logger.info("Successfully created a OpenGL tessellation controlling shader.");

		return new Shader(tcs, ShaderType.TESS_CONTROL);
	}

	/**
	 * Load a tessellation evaluating shader of a source file.
	 * 
	 * @param fp The path of the shader source file.
	 */
	private static Shader loadTessEvaluatingShader(String fp) {
		int tes = glCreateShader(GL_TESS_EVALUATION_SHADER);
		try {
			glShaderSource(tes, Utils.readFileToString(fp));
		} catch (IOException e) {
			e.printStackTrace();
		}

		glCompileShader(tes);
		if (glGetShaderi(tes, GL_COMPILE_STATUS) != 1) {
			logger.error("ERROR AT LOADING A OPENGL TESSELLATION EVALUATING SHADER!");
			logger.error(glGetShaderInfoLog(tes));
		}

		logger.info("Successfully created a OpenGL tessellation evaluating shader.");

		return new Shader(tes, ShaderType.TESS_EVALUATION);
	}

	/**
	 * Load a fragment shader in the archive.
	 * 
	 * @param fp The path of the resource file in archive.
	 */
	private static Shader loadTessEvaluatingShaderA(String fp) {
		int tes = glCreateShader(GL_TESS_EVALUATION_SHADER);
		try {
			glShaderSource(tes, Utils.listToString(Utils.getResAsStrings(fp)));
		} catch (IOException e) {
			e.printStackTrace();
		}

		glCompileShader(tes);
		if (glGetShaderi(tes, GL_COMPILE_STATUS) != 1) {
			logger.error("ERROR AT LOADING A OPENGL TESSELLATION EVALUATING SHADER!");
			logger.error(glGetShaderInfoLog(tes));
		}

		logger.info("Successfully created a OpenGL tessellation evaluating shader.");

		return new Shader(tes, ShaderType.TESS_EVALUATION);
	}

	/**
	 * Types of OpenGL shaders.
	 */
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
				if (e.getName().equals(name))
					return e;
			return UNKNOWN;
		}

		/**
		 * Convert a ShaderType to OpenGL inner name.
		 */
		public static int t2gl(ShaderType t) {
			switch (t) {
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
		 * Convert a regular name to OpenGL inner name.
		 */
		public static int n2gl(String name) {
			return t2gl(n2t(name));
		}

	}

}
