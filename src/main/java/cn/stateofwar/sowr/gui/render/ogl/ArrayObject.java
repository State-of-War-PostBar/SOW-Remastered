package cn.stateofwar.sowr.gui.render.ogl;

import static org.lwjgl.opengl.GL45.*;

/**
 * A vertex array object.
 */
public class ArrayObject {

	/** ID of the object. */
	private final int id;

	/**
	 * Create an OpenGL vertex array.
	 */
	public ArrayObject() {
		id = glGenVertexArrays();
	}

	/**
	 * Bind the array to current procedure.
	 */
	public void bind() {
		glBindVertexArray(id);
	}

	/**
	 * Get the ID of the object.
	 * 
	 * @return ID of the object.
	 */
	public int getID() {
		return id;
	}

	/**
	 * Unbind any array from current procedure.
	 */
	public void unbind() {
		glBindVertexArray(0);
	}

	/**
	 * Delete the object.
	 */
	public void abrogate() {
		glDeleteVertexArrays(id);
	}

}
