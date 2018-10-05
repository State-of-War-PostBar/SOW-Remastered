package cn.stateofwar.sowr.input;

import static org.lwjgl.glfw.GLFW.*;

import cn.stateofwar.sowr.gui.base.DkSInputs;
import cn.stateofwar.sowr.gui.render.Graphic;

/**
 * Hooks for different type of inputs.
 */
public class InputHooks {

	/**
	 * A cursor position listener.
	 * 
	 * @param x X position (in pixels) of the cursor.
	 * 
	 * @param y Y position (in pixels) of the cursor.
	 */
	public static void cursor(double x, double y) {
		DkSInputs.cursor_x = x < 0 ? 0 : x > Graphic.win.getWidth() ? Graphic.win.getWidth() : x;
		DkSInputs.cursor_y = y < 0 ? 0
				: y > Graphic.win.getHeight() ? Graphic.win.getHeight() : Graphic.win.getHeight() - y;
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
			DkSInputs.DK_keys[key] = true;
		if (action == GLFW_RELEASE)
			DkSInputs.DK_keys[key] = false;
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
			DkSInputs.DK_mouse[button] = true;
		if (action == GLFW_RELEASE)
			DkSInputs.DK_mouse[button] = false;
	}

}
