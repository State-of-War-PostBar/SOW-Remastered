package cn.stateofwar.sowr.util;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.joml.Vector2d;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4d;
import org.lwjgl.BufferUtils;

import cn.stateofwar.sowr.gui.render.Graphic;
import cn.stateofwar.sowr.gui.render.Texture;

/**
 * Utilities for data calculation and conversion, mostly for OpenGL.
 */
public class DataUtils {

	/**
	 * Create a byte buffer from a byte array.<br />
	 * <i>The data buffer created is fliped.</i>
	 * 
	 * @param data Array of the data.
	 * 
	 * @return Buffer created from the array.
	 */
	public static ByteBuffer createByteBuffer(byte[] data) {
		return (ByteBuffer) BufferUtils.createByteBuffer(data.length).put(data).flip();
	}

	/**
	 * Create a float buffer from a float array.<br />
	 * <i>The data buffer created is fliped.</i>
	 * 
	 * @param data Array of the data.
	 * 
	 * @return Buffer created from the array.
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
	 * @return Buffer created from the array.
	 */
	public static DoubleBuffer createDoubleBuffer(double[] data) {
		return (DoubleBuffer) BufferUtils.createDoubleBuffer(data.length).put(data).flip();
	}

	/**
	 * Create an integer buffer from an integer array.<br />
	 * <i>The data buffer created is fliped.</i>
	 * 
	 * @param data Array of the data.
	 * 
	 * @return Buffer created from the array.
	 */
	public static IntBuffer createIntBuffer(int[] data) {
		return (IntBuffer) BufferUtils.createIntBuffer(data.length).put(data).flip();
	}

	/**
	 * Determine the quadrant of a x-y coordinate on the screen.
	 * 
	 * @param x X coordinate.
	 * 
	 * @param y Y coordinate.
	 * 
	 * @return A number indicates the quadrant of the coordinate:<br />
	 *         <b>0</b> → Origin;<br />
	 *         <b>1</b> → 1st quadrant;<br />
	 *         <b>2</b> → 2nd quadrant;<br />
	 *         <b>3</b> → 3rd quadrant;<br />
	 *         <b>4</b> → 4th quadrant;<br />
	 *         <b>5</b> → Middle of 1st and 2nd quadrant;<br />
	 *         <b>6</b> → Middle of 2nd and 3rd quadrant;<br />
	 *         <b>7</b> → Middle of 3rd and 4th quadrant;<br />
	 *         <b>8</b> → Middle of 1st and 4th quadrant.
	 */
	public static int getQuadrant(double x, double y) {
		y = Graphic.window.getHeight() - y;

		int x_mid = Graphic.window.getWidth() / 2;
		int y_mid = Graphic.window.getHeight() / 2;

		if (x == y && x == 0)
			return 3;
		else if (x == x_mid && y == y_mid)
			return 0;
		else if (x > x_mid && y < y_mid)
			return 1;
		else if (x < x_mid && y < y_mid)
			return 2;
		else if (x < x_mid && y > y_mid)
			return 3;
		else if (x > x_mid && y > y_mid)
			return 4;
		else if (x == x_mid && y < y_mid)
			return 5;
		else if (x < x_mid && y == y_mid)
			return 6;
		else if (x == x_mid && y > y_mid)
			return 7;
		else
			return 8;
	}

	/**
	 * Check if a point is in a rectangle.
	 * 
	 * @param point     Coordinate of the point.
	 * 
	 * @param rectangle Coordinates of the rectangle, x-y are the coordinate values
	 *                  of bottom left corner of the rectangle and z-w are its width
	 *                  and height.
	 * 
	 * @return If the point is in the range of the rectangle.
	 */
	public static boolean pir(Vector2d point, Vector4d rectangle) {
		if (point.x >= rectangle.x && point.x <= rectangle.x + rectangle.z && point.y >= rectangle.y
				&& point.y <= rectangle.y + rectangle.w)
			return true;
		return false;
	}

	/**
	 * Convert a x-y coordinate of the screen to OpenGL coordinate.
	 * 
	 * @param x X coordinate.
	 * 
	 * @param y Y coordinate.
	 * 
	 * @return OpenGL coordinate based on the coordinate. <i>The Z value is always
	 *         0.</i>
	 */
	public static Vector3f toGLCoord(int x, int y) {
		int w = Graphic.window.getWidth();
		int h = Graphic.window.getHeight();

		x = x > w ? w : x;
		y = y > h ? h : y;

		Vector2f vec = toScrPerc(x, y);

		return new Vector3f(vec.x * 2.0f - 1.0f, vec.y * 2.0f - 1.0f, 0.0f);
	}

	/**
	 * Convert a x-y coordinate of the screen to OpenGL coordinate with a desired Z
	 * value.
	 * 
	 * @param x X coordinate.
	 * 
	 * @param y Y coordinate.
	 * 
	 * @param z Desired Z value.
	 * 
	 * @return OpenGL coordinate based on the coordinate.
	 */
	public static Vector3f toGLCoord(int x, int y, int z) {
		Vector3f vec = toGLCoord(x, y);

		return new Vector3f(vec.x, vec.y, z);
	}

	/**
	 * Convert a x-y coordinate to their ratios toward the resolution of screen.
	 * 
	 * @param x X coordinate.
	 * 
	 * @param y Y coordinate.
	 * 
	 * @return Their percentages toward the screen resolution.
	 */
	public static Vector2f toScrPerc(int x, int y) {
		float x_percentage = (float) x / (float) Graphic.window.getWidth();
		float y_percentage = (float) y / (float) Graphic.window.getHeight();

		return new Vector2f(x_percentage, y_percentage);
	}

	/**
	 * Convert a pair of s-t coordinate of a texture to OpenGL texture coordinate.
	 * 
	 * @param s       S coordinate.
	 * 
	 * @param t       T coordinate.
	 * 
	 * @param texture Texture, used for getting its width and height.
	 * 
	 * @return OpenGL texture coordinate based on this pair of s-t coordinate.
	 */
	public static Vector2f toTexCoord(int s, int t, Texture texture) {
		int w = texture.getWidth();
		int h = texture.getHeight();

		s = s > w ? w : s;
		t = t > h ? h : t;

		float s_percentage = (float) s / (float) w;
		float t_percentage = (float) t / (float) h;

		return new Vector2f(s_percentage, t_percentage);
	}

}
