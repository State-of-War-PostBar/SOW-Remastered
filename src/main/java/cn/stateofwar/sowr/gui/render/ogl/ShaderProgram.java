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
			logger.error("Error at linking an OpenGL shader program!");
			logger.error(glGetProgramInfoLog(id));
		}

		glValidateProgram(id);
		if ((glGetProgrami(id, GL_VALIDATE_STATUS)) == GL_FALSE) {
			logger.error("Error at validating an OpenGL shader program!");
			logger.error(glGetProgramInfoLog(id));
		}
	}

	/**
	 * Get the ID of the program.
	 * 
	 * @return ID of the program.
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
	 * @param index    Index of the attribute.
	 * 
	 * @param location Attribute variable name.
	 */
	public void bindAttribute(int index, String location) {
		glBindAttribLocation(id, index, location);
	}

	/**
	 * Bind the fragment out color variable.
	 *
	 * @param number   Color number binds.
	 * 
	 * @param location Variable name.
	 */
	public void bindFragmentDataLoc(int number, String location) {
		glBindFragDataLocation(id, number, location);
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
	 * @param name  Name of the variable.
	 * 
	 * @param value Value to set.
	 */
	public void setUniform(String name, float value) {
		int loc = getUniformLoc(name);
		if (loc != 1)
			glUniform1f(loc, value);
	}

	/**
	 * Set an uniform variable.
	 * 
	 * @param name  Name of the variable.
	 * 
	 * @param value Value to set.
	 */
	public void setUniform(String name, int value) {
		int loc = getUniformLoc(name);
		if (loc != 1)
			glUniform1i(loc, value);
	}

	/**
	 * Set an uniform variable.
	 * 
	 * @param name  Name of the variable.
	 * 
	 * @param value Value to set.
	 */
	public void setUniform(String name, Vector4f value) {
		int loc = getUniformLoc(name);
		FloatBuffer buffer = BufferUtils.createFloatBuffer(4);
		value.get(buffer);
		if (loc != 1)
			glUniform4fv(loc, buffer);
	}

	/**
	 * Set an uniform variable.
	 * 
	 * @param name  Name of the variable.
	 * 
	 * @param value Value to set.
	 */
	public void setUniform(String name, Matrix4f value) {
		int loc = getUniformLoc(name);
		FloatBuffer buffer = BufferUtils.createFloatBuffer(4 * 4);
		value.get(buffer);
		if (loc != 1)
			glUniformMatrix4fv(loc, false, buffer);
	}

	/**
	 * Bind the program to current rendering.
	 */
	public void use() {
		glUseProgram(id);
	}

	/**
	 * Unbind any program from rendering.
	 */
	public void unuse() {
		glUseProgram(0);
	}

	/**
	 * Delete the shader program.
	 */
	public void abrogate() {
		glDeleteProgram(id);
	}

}
