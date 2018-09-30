package cn.stateofwar.sowr.input;

/**
 * Hooks for different type of inputs.
 */
public class InputHooks {

	/**
	 * A key has been triggered. This method receives the parameters from the
	 * listener automatically.
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

	}

	/**
	 * A mouse button has been triggered. This method receives the parameters from
	 * the listener automatically.
	 * 
	 * @param button The button triggered.
	 * 
	 * @param action The action performed by the button.
	 * 
	 * @param mods   Which modifiers keys were held down.
	 */
	public static void mouse(int button, int action, int mods) {

	}

	/**
	 * A cursor position listener.
	 * 
	 * @param x X position (in pixels) of the cursor.
	 * 
	 * @param y Y position (in pixels) of the cursor.
	 */
	public static void cursor(double x, double y) {

	}

}
