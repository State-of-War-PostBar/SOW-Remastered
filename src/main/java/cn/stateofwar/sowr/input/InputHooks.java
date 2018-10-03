package cn.stateofwar.sowr.input;

import static org.lwjgl.glfw.GLFW.*;

import cn.stateofwar.sowr.gui.DkInputs;
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
		DkInputs.cursPosX = x < 0 ? 0 : x > Graphic.win.getWidth() ? Graphic.win.getWidth() : x;
		DkInputs.cursPosY = y < 0 ? 0
				: y > Graphic.win.getHeight() ? Graphic.win.getHeight() : Graphic.win.getHeight() - y;
	}

	/**
	 * A key has been triggered.
	 * 
	 * @param key      The key triggered.
	 * 
	 * @param scancode The system-specific scancode of the key.
	 * 
	 * @param action   The action of the key triggered.
	 * 
	 * @param mods     Which modifiers keys were held down.
	 */
	public static void keyboard(int key, int scancode, int action, int mods) {
		if (action == GLFW_PRESS)
			DkInputs.DK_Keys[key] = true;
		if (action == GLFW_RELEASE)
			DkInputs.DK_Keys[key] = false;
	}

	/**
	 * A mouse button has been triggered.
	 * 
	 * @param button The button triggered.
	 * 
	 * @param action The action performed by the button.
	 * 
	 * @param mods   Which modifiers keys were held down.
	 */
	public static void mouse(int button, int action, int mods) {
		if (action == GLFW_PRESS)
			DkInputs.DK_Mouse[button] = true;
		if (action == GLFW_RELEASE)
			DkInputs.DK_Mouse[button] = false;
	}

}
