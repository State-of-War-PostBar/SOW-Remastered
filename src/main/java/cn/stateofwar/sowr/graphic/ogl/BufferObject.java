package cn.stateofwar.sowr.graphic.ogl;

import static org.lwjgl.opengl.GL45.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class BufferObject {

	private final int id;

	private int target;

	public BufferObject() {
		id = glCreateBuffers();
	}

	public int getID() {
		return id;
	}

	public void bind(int _target) {
		target = _target;
		glBindBuffer(id, target);
	}

	public void malloc(long size, int usage) {
		glBufferData(target, size, usage);
	}

	public void buffer(FloatBuffer data, int usage) {
		glBufferData(target, data, usage);
	}

	public void buffer(IntBuffer data, int usage) {
		glBufferData(target, data, usage);
	}

	public void bufferSub(long offset, FloatBuffer data) {
		glBufferSubData(target, offset, data);
	}

	public void bufferSub(long offset, IntBuffer data) {
		glBufferSubData(target, offset, data);
	}

	public void unbind() {
		glBindBuffer(target, 0);
	}

	public void abrogate() {
		glDeleteBuffers(id);
	}

}
