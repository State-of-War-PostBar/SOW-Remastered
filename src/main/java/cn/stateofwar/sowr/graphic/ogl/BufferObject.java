package cn.stateofwar.sowr.graphic.ogl;

import static org.lwjgl.opengl.GL45.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * An OpenGL buffer object.
 */
public class BufferObject {

	/** ID of the buffer. */
	private final int id;

	/** Target this buffer binds to. */
	private int target;

	/**
	 * Create a buffer object.
	 */
	public BufferObject() {
		id = glCreateBuffers();
	}

	/**
	 * Get the ID of the buffer.
	 * 
	 * @return ID of the buffer.
	 */
	public int getID() {
		return id;
	}

	/**
	 * Bind this buffer to a target.
	 * 
	 * @param _target Target to bind.
	 */
	public void bind(int _target) {
		target = _target;
		glBindBuffer(target, id);
	}

	/**
	 * Allocate some memory for the buffer. Use {@code bufferSub} later on to buffer
	 * data.
	 * 
	 * @param size  Size of memory to allocate.
	 * 
	 * @param usage Usage of the buffer.
	 */
	public void alloc(long size, int usage) {
		glBufferData(target, size, usage);
	}

	/**
	 * Upload data to the buffer.
	 * 
	 * @param data  Data to buffer.
	 * 
	 * @param usage Usage of the buffer.
	 */
	public void buffer(FloatBuffer data, int usage) {
		glBufferData(target, data, usage);
	}

	/**
	 * Upload data to the buffer.
	 * 
	 * @param data  Data to buffer.
	 * 
	 * @param usage Usage of the buffer.
	 */
	public void buffer(IntBuffer data, int usage) {
		glBufferData(target, data, usage);
	}

	/**
	 * Update data of the buffer. Usage is unchanged.
	 * 
	 * @param offset The starting point where data updates.
	 * 
	 * @param data   Data to buffer.
	 */
	public void bufferSub(long offset, FloatBuffer data) {
		glBufferSubData(target, offset, data);
	}

	/**
	 * Update data of the buffer. Usage is unchanged.
	 * 
	 * @param offset The starting point where data updates.
	 * 
	 * @param data   Data to buffer.
	 */
	public void bufferSub(long offset, IntBuffer data) {
		glBufferSubData(target, offset, data);
	}

	/**
	 * Unbind the buffer from rendering procedure.
	 */
	public void unbind() {
		glBindBuffer(target, 0);
	}

	/**
	 * Clean up the buffer object.
	 */
	public void abrogate() {
		glDeleteBuffers(id);
	}

}
