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
	private final int id;

	/**
	 * Create a shader program with some shaders.
	 * 
	 * @param _shaders Shaders for this program.
	 */
	public ShaderProgram(Shader[] _shaders) {
		id = glCreateProgram();
		for (Shader shader : _shaders)
			glAttachShader(id, shader.getID());

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
	 * Get the location of an attribute.
	 * 
	 * @param name Attribute variable name.
	 * 
	 * @return Location of the attribute.
	 */
	public int getAttributeLoc(String name) {
		return glGetAttribLocation(id, name);
	}

	/**
	 * Bind an attribute index to an attribute variable.
	 * 
	 * @param index Index of the attribute.
	 * 
	 * @param loc   Attribute variable name.
	 */
	public void bindAttribute(int index, String loc) {
		glBindAttribLocation(id, index, loc);
	}

	/**
	 * Bind the fragment out color variable.
	 *
	 * @param number Color number binds.
	 * 
	 * @param loc    Variable name.
	 */
	public void bindFragmentDataLoc(int number, String loc) {
		glBindFragDataLocation(id, number, loc);
	}

	/**
	 * Get the location of an uniform variable.
	 * 
	 * @param name Uniform variable name.
	 * 
	 * @return Uniform variable location.
	 */
	public int getUniformLoc(String name) {
		return glGetUniformLocation(id, name);
	}

	/**
	 * Set an uniform variable.
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
	 * Set an uniform variable.
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
	 * Set an uniform variable.
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
	 * Set an uniform variable.
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
	 * Bind this program to current rendering.
	 */
	public void use() {
		glUseProgram(id);
	}

	/**
	 * Delete the shader program.
	 */
	public void abrogate() {
		glDeleteProgram(id);
	}

}
