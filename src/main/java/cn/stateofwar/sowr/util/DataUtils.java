package cn.stateofwar.sowr.util;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

/**
 * Utilities of data managements, mostly for OpenGL.
 */
public class DataUtils {

	/**
	 * Create a byte buffer of the data. <i>The result buffer is not flipped!</i>
	 * 
	 * @param data The array for the buffer.
	 */
	public static ByteBuffer createByteBuffer(byte[] data) {
		return BufferUtils.createByteBuffer(data.length).put(data);
	}

	/**
	 * Create a integer buffer of the data. <i>The result buffer is not flipped!</i>
	 * 
	 * @param data The array for the buffer.
	 */
	public static IntBuffer createIntBuffer(int[] data) {
		return BufferUtils.createIntBuffer(data.length).put(data);
	}

	/**
	 * Create a float buffer of the data. <i>The result buffer is not flipped!</i>
	 * 
	 * @param data The array for the buffer.
	 */
	public static FloatBuffer createFloatBuffer(float[] data) {
		return BufferUtils.createFloatBuffer(data.length).put(data);
	}

	/**
	 * Create a double buffer of the data. <i>The result buffer is not flipped!</i>
	 * 
	 * @param data The array for the buffer.
	 */
	public static DoubleBuffer createDoubleBuffer(double[] data) {
		return BufferUtils.createDoubleBuffer(data.length).put(data);
	}

	/**
	 * Convert a projection data to orthographic and creates a orthographic
	 * projection matrix.
	 * 
	 * @param left   Coordinate for the left vertical clipping pane.
	 * 
	 * @param right  Coordinate for the right vertical clipping pane.
	 * 
	 * @param bottom Coordinate for the bottom horizontal clipping pane.
	 * 
	 * @param top    Coordinate for the bottom horizontal clipping pane.
	 * 
	 * @param near   Coordinate for the near depth clipping pane.
	 * 
	 * @param far    Coordinate for the far depth clipping pane.
	 * 
	 * @see glOrtho
	 */
	public static Matrix4f orthographic(float left, float right, float bottom, float top, float near, float far) {
		Matrix4f ortho = new Matrix4f();

		float tx = -(right + left) / (right - left);
		float ty = -(top + bottom) / (top - bottom);
		float tz = -(far + near) / (far - near);

		ortho._m00(2f / (right - left));
		ortho._m11(2f / (top - bottom));
		ortho._m22(-2f / (far - near));
		ortho._m03(tx);
		ortho._m13(ty);
		ortho._m23(tz);

		return ortho;
	}

}
