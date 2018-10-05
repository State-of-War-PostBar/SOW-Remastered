package cn.stateofwar.sowr.gui.base;

import org.lwjgl.glfw.GLFW;

/**
 * A timer for DkUI only, diffs from {@code Game.timer}.
 */
public class DkTimer {

	protected static double lastTime;

	public static void updateTime() {
		lastTime = GLFW.glfwGetTime();
	}

}
