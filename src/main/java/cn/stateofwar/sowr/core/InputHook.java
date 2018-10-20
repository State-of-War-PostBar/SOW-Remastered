package cn.stateofwar.sowr.core;

import static org.lwjgl.glfw.GLFW.*;

public class InputHook {

	public double cursor_x;

	public double cursor_y;

	public boolean[] keys = new boolean[365];

	public boolean[] mouse_buttons = new boolean[11];

	public double double_click_interval;

	public void updateCursorPos(double x, double y) {
		cursor_x = x < 0 ? 0 : x;
		cursor_y = y < 0 ? 0 : y;
	}

	public void updateKeys(int key, int action) {
		if (action == GLFW_PRESS)
			keys[key] = true;
		if (action == GLFW_RELEASE)
			keys[key] = false;
	}

	public void updateMouseButton(int button, int action) {
		if (action == GLFW_PRESS)
			mouse_buttons[button] = true;
		if (action == GLFW_RELEASE)
			mouse_buttons[button] = false;
	}

}
