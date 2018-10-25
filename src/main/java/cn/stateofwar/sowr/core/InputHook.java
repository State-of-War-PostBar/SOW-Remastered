package cn.stateofwar.sowr.core;

import static org.lwjgl.glfw.GLFW.*;

public class InputHook {

	public float cursor_x;

	public float cursor_y;

	public boolean[] keys = new boolean[365];

	public boolean[] mouse_buttons = new boolean[11];

	public double double_click_interval;

	public void updateCursorPos(double x, double y) {
		cursor_x = (float) x;
		cursor_y = (float) (Core.state.getWindow().getHeight() - y);
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
