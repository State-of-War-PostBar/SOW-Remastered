package cn.stateofwar.sowr.graphic.multimedia;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL45.*;
import static org.lwjgl.system.MemoryUtil.NULL;

import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.opengl.GL;

import cn.stateofwar.sowr.core.Core;
import cn.stateofwar.sowr.util.Logger;
import cn.stateofwar.sowr.util.Utils;
import cn.stateofwar.sowr.util.Utils.OSType;

public class Window {

	private static final Logger LOGGER = new Logger("Render");

	private final long handle;

	private String title;

	private int width;

	private int height;

	private boolean vertical_sync;

	private boolean full_screen;

	public Window(String _title, int _width, int _height, boolean _vertical_sync, boolean _full_screen) {
		title = _title;
		width = _width;
		height = _height;
		vertical_sync = _vertical_sync;
		full_screen = _full_screen;

		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GL_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 5);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

		if (Utils.getOS() == OSType.MAC)
			glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);

		handle = glfwCreateWindow(width, height, title, full_screen ? glfwGetPrimaryMonitor() : NULL, NULL);

		if (handle == NULL) {
			LOGGER.fatal("Failed to create a GLFW window!");
			throw new RuntimeException("Failed to create a GLFW window!");
		}

		LOGGER.info("Created a GLFW window.");

		glfwSetCursorPosCallback(handle, GLFWCursorPosCallback.create((window, x, y) -> {
			Core.state.getInputHook().updateCursorPos(x, y);
		}));

		glfwSetKeyCallback(handle, GLFWKeyCallback.create((window, key, scancode, action, mods) -> {
			Core.state.getInputHook().updateKeys(key, action);
		}));

		glfwSetMouseButtonCallback(handle, GLFWMouseButtonCallback.create((window, button, action, mods) -> {
			Core.state.getInputHook().updateMouseButton(button, action);
		}));

		if (vertical_sync)
			glfwSwapInterval(1);

		glfwShowWindow(handle);
		glfwMakeContextCurrent(handle);

		GL.createCapabilities();
		glViewport(0, 0, width, height);
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		glDepthFunc(GL_LEQUAL);
		glClearDepth(1.0f);
	}

	public void clear() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}

	public void update() {
		glfwSwapBuffers(handle);
		glfwPollEvents();
	}

	public long getHandle() {
		return handle;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int _width) {
		width = _width;
		resize(width, height);
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int _height) {
		height = _height;
		resize(width, height);
	}

	public void resize(int _width, int _height) {
		width = _width;
		height = _height;
		glViewport(0, 0, width, height);
	}

	public boolean isClosing() {
		return glfwWindowShouldClose(handle);
	}

	public void close() {
		glfwSetWindowShouldClose(handle, true);
	}

	public boolean isVerticalSync() {
		return vertical_sync;
	}

	public void setVerticalSync(boolean _vertical_sync) {
		vertical_sync = _vertical_sync;
		if (_vertical_sync)
			glfwSwapInterval(1);
		else
			glfwSwapInterval(0);
	}

	public void abrogate() {
		Callbacks.glfwFreeCallbacks(handle);
		glfwDestroyWindow(handle);
	}

}
