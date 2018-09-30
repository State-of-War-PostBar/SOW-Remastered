package cn.stateofwar.sowr.gui.render.ogl;

import static org.lwjgl.opengl.GL45.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * An OpenGL buffer object.
 */
public class BufferObj {

	/** ID of this object. */
	private final int id;

	public BufferObj() {
		id = glGenBuffers();
	}

	/**
	 * Bind the object to an OpenGL instance.
	 * 
	 * @param target The target to bind.
	 */
	public void bind(int target) {
		glBindBuffer(target, id);
	}

	/**
	 * Upload null data to this VBO with specified target, size and usage.
	 * 
	 * @param target Target to upload.
	 * 
	 * @param size   Size in bytes of the VBO data store.
	 * 
	 * @param usage  Usage of the data.
	 */
	public void buffer(int target, long size, int usage) {
		glBufferData(target, size, usage);
	}

	/**
	 * Upload vertex data to this VBO with specified target, data and usage.
	 * 
	 * @param target Target to upload.
	 * 
	 * @param data   Buffer with the data to upload.
	 * 
	 * @param usage  Usage of the data.
	 */
	public void buffer(int target, FloatBuffer data, int usage) {
		glBufferData(target, data, usage);
	}

	/**
	 * Upload element data to this EBO with specified target, data and usage.
	 * 
	 * @param target Target to upload.
	 * 
	 * @param data   Buffer with the data to upload.
	 * 
	 * @param usage  Usage of the data.
	 */
	public void buffer(int target, IntBuffer data, int usage) {
		glBufferData(target, data, usage);
	}

	/**
	 * Upload sub data to this VBO with specified target, offset and data.
	 * 
	 * @param target Target to upload.
	 * 
	 * @param offset Offset where the data should go in bytes.
	 * 
	 * @param data   Buffer with the data to upload.
	 */
	public void bufferSub(int target, long offset, FloatBuffer data) {
		glBufferSubData(target, offset, data);
	}

	/**
	 * Upload sub data to this VBO with specified target, offset and data.
	 * 
	 * @param target Target to upload.
	 * 
	 * @param offset Offset where the data should go in bytes.
	 * 
	 * @param data   Buffer with the data to upload.
	 */
	public void bufferSub(int target, long offset, IntBuffer data) {
		glBufferSubData(target, offset, data);
	}

	/**
	 * Get the ID of this buffer.
	 * 
	 * @return ID of this buffer.
	 */
	public int getID() {
		return id;
	}

	/**
	 * Unbind VBO.
	 */
	public void unbind(int target) {
		glBindBuffer(target, 0);
	}

	/**
	 * Delete this buffer.
	 */
	public void delete() {
		glDeleteBuffers(id);
	}

}
