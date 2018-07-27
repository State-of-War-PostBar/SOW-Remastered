package cn.stateofwar.sowr.gui.render;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import cn.stateofwar.sowr.util.Logger;

/**
 * An OpenGL shader program.
 */
public class ShaderProgram {

	private static final Logger logger = new Logger("Render");

	/**
	 * ID of this shader program.
	 */
	private int id;

	/**
	 * Attribute location counter for different shaders.
	 */
	private int attr = 0;

	public ShaderProgram(Shader[] shaders) {
		id = glCreateProgram();
		for (Shader shader : shaders) {
			glAttachShader(id, shader.getID());
			glBindAttribLocation(id, attr++, shader.getType().getName());
		}

		glLinkProgram(id);
		if ((glGetProgrami(id, GL_LINK_STATUS)) != 1) {
			logger.error("ERROR AT LINKING A OPENGL SHADER!");
			logger.error(glGetProgramInfoLog(id));
		}

		glValidateProgram(id);
		if ((glGetProgrami(id, GL_VALIDATE_STATUS)) != 1) {
			logger.error("ERROR AT VALIDATING A OPENGL SHADER!");
			logger.error(glGetProgramInfoLog(id));
		}

		for (Shader shader : shaders) {
			shader.delete();
		}

		logger.info("Loaded a shader program with " + Integer.toString((attr + 1)) + "shaders.");

	}

	/**
	 * Use this shader program.
	 */
	public void use() {
		glUseProgram(id);
	}

	public int getID() {
		return id;
	}

	/**
	 * Set a uniform variable.
	 * 
	 * @param name Name of the variable.
	 * 
	 * @param val  Value to set.
	 */
	public void setUniformI(String name, int val) {
		int loc = glGetUniformLocation(id, name);
		if (loc != 1) {
			glUniform1i(loc, val);
		}
	}

	/**
	 * Set a uniform variable.
	 * 
	 * @param name Name of the variable.
	 * 
	 * @param val  Value to set.
	 */
	public void setUniformF(String name, float val) {
		int loc = glGetUniformLocation(id, name);
		if (loc != 1) {
			glUniform1f(loc, val);
		}
	}

	/**
	 * Set a uniform variable.
	 * 
	 * @param name Name of the variable.
	 * 
	 * @param val  Value to set.
	 */
	public void setUniformM4f(String name, Matrix4f val) {
		int loc = glGetUniformLocation(id, name);
		FloatBuffer buffer = BufferUtils.createFloatBuffer(4 * 4);
		val.get(buffer);
		if (loc != 1) {
			glUniformMatrix4fv(loc, false, buffer);
		}
	}

	/**
	 * Bind the fragment out color variable.
	 *
	 * @param number Color number binds.
	 * 
	 * @param name   Variable name.
	 */
	public void bindFragmentDataLocation(int number, CharSequence name) {
		glBindFragDataLocation(id, number, name);
	}

	/**
	 * Get the location of an attribute variable.
	 *
	 * @param name Attribute name.
	 *
	 */
	public int getAttributeLocation(CharSequence name) {
		return glGetAttribLocation(id, name);
	}

	/**
	 * Enable a vertex attribute.
	 *
	 * @param loc Location of the vertex attribute.
	 */
	public void enableVertexAttribute(int loc) {
		glEnableVertexAttribArray(loc);
	}

	/**
	 * Disable a vertex attribute.
	 *
	 * @param loc Location of the vertex attribute.
	 */
	public void disableVertexAttribute(int loc) {
		glDisableVertexAttribArray(loc);
	}

	/**
	 * Set the vertex attribute pointer.
	 *
	 * @param loc    Location of the vertex attribute.
	 * 
	 * @param size   Number of values per vertex.
	 * 
	 * @param stride Offset between consecutive generic vertex attributes in bytes.
	 * 
	 * @param offset Offset of the first component of the first generic vertex
	 *               attribute in bytes.
	 */
	public void pointVertexAttribute(int loc, int size, int stride, int offset) {
		glVertexAttribPointer(loc, size, GL_FLOAT, false, stride, offset);
	}

	/**
	 * Get the location of an uniform variable.
	 *
	 * @param name Uniform name.
	 */
	public int getUniformLocation(CharSequence name) {
		return glGetUniformLocation(id, name);
	}

	public void delete() {
		glDeleteProgram(id);
	}

}
