package cn.stateofwar.sowr.gui.render;

import static org.lwjgl.opengl.GL15.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class VBO {

	private final int id;

	public VBO() {
		id = glGenBuffers();
	}

	public void bind(int target) {
		glBindBuffer(target, id);
	}

	public void upload(int target, long size, int usage) {
		glBufferData(target, size, usage);
	}

	public void upload(int target, FloatBuffer data, int usage) {
		glBufferData(target, data, usage);
	}

	public void upload(int target, IntBuffer data, int usage) {
		glBufferData(target, data, usage);
	}

	public void uploadSub(int target, long offset, FloatBuffer data) {
		glBufferSubData(target, offset, data);
	}

	public int getID() {
		return id;
	}

	public void delete() {
		glDeleteBuffers(id);
	}

}
