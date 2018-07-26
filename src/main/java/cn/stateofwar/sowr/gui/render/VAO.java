package cn.stateofwar.sowr.gui.render;

import static org.lwjgl.opengl.GL30.*;

/**
 * A vertex array object.
 */
public class VAO {

	/**
	 * ID of the VAO.
	 */
	private final int id;

	public VAO() {
		id = glGenVertexArrays();
	}

	/**
	 * Bind this VAO.
	 */
	public void bind() {
		glBindVertexArray(id);
	}

	/**
	 * Delete this array.
	 */
	public void delete() {
		glDeleteVertexArrays(id);
	}

	public int getID() {
		return id;
	}

}
