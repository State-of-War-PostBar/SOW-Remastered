package cn.stateofwar.sowr.gui.render.ogl;

import static org.lwjgl.opengl.GL45.*;

import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;

import cn.stateofwar.sowr.util.Logger;

/**
 * An OpenGL shader program.
 */
public class ShaderProgram {

	private static final Logger logger = new Logger("Render");

	/** ID of the shader program. */
	private int id;

	/** Counter for number of shaders in this shader program. */
	private int count = 0;

	/** Array of shaders. */
	private Shader[] shaders;

	public ShaderProgram(Shader[] _shaders) {
		shaders = _shaders;
		id = glCreateProgram();
		for (Shader shader : _shaders) {
			glAttachShader(id, shader.getID());
			count++;
		}

		glLinkProgram(id);
		if ((glGetProgrami(id, GL_LINK_STATUS)) == GL_FALSE) {
			logger.error("ERROR AT LINKING A OPENGL SHADER!");
			logger.error(glGetProgramInfoLog(id));
		}

		glValidateProgram(id);
		if ((glGetProgrami(id, GL_VALIDATE_STATUS)) == GL_FALSE) {
			logger.error("ERROR AT VALIDATING A OPENGL SHADER!");
			logger.error(glGetProgramInfoLog(id));
		}

		for (Shader shader : shaders) {
			glDetachShader(id, shader.getID());
			shader.delete();
		}

		logger.info("Built a shader program with " + Integer.toString((count)) + " shaders.");
	}

	/**
	 * Bind this program to current rendering.
	 */
	public void use() {
		glUseProgram(id);
	}

	/**
	 * Get the ID of this program.
	 * 
	 * @return ID of this program.
	 */
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
	public void setUniform(String name, int val) {
		int loc = getUniformLoc(name);
		if (loc != 1)
			glUniform1i(loc, val);
	}

	/**
	 * Set a uniform variable.
	 * 
	 * @param name Name of the variable.
	 * 
	 * @param val  Value to set.
	 */
	public void setUniform(String name, float val) {
		int loc = getUniformLoc(name);
		if (loc != 1)
			glUniform1f(loc, val);
	}

	/**
	 * Set a uniform variable.
	 * 
	 * @param name Name of the variable.
	 * 
	 * @param val  Value to set.
	 */
	public void setUniform(String name, Vector4f val) {
		int loc = getUniformLoc(name);
		FloatBuffer buffer = BufferUtils.createFloatBuffer(4);
		val.get(buffer);
		if (loc != 1)
			glUniform4fv(loc, buffer);
	}

	/**
	 * Set a uniform variable.
	 * 
	 * @param name Name of the variable.
	 * 
	 * @param val  Value to set.
	 */
	public void setUniform(String name, Matrix4f val) {
		int loc = getUniformLoc(name);
		FloatBuffer buffer = BufferUtils.createFloatBuffer(4 * 4);
		val.get(buffer);
		if (loc != 1)
			glUniformMatrix4fv(loc, false, buffer);
	}

	/**
	 * Bind a attribute index to an attribute variable.
	 * 
	 * @param index Index of the attribute.
	 * 
	 * @param loc   Name of the attribute variable.
	 */
	public void bindAttribute(int index, CharSequence loc) {
		glBindAttribLocation(id, index, loc);
	}

	/**
	 * Bind the fragment out color variable.
	 *
	 * @param index Color number binds.
	 * 
	 * @param loc   Variable name.
	 */
	public void bindFragmentDataLoc(int number, CharSequence name) {
		glBindFragDataLocation(id, number, name);
	}

	/**
	 * Get the location of an attribute.
	 * 
	 * @param name Attribute name.
	 * 
	 * @return Location of the attribute.
	 */
	public int getAttributeLoc(String name) {
		return glGetAttribLocation(id, name);
	}

	/**
	 * Get the location of an uniform variable.
	 * 
	 * @return The uniform variable location.
	 */
	public int getUniformLoc(String name) {
		return glGetUniformLocation(id, name);
	}

	/**
	 * Delete the shader program.
	 */
	public void delete() {
		glDeleteProgram(id);
	}

}
