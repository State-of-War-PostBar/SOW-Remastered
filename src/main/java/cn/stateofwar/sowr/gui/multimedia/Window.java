package cn.stateofwar.sowr.gui.multimedia;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

import org.lwjgl.glfw.Callbacks;
import org.lwjgl.opengl.GL;

import cn.stateofwar.sowr.util.Logger;

public class Window {

	private static final Logger logger = new Logger("Render");

	private long handle;

	private String title;

	private int width, height;

	private boolean vSync;

	private boolean fullScreen;

	public Window(String _title, int _width, int _height, boolean _vSync, boolean _fs) {
		title = _title;
		width = _width;
		height = _height;
		vSync = _vSync;
		fullScreen = _fs;
	}

	public Window initWindow() {
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GL_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GL_FALSE);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);

		handle = glfwCreateWindow(width, height, title, fullScreen ? glfwGetPrimaryMonitor() : NULL, NULL);

		if (handle == NULL)
			throw new RuntimeException("Failed to create a GLFW window.");

		logger.info("Created a GLFW window with handle " + handle + ".");
		glfwShowWindow(handle);
		glfwMakeContextCurrent(handle);

		if (vSync)
			glfwSwapInterval(1);

		GL.createCapabilities(true);
		glViewport(0, 0, width, height);
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

		return this;
	}

	public long getHandle() {
		return handle;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public boolean isVSync() {
		return vSync;
	}

	public void setVSync(boolean _vSync) {
		vSync = _vSync;
		if (_vSync)
			glfwSwapInterval(1);
		else
			glfwSwapInterval(0);
	}

	public boolean closing() {
		return glfwWindowShouldClose(handle);
	}

	public void setClosing(boolean val) {
		glfwSetWindowShouldClose(handle, val);
	}

	public void update() {
		glfwSwapBuffers(handle);
		glfwPollEvents();
	}

	public void clear() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}

	public void abrogate() {
		glfwDestroyWindow(handle);
		Callbacks.glfwFreeCallbacks(handle);
	}

}
