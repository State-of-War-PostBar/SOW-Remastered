package cn.stateofwar.sowr.gui.render.ogl;

import static org.lwjgl.opengl.GL45.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * An OpenGL buffer object.
 */
public class BufferObject {

	/** ID of the object. */
	private final int id;

	public BufferObject() {
		id = glGenBuffers();
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
	 * Bind the object to an OpenGL instance.
	 * 
	 * @param target Target to bind.
	 */
	public void bind(int target) {
		glBindBuffer(target, id);
	}

	/**
	 * Upload vertex data to the buffer with specified target, data and usage.
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
	 * Upload element data to the buffer with specified target, data and usage.
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
	 * Upload null data to the buffer with specified target, size and usage.
	 * 
	 * @param target Target to upload.
	 * 
	 * @param size   Size in bytes of the buffer data store.
	 * 
	 * @param usage  Usage of the data.
	 */
	public void buffer(int target, long size, int usage) {
		glBufferData(target, size, usage);
	}

	/**
	 * Upload sub data to the buffer with specified target, offset and data.
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
	 * Upload sub data to the buffer with specified target, offset and data.
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
	 * Unbind any buffer objects.
	 */
	public void unbind(int target) {
		glBindBuffer(target, 0);
	}

	/**
	 * Delete the buffer.
	 */
	public void abrogate() {
		glDeleteBuffers(id);
	}

}
