package cn.stateofwar.sowr.core;

import static org.lwjgl.glfw.GLFW.*;

import cn.stateofwar.sowr.gui.render.Graphic;

/**
 * Hooks for inputs.
 */
public class Inputs {

	/** X position of the cursor. */
	public static double cursor_x;

	/** Y position of the cursor. */
	public static double cursor_y;

	/** Key inputs from keyboard; implements them from GLFW directly. */
	public static boolean[] keys = new boolean[350];

	/** Mouse button inputs from mouse; implements them from GLFW directly. */
	public static boolean[] mouse = new boolean[11];

	/**
	 * A cursor position listener.
	 * 
	 * @param x X position (in pixels) of the cursor.
	 * 
	 * @param y Y position (in pixels) of the cursor.
	 */
	public static void cursor(double x, double y) {
		cursor_x = x < 0 ? 0 : x > Graphic.win.getWidth() ? Graphic.win.getWidth() : x;
		cursor_y = y < 0 ? 0 : y > Graphic.win.getHeight() ? Graphic.win.getHeight() : Graphic.win.getHeight() - y;
	}

	/**
	 * A key has been triggered.
	 * 
	 * @param key      Key triggered.
	 * 
	 * @param scancode System-specific scancode of the key.
	 * 
	 * @param action   Action of the key triggered.
	 * 
	 * @param mods     Which modifiers keys were held down.
	 */
	public static void keyboard(int key, int scancode, int action, int mods) {
		if (action == GLFW_PRESS)
			keys[key] = true;
		if (action == GLFW_RELEASE)
			keys[key] = false;
	}

	/**
	 * A mouse button has been triggered.
	 * 
	 * @param button Button triggered.
	 * 
	 * @param action Action performed by the button.
	 * 
	 * @param mods   Which modifiers keys were held down.
	 */
	public static void mouse(int button, int action, int mods) {
		if (action == GLFW_PRESS)
			mouse[button] = true;
		if (action == GLFW_RELEASE)
			mouse[button] = false;
	}

}
