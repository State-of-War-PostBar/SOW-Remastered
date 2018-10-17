package cn.stateofwar.sowr.gui.render;

import static org.lwjgl.opengl.GL45.*;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4d;

import cn.stateofwar.sowr.util.DataUtils;

/**
 * Renderer for the program.
 */
public class Renderer {

	/** Vertex indices for a triangle. */
	public static final int[] INDICES_TRIANGLE = new int[] { 0, 1, 2 };
	/**
	 * Vertex indices for a rectangle; for 4 vertices, their listing is <b>Top
	 * Right</b> → <b>Bottom Right</b> → <b>Bottom Left</b> → <b>Top Left</b>.
	 */
	public static final int[] INDICES_RECTANGLE = new int[] { 0, 1, 3, 1, 2, 3 };

	/** OpenGL matrix mode for the program. */
	private static int matrix_mode;

	/**
	 * Set the OpenGL matrix mode to projection.
	 */
	public static void setProjMatrix() {
		if (matrix_mode == GL_PROJECTION)
			return;
		glMatrixMode(GL_PROJECTION);
		matrix_mode = GL_PROJECTION;
	}

	/**
	 * Set the OpenGL matrix mode to model.
	 */
	public static void setModelMatrix() {
		if (matrix_mode == GL_MODELVIEW)
			return;
		glMatrixMode(GL_MODELVIEW);
		matrix_mode = GL_MODELVIEW;
	}

	/**
	 * Draw a horizontal line on the screen with color.
	 * 
	 * @param x      X coordinate of the start point.
	 * 
	 * @param y      Y coordinate of the start point.
	 * 
	 * @param length Length of the line.
	 * 
	 * @param color  Color of the line.
	 */
	public void drawHorizontalLine(int x, int y, int length, RGBA color) {
		drawColoredRect(x, y, length - x, 1, color);
	}

	/**
	 * Draw a vertical line on the screen with color.
	 * 
	 * @param x      X coordinate of the start point.
	 * 
	 * @param y      Y coordinate of the start point.
	 * 
	 * @param length Length of the line.
	 * 
	 * @param color  Color of the line.
	 */
	public void drawVerticalLine(int x, int y, int length, RGBA color) {
		drawColoredRect(x, y, 1, length - y, color);
	}

	/**
	 * Draw a rectangle and render a color on it.
	 * 
	 * @param x     X coordinate of the bottom-left vertex of the rectangle.
	 * 
	 * @param y     Y coordinate of the bottom-left vertex of the rectangle.
	 * 
	 * @param w     Width of the rectangle, from left to right.
	 * 
	 * @param h     Height of the rectangle, from down to up.
	 * 
	 * @param color Color to render on the rectangle.
	 */
	public void drawColoredRect(int x, int y, int w, int h, RGBA color) {
		Vector3f coord1 = DataUtils.toGLCoord(x + w, y + h);
		Vector3f coord2 = DataUtils.toGLCoord(x + w, y);
		Vector3f coord3 = DataUtils.toGLCoord(x, y);
		Vector3f coord4 = DataUtils.toGLCoord(x, y + h);
		RawModel model = new RawModel(new float[] { coord1.x, coord1.y, coord1.z, coord2.x, coord2.y, coord2.z,
				coord3.x, coord3.y, coord3.z, coord4.x, coord4.y, coord4.z }, INDICES_RECTANGLE, color);
		model.draw();
		model.abrogate();
	}

	/**
	 * Draw a rectangle with a texture.
	 * 
	 * @param coord   Coordinates of the rectangle.
	 * 
	 * @param texture Texture to use.
	 */
	public void drawTexturedRect(Vector4d coord, Texture texture) {
		drawTexturedRect((int) coord.x, (int) coord.y, (int) coord.z, (int) coord.w, texture);
	}

	/**
	 * Draw a rectangle with a texture.
	 * 
	 * @param x       X coordinate of the bottom-left vertex of the rectangle.
	 * 
	 * @param y       Y coordinate of the bottom-left vertex of the rectangle.
	 * 
	 * @param w       Width of the rectangle, from left to right.
	 * 
	 * @param h       Height of the rectangle, from down to up.
	 * 
	 * @param texture Texture to use.
	 */
	public void drawTexturedRect(int x, int y, int w, int h, Texture texture) {
		drawTexturedRect(x, y, w, h, texture, 0, 0, texture.getWidth(), texture.getHeight());
	}

	/**
	 * Draw a rectangle with a portion of a texture.
	 * 
	 * @param x       X coordinate of the bottom-left vertex of the rectangle.
	 * 
	 * @param y       Y coordinate of the bottom-left vertex of the rectangle.
	 * 
	 * @param w       Width of the rectangle, from left to right.
	 * 
	 * @param h       Height of the rectangle, from down to up.
	 * 
	 * @param texture Texture to use.
	 * 
	 * @param s       Bottom left vertex of using texture area in pixels.
	 * 
	 * @param t       Bottom left vertex of using texture area in pixels.
	 * 
	 * @param u       Width of selecting area in pixels.
	 * 
	 * @param v       Height of selecting area in pixels.
	 */
	public void drawTexturedRect(int x, int y, int w, int h, Texture texture, int s, int t, int u, int v) {
		Vector3f coord1 = DataUtils.toGLCoord(x + w, y + h);
		Vector3f coord2 = DataUtils.toGLCoord(x + w, y);
		Vector3f coord3 = DataUtils.toGLCoord(x, y);
		Vector3f coord4 = DataUtils.toGLCoord(x, y + h);
		Vector2f coord5 = DataUtils.toTexCoord(s + u, t + v, texture);
		Vector2f coord6 = DataUtils.toTexCoord(s + u, t, texture);
		Vector2f coord7 = DataUtils.toTexCoord(s, t, texture);
		Vector2f coord8 = DataUtils.toTexCoord(s, t + v, texture);
		TexturedModel model = new TexturedModel(
				new float[] { coord1.x, coord1.y, coord1.z, coord2.x, coord2.y, coord2.z, coord3.x, coord3.y, coord3.z,
						coord4.x, coord4.y, coord4.z },
				INDICES_RECTANGLE, texture,
				new float[] { coord5.x, coord5.y, coord6.x, coord6.y, coord7.x, coord7.y, coord8.x, coord8.y });
		model.draw();
		model.abrogate();
	}

}
