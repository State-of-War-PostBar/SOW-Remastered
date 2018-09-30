package cn.stateofwar.sowr.util;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

import cn.stateofwar.sowr.gui.render.Graphic;
import cn.stateofwar.sowr.gui.render.Texture;

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
	 * Create an OpenGL orthographic by specific positions.
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
	 */
	public static Matrix4f orthographic(float left, float right, float bottom, float top, float near, float far) {
		Matrix4f ortho = new Matrix4f();

		float x = -(right + left) / (right - left);
		float y = -(top + bottom) / (top - bottom);
		float z = -(far + near) / (far - near);

		ortho._m00(2.0f / (right - left));
		ortho._m11(2.0f / (top - bottom));
		ortho._m22(-2.0f / (far - near));
		ortho._m03(x);
		ortho._m13(y);
		ortho._m23(z);

		return ortho;
	}

	/**
	 * Convert a pair of x-y coordinate of the screen (starts at the bottom left
	 * corner, and x→, y↑) to OpenGL coordinate.
	 * 
	 * @param x The x coordinate.
	 * 
	 * @param y The y coordinate.
	 * 
	 * @return The OpenGL coordinate based on this pair of x-y coordinate. <i>The Z
	 *         value is always 0.</i>
	 */
	public static Vector3f toGlCoord(int x, int y) {
		x = Math.abs(x);
		y = Math.abs(y);
		x = x > Graphic.win.getWidth() ? Graphic.win.getWidth() : x;
		y = y > Graphic.win.getHeight() ? Graphic.win.getHeight() : y;

		Vector2f vec = toScrPerc(x, y);

		return new Vector3f(vec.x * 2.0f - 1.0f, vec.y * 2.0f - 1.0f, 0.0f);
	}

	/**
	 * Convert a pair of s-t coordinate of a texture (starts at the bottom left
	 * corner, and u→, v↑) to OpenGL texture coordinate.
	 * 
	 * @param s The s coordinate.
	 * 
	 * @param t The t coordinate.
	 * 
	 * @return A vector contains their percentages toward the texture size, which is
	 *         also the OpenGL texture coordinate.
	 */
	public static Vector2f toTexCoord(int s, int t, Texture texture) {
		s = Math.abs(s);
		t = Math.abs(t);
		int w = texture.getWidth();
		int h = texture.getHeight();

		s = s > w ? w : s;
		t = t > h ? h : t;

		float sPerc = (float) s / (float) w;
		float tPerc = (float) t / (float) h;

		return new Vector2f(sPerc, tPerc);
	}

	/**
	 * Convert a x-y coordinate to their ratios to the resolution of screen.
	 * 
	 * @param x The x coordinate.
	 * 
	 * @param y The y coordinate.
	 * 
	 * @return A vector contains their percentages toward the screen.
	 */
	public static Vector2f toScrPerc(int x, int y) {
		x = Math.abs(x);
		y = Math.abs(y);

		float xPerc = (float) x / (float) Graphic.win.getWidth();
		float yPerc = (float) y / (float) Graphic.win.getHeight();

		return new Vector2f(xPerc, yPerc);
	}

	/**
	 * Determine the quadrant of a x-y coordinate.
	 * 
	 * @param x The x coordinate.
	 * 
	 * @param y The y coordinate.
	 * 
	 * @return A number indicates the quadrant of the coordinate.<br />
	 *         0 -> At origin;<br />
	 *         1 -> 1st quadrant;<br />
	 *         2 -> 2nd quadrant;<br />
	 *         3 -> 3rd quadrant;<br />
	 *         4 -> 4th quadrant;<br />
	 *         5 -> Middle of 1st and 2nd quadrant;<br />
	 *         6 -> Middle of 2nd and 3rd quadrant;<br />
	 *         7 -> Middle of 3rd and 4th quadrant;<br />
	 *         8 -> Middle of 1st and 4th quadrant.<br />
	 */
	public static int getQuadrant(int x, int y) {
		y = Graphic.win.getHeight() - y;

		int xMid = Graphic.win.getWidth() / 2;
		int yMid = Graphic.win.getHeight() / 2;

		if (x == y && x == 0)
			return 3;
		else if (x == xMid && y == yMid)
			return 0;
		else if (x > xMid && y < yMid)
			return 1;
		else if (x < xMid && y < yMid)
			return 2;
		else if (x < xMid && y > yMid)
			return 3;
		else if (x > xMid && y > yMid)
			return 4;
		else if (x == xMid && y < yMid)
			return 5;
		else if (x < xMid && y == yMid)
			return 6;
		else if (x == xMid && y > yMid)
			return 7;
		else
			return 8;
	}

}
