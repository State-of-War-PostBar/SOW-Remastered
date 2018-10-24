package cn.stateofwar.sowr.graphic.ogl;

import static org.lwjgl.opengl.GL45.*;

public class VertexArray {

	private final int id;

	public VertexArray() {
		id = glCreateVertexArrays();
	}

	public int getID() {
		return id;
	}

	public void bind() {
		glBindVertexArray(id);
	}

	public void unbind() {
		glBindVertexArray(0);
	}

	public void abrogate() {
		glDeleteVertexArrays(id);
	}

}
