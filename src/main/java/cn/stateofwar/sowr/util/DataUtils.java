package cn.stateofwar.sowr.util;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.joml.Vector2f;
import org.lwjgl.BufferUtils;

import cn.stateofwar.sowr.core.Core;
import cn.stateofwar.sowr.graphic.render.Texture2D;

/**
 * Utilities for processing data.
 */
public class DataUtils {

	/**
	 * Create a byte buffer from a byte array.<br />
	 * 
	 * @param data Array of the data.
	 * 
	 * @return Buffer created from the array.
	 */
	public static ByteBuffer createByteBuffer(byte[] data) {
		return (ByteBuffer) BufferUtils.createByteBuffer(data.length).put(data);
	}

	/**
	 * Create a float buffer from a float array.<br />
	 * 
	 * @param data Array of the data.
	 * 
	 * @return Buffer created from the array.
	 */
	public static FloatBuffer createFloatBuffer(float[] data) {
		return (FloatBuffer) BufferUtils.createFloatBuffer(data.length).put(data);
	}

	/**
	 * Create a double buffer from a double array.<br />
	 * 
	 * @param data Array of the data.
	 * 
	 * @return Buffer created from the array.
	 */
	public static DoubleBuffer createDoubleBuffer(double[] data) {
		return (DoubleBuffer) BufferUtils.createDoubleBuffer(data.length).put(data);
	}

	/**
	 * Create an integer buffer from an integer array.<br />
	 * 
	 * @param data Array of the data.
	 * 
	 * @return Buffer created from the array.
	 */
	public static IntBuffer createIntBuffer(int[] data) {
		return (IntBuffer) BufferUtils.createIntBuffer(data.length).put(data);
	}

	/**
	 * Convert a X-Y coordinate point to its ratios toward the resolution of screen.
	 * 
	 * @param x X coordinate.
	 * 
	 * @param y Y coordinate.
	 * 
	 * @return Percentages toward the screen resolution.
	 */
	public static Vector2f toScreenPercentage(int x, int y) {
		float x_percentage = (float) x / (float) Core.state.getWindow().getWidth();
		float y_percentage = (float) y / (float) Core.state.getWindow().getHeight();

		return new Vector2f(x_percentage, y_percentage);
	}

	/**
	 * Convert a X-Y point of the screen to OpenGL coordinate.
	 * 
	 * @param x X coordinate.
	 * 
	 * @param y Y coordinate.
	 * 
	 * @return OpenGL coordinate based on the coordinate.
	 */
	public static Vector2f toGLCoord(int x, int y) {
		int w = Core.state.getWindow().getWidth();
		int h = Core.state.getWindow().getHeight();

		x = x > w ? w : x;
		y = y > h ? h : y;

		Vector2f vec = toScreenPercentage(x, y);
		return vec.set(vec.x * 2.0f - 1.0f, vec.y * 2.0f - 1.0f);
	}

	/**
	 * Convert a S-T coordinate point in pixels of a texture to OpenGL texture
	 * coordinate.
	 * 
	 * @param s       S coordinate.
	 * 
	 * @param t       T coordinate.
	 * 
	 * @param texture The texture, used for getting its width and height.
	 * 
	 * @return OpenGL texture coordinate based on this pixel point.
	 */
	public static Vector2f to2DTexCoord(int s, int t, Texture2D texture) {
		int w = texture.getWidth();
		int h = texture.getHeight();

		s = s > w ? w : s;
		t = t > h ? h : t;

		float s_percentage = (float) s / (float) w;
		float t_percentage = (float) t / (float) h;

		return new Vector2f(s_percentage, t_percentage);
	}

}
