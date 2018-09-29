package cn.stateofwar.sowr.util;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

/**
 * Utilities for data calculation and conversion. Mostly for OpenGL.
 */
public class DataUtils {

	/**
	 * Create a byte buffer from a byte array.<br />
	 * <i>The data buffer created is fliped.</i>
	 * 
	 * @param data Array of the data.
	 * 
	 * @return The buffer created from the array.
	 */
	public static ByteBuffer createByteBuffer(byte[] data) {
		return (ByteBuffer) BufferUtils.createByteBuffer(data.length).put(data).flip();
	}

	/**
	 * Create a integer buffer from an integer array.<br />
	 * <i>The data buffer created is fliped.</i>
	 * 
	 * @param data Array of the data.
	 * 
	 * @return The buffer created from the array.
	 */
	public static IntBuffer createIntBuffer(int[] data) {
		return (IntBuffer) BufferUtils.createIntBuffer(data.length).put(data).flip();
	}

	/**
	 * Create a float buffer from a float array.<br />
	 * <i>The data buffer created is fliped.</i>
	 * 
	 * @param data Array of the data.
	 * 
	 * @return The buffer created from the array.
	 */
	public static FloatBuffer createFloatBuffer(float[] data) {
		return (FloatBuffer) BufferUtils.createFloatBuffer(data.length).put(data).flip();
	}

	/**
	 * Create a double buffer from a double array.<br />
	 * <i>The data buffer created is fliped.</i>
	 * 
	 * @param data Array of the data.
	 * 
	 * @return The buffer created from the array.
	 */
	public static DoubleBuffer createDoubleBuffer(double[] data) {
		return (DoubleBuffer) BufferUtils.createDoubleBuffer(data.length).put(data).flip();
	}

	/**
	 * Create a OpenGL orthographic by specific positions.
	 * 
	 * @param left   Farthest left of the scene box.
	 * 
	 * @param right  Farthest right of the scene box.
	 * 
	 * @param bottom Farthest bottom of the scene box.
	 * 
	 * @param top    Farthest top of the scene box.
	 * 
	 * @param near   Farthest front of the scene box.
	 * 
	 * @param far    Farthest back of the scene box.
	 * 
	 * @return The matrix of the orthographic data of the canonical view box.
	 * 
	 * @see glOrtho
	 */
	public static Matrix4f orthographic(float left, float right, float bottom, float top, float near, float far) {
		Matrix4f ortho = new Matrix4f();

		float tx = -(right + left) / (right - left);
		float ty = -(top + bottom) / (top - bottom);
		float tz = -(far + near) / (far - near);

		ortho._m00(2.0f / (right - left));
		ortho._m11(2.0f / (top - bottom));
		ortho._m22(-2.0f / (far - near));
		ortho._m03(tx);
		ortho._m13(ty);
		ortho._m23(tz);

		return ortho;
	}

}
