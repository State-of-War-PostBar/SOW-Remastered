package cn.stateofwar.sowr.gui.render;

import org.joml.Vector2f;
import org.joml.Vector3f;

import cn.stateofwar.sowr.util.DataUtils;

/**
 * Renderer for the program.
 */
public class Renderer {

	/** Vertex indices for a triangle. */
	public static final int[] indices_trig = new int[] { 0, 1, 2 };

	/**
	 * Vertex indices for a rectangle, for 4 vertices, their listing is TR -> BR ->
	 * BL -> TL.
	 */
	public static final int[] indices_rect = new int[] { 0, 1, 3, 1, 2, 3 };

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
		Vector3f coord1 = DataUtils.toGlCoord(x + w, y + h);
		Vector3f coord2 = DataUtils.toGlCoord(x + w, y);
		Vector3f coord3 = DataUtils.toGlCoord(x, y);
		Vector3f coord4 = DataUtils.toGlCoord(x, y + h);
		RawModel model = new RawModel(new float[] { coord1.x, coord1.y, coord1.z, coord2.x, coord2.y, coord2.z,
				coord3.x, coord3.y, coord3.z, coord4.x, coord4.y, coord4.z }, indices_rect, color);
		model.draw();
		model.abrogate();
	}

	/**
	 * Draw a rectangle with texture.
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
		Vector3f coord1 = DataUtils.toGlCoord(x + w, y + h);
		Vector3f coord2 = DataUtils.toGlCoord(x + w, y);
		Vector3f coord3 = DataUtils.toGlCoord(x, y);
		Vector3f coord4 = DataUtils.toGlCoord(x, y + h);
		Vector2f coord5 = DataUtils.toTexCoord(s + u, t + v, texture);
		Vector2f coord6 = DataUtils.toTexCoord(s + u, t, texture);
		Vector2f coord7 = DataUtils.toTexCoord(s, t, texture);
		Vector2f coord8 = DataUtils.toTexCoord(s, t + v, texture);
		TexturedModel mod = new TexturedModel(
				new float[] { coord1.x, coord1.y, coord1.z, coord2.x, coord2.y, coord2.z, coord3.x, coord3.y, coord3.z,
						coord4.x, coord4.y, coord4.z },
				indices_rect, texture,
				new float[] { coord5.x, coord5.y, coord6.x, coord6.y, coord7.x, coord7.y, coord8.x, coord8.y });
		mod.draw();
		mod.abrogate();
	}

}
