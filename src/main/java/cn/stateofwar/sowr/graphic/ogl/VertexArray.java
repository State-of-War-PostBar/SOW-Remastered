package cn.stateofwar.sowr.graphic.ogl;

import static org.lwjgl.opengl.GL45.*;

/**
 * An OpenGL vertex array object.
 */
public class VertexArray {

	/** ID of the vertex array. */
	private final int id;

	/**
	 * Create a vertex array object.
	 */
	public VertexArray() {
		id = glCreateVertexArrays();
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
	 * Bind the vertex array to rendering procedure.
	 */
	public void bind() {
		glBindVertexArray(id);
	}

	/**
	 * Unbind vertex arrays from rendering procedures.
	 */
	public void unbind() {
		glBindVertexArray(0);
	}

	/**
	 * Clean up the vertex array.
	 */
	public void abrogate() {
		glDeleteVertexArrays(id);
	}

}
