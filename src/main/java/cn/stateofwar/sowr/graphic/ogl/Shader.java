package cn.stateofwar.sowr.graphic.ogl;

import static org.lwjgl.opengl.GL45.*;

import java.io.IOException;

import cn.stateofwar.sowr.util.Logger;
import cn.stateofwar.sowr.util.Utils;

public class Shader {

	private static final Logger LOGGER = new Logger("Render");

	private final int id;

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

	public void abrogate() {
		glDeleteShader(id);
	}

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

	private static Shader loadVertexShader(String path) {
		int shader = glCreateShader(GL_VERTEX_SHADER);

		try {
			glShaderSource(shader, Utils.readFileToString(path));
		} catch (IOException e) {
			LOGGER.error("Error at loading a shader source!");
			LOGGER.error(e.getLocalizedMessage());
		}

		glCompileShader(shader);
		if (glGetShaderi(shader, GL_COMPILE_STATUS) == GL_FALSE) {
			LOGGER.error("Error at compiling an OpenGL vertex shader!");
			LOGGER.error(glGetShaderInfoLog(shader));
		}

		LOGGER.info("Loaded a " + ShaderType.VERTEX.name + " shader at path " + path + ".");
		return new Shader(shader, ShaderType.VERTEX);
	}

	private static Shader loadVertexShaderA(String path) {
		int shader = glCreateShader(GL_VERTEX_SHADER);

		try {
			glShaderSource(shader, Utils.listToString(Utils.getResourceAsStrings(path), true));
		} catch (IOException e) {
			LOGGER.error("Error at loading a shader source!");
			LOGGER.error(e.getLocalizedMessage());
		}

		glCompileShader(shader);
		if (glGetShaderi(shader, GL_COMPILE_STATUS) == GL_FALSE) {
			LOGGER.error("Error at compiling an OpenGL vertex shader!");
			LOGGER.error(glGetShaderInfoLog(shader));
		}

		LOGGER.info("Loaded a " + ShaderType.VERTEX.name + " shader at path [jar]" + path + ".");
		return new Shader(shader, ShaderType.VERTEX);
	}

	private static Shader loadFragmentShader(String path) {
		int shader = glCreateShader(GL_FRAGMENT_SHADER);

		try {
			glShaderSource(shader, Utils.readFileToString(path));
		} catch (IOException e) {
			LOGGER.error("Error at loading a shader source!");
			LOGGER.error(e.getLocalizedMessage());
		}

		glCompileShader(shader);
		if (glGetShaderi(shader, GL_COMPILE_STATUS) != 1) {
			LOGGER.error("Error at compiling an OpenGL fragment shader!");
			LOGGER.error(glGetShaderInfoLog(shader));
		}

		LOGGER.info("Loaded a " + ShaderType.FRAGMENT.name + " shader at path " + path + ".");
		return new Shader(shader, ShaderType.FRAGMENT);
	}

	private static Shader loadFragmentShaderA(String path) {
		int shader = glCreateShader(GL_FRAGMENT_SHADER);

		try {
			glShaderSource(shader, Utils.listToString(Utils.getResourceAsStrings(path), true));
		} catch (IOException e) {
			LOGGER.error("Error at loading a shader source!");
			LOGGER.error(e.getLocalizedMessage());
		}

		glCompileShader(shader);
		if (glGetShaderi(shader, GL_COMPILE_STATUS) == GL_FALSE) {
			LOGGER.error("Error at compiling an OpenGL fragment shader!");
			LOGGER.error(glGetShaderInfoLog(shader));
		}

		LOGGER.info("Loaded a " + ShaderType.FRAGMENT.name + " shader at path [jar]" + path + ".");
		return new Shader(shader, ShaderType.FRAGMENT);
	}

	private static Shader loadGeometricShader(String path) {
		int shader = glCreateShader(GL_GEOMETRY_SHADER);

		try {
			glShaderSource(shader, Utils.readFileToString(path));
		} catch (IOException e) {
			LOGGER.error("Error at loading a shader source!");
			LOGGER.error(e.getLocalizedMessage());
		}

		glCompileShader(shader);
		if (glGetShaderi(shader, GL_COMPILE_STATUS) == GL_FALSE) {
			LOGGER.error("Error at compiling an OpenGL geometric shader!");
			LOGGER.error(glGetShaderInfoLog(shader));
		}

		LOGGER.info("Loaded a " + ShaderType.GEOMETRY.name + " shader at path " + path + ".");
		return new Shader(shader, ShaderType.GEOMETRY);
	}

	private static Shader loadGeometricShaderA(String path) {
		int shader = glCreateShader(GL_GEOMETRY_SHADER);

		try {
			glShaderSource(shader, Utils.listToString(Utils.getResourceAsStrings(path), true));
		} catch (IOException e) {
			LOGGER.error("Error at loading a shader source!");
			LOGGER.error(e.getLocalizedMessage());
		}

		glCompileShader(shader);
		if (glGetShaderi(shader, GL_COMPILE_STATUS) == GL_FALSE) {
			LOGGER.error("Error at compiling an OpenGL geometric shader!");
			LOGGER.error(glGetShaderInfoLog(shader));
		}

		LOGGER.info("Loaded a " + ShaderType.GEOMETRY.name + " shader at path [jar]" + path + ".");
		return new Shader(shader, ShaderType.GEOMETRY);
	}

	private static Shader loadTessControllingShader(String path) {
		int shader = glCreateShader(GL_TESS_CONTROL_SHADER);

		try {
			glShaderSource(shader, Utils.readFileToString(path));
		} catch (IOException e) {
			LOGGER.error("Error at loading a shader source!");
			LOGGER.error(e.getLocalizedMessage());
		}

		glCompileShader(shader);
		if (glGetShaderi(shader, GL_COMPILE_STATUS) == GL_FALSE) {
			LOGGER.error("Error at compiling an OpenGL tessellation controlling shader!");
			LOGGER.error(glGetShaderInfoLog(shader));
		}

		LOGGER.info("Loaded a " + ShaderType.TESS_CONTROL.name + " shader at path " + path + ".");
		return new Shader(shader, ShaderType.TESS_CONTROL);
	}

	private static Shader loadTessControllingShaderA(String path) {
		int shader = glCreateShader(GL_TESS_CONTROL_SHADER);

		try {
			glShaderSource(shader, Utils.listToString(Utils.getResourceAsStrings(path), true));
		} catch (IOException e) {
			LOGGER.error("Error at loading a shader source!");
			LOGGER.error(e.getLocalizedMessage());
		}

		glCompileShader(shader);
		if (glGetShaderi(shader, GL_COMPILE_STATUS) == GL_FALSE) {
			LOGGER.error("Error at compiling an OpenGL tessellation controlling shader!");
			LOGGER.error(glGetShaderInfoLog(shader));
		}

		LOGGER.info("Loaded a " + ShaderType.TESS_CONTROL.name + " shader at path [jar]" + path + ".");
		return new Shader(shader, ShaderType.TESS_CONTROL);
	}

	private static Shader loadTessEvaluatingShader(String path) {
		int shader = glCreateShader(GL_TESS_EVALUATION_SHADER);

		try {
			glShaderSource(shader, Utils.readFileToString(path));
		} catch (IOException e) {
			LOGGER.error("Error at loading a shader source!");
			LOGGER.error(e.getLocalizedMessage());
		}

		glCompileShader(shader);
		if (glGetShaderi(shader, GL_COMPILE_STATUS) == GL_FALSE) {
			LOGGER.error("Error at compiling an OpenGL tessellation evaluating shader!");
			LOGGER.error(glGetShaderInfoLog(shader));
		}

		LOGGER.info("Loaded a " + ShaderType.TESS_EVALUATION.name + " shader at path " + path + ".");
		return new Shader(shader, ShaderType.TESS_EVALUATION);
	}

	private static Shader loadTessEvaluatingShaderA(String path) {
		int shader = glCreateShader(GL_TESS_EVALUATION_SHADER);

		try {
			glShaderSource(shader, Utils.listToString(Utils.getResourceAsStrings(path), true));
		} catch (IOException e) {
			LOGGER.error("Error at loading a shader source!");
			LOGGER.error(e.getLocalizedMessage());
		}

		glCompileShader(shader);
		if (glGetShaderi(shader, GL_COMPILE_STATUS) == GL_FALSE) {
			LOGGER.error("Error at compiling an OpenGL tessellation evaluating shader!");
			LOGGER.error(glGetShaderInfoLog(shader));
		}

		LOGGER.info("Loaded a " + ShaderType.TESS_EVALUATION.name + " shader at path [jar]" + path + ".");
		return new Shader(shader, ShaderType.TESS_EVALUATION);
	}

	public static enum ShaderType {
		VERTEX("vertex"), FRAGMENT("fragment"), GEOMETRY("geometry"), TESS_CONTROL("tessellation control"),
		TESS_EVALUATION("tessellation evaluation"), UNKNOWN("unknown");

		private String name;

		private ShaderType(String _name) {
			name = _name;
		}

		public String getName() {
			return name;
		}

		public static String gl2n(int name) {
			return gl2t(name).getName();
		}

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

		public static int n2gl(String name) {
			return t2gl(n2t(name));
		}

		public static ShaderType n2t(String name) {
			for (ShaderType e : ShaderType.values())
				if (e.getName().equals(name))
					return e;
			return UNKNOWN;
		}

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

}
