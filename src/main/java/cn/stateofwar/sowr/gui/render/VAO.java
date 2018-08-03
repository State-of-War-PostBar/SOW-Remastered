package cn.stateofwar.sowr.gui.render;

import static org.lwjgl.opengl.GL30.*;

public class VAO {

	private final int id;

	public VAO() {
		id = glGenVertexArrays();
	}

	public void bind() {
		glBindVertexArray(id);
	}

	public int getID() {
		return id;
	}

	public void delete() {
		glDeleteVertexArrays(id);
	}

	public void unBind() {
		glBindVertexArray(0);
	}

}
