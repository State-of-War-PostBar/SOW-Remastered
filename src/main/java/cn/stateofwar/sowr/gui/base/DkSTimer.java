package cn.stateofwar.sowr.gui.base;

import org.lwjgl.glfw.GLFW;

/**
 * A timer for DkUI only, diffs from {@code Game.timer}.
 */
public class DkSTimer {

	/** Time recorded. */
	protected static double time;

	/**
	 * Update the timer.
	 */
	public static void update() {
		time = GLFW.glfwGetTime();
	}

}
