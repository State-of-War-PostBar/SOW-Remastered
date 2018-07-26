package cn.stateofwar.sowr.gui.multimedia;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

import org.lwjgl.opengl.GL;

import cn.stateofwar.sowr.util.Logger;
import cn.stateofwar.sowr.util.Utils;
import cn.stateofwar.sowr.util.Utils.OSType;

/**
 * A regular GLFW window that is not resizable.
 */
public class Window {

	private static final Logger logger = new Logger("Render");

	/**
	 * Handle of the window.
	 */
	private long handle;

	private String title;

	private int width, height;

	/**
	 * If this window enables vertical sync.
	 */
	private boolean vSync;

	/**
	 * If this window takes all of the screen.
	 */
	private boolean fullScreen;

	public Window(String _title, int _width, int _height, boolean _vSync, boolean _fs) {
		title = _title;
		width = _width;
		height = _height;
		vSync = _vSync;
		fullScreen = _fs;
	}

	/**
	 * Initialize this window.
	 */
	public Window initWindow() {
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GL_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GL_FALSE);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		/* GLFW_OPENGL_FORWARD_COMPAT does not seem very necessary except for MacOS. */
		if (Utils.getOS() == OSType.MacOS)
			glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);

		handle = glfwCreateWindow(width, height, title, fullScreen ? glfwGetPrimaryMonitor() : NULL, NULL);

		if (handle == NULL)
			throw new RuntimeException("Failed to create a GLFW window.");

		logger.info("Successfully created a window with handle " + handle + ".");
		glfwShowWindow(handle);
		glfwMakeContextCurrent(handle);

		if (vSync)
			glfwSwapInterval(1);

		GL.createCapabilities();
		glViewport(0, 0, width, height);
		glClearColor(0.5f, 0.5f, 0.5f, 0.0f);

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

	public String getTitle() {
		return title;
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

	public void update() {
		glfwSwapBuffers(handle);
		glfwPollEvents();
	}

}
