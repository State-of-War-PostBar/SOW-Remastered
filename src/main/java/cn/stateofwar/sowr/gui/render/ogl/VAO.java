package cn.stateofwar.sowr.gui.render.ogl;

import static org.lwjgl.opengl.GL45.*;

/**
 * A vertex array object.
 */
public class VAO {

	/** ID of this object. */
	private final int id;

	public VAO() {
		id = glGenVertexArrays();
	}

	/**
	 * Bind this array to current procedure.
	 */
	public void bind() {
		glBindVertexArray(id);
	}

	/**
	 * Get the ID of this object.
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
	 * Delete this object.
	 */
	public void delete() {
		glDeleteVertexArrays(id);
	}

}
