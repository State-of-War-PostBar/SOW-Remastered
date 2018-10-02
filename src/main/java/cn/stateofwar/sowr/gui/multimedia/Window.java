package cn.stateofwar.sowr.gui.multimedia;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL45.*;
import static org.lwjgl.system.MemoryUtil.NULL;

import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.opengl.GL;

import cn.stateofwar.sowr.input.InputHooks;
import cn.stateofwar.sowr.util.Logger;

/**
 * An instance of a GLFW window.
 */
public class Window {

	private static final Logger logger = new Logger("Render");

	/** Handle of the window. */
	private long handle;

	/** Title of the window. */
	private String title;

	/** Width in pixel of the window. */
	private int width;

	/** Height in pixel of the window. */
	private int height;

	/** If the window enabled vertical sync. */
	private boolean vSync;

	/** If the window takes all the screen. */
	private boolean fullScreen;

	/**
	 * Initialize window properties.
	 * 
	 * @param _title  Title of the program.
	 * 
	 * @param _width  With of the window.
	 * 
	 * @param _height Height of the window.
	 * 
	 * @param _vSync  Vertical sync for the window.
	 * 
	 * @param fs      Full screen capturing.
	 */
	public Window(String _title, int _width, int _height, boolean _vSync, boolean fs) {
		title = _title;
		width = _width;
		height = _height;
		vSync = _vSync;
		fullScreen = fs;
	}

	/**
	 * Create the window.
	 */
	public Window initWindow() {
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GL_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GL_FALSE);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);

		handle = glfwCreateWindow(width, height, title, fullScreen ? glfwGetPrimaryMonitor() : NULL, NULL);

		if (handle == NULL) {
			logger.fatal("Failed to create a GLFW window!");
			throw new RuntimeException("Failed to create a GLFW window.");
		}

		logger.info("Created a GLFW window with handle " + handle + ".");
		glfwShowWindow(handle);
		glfwMakeContextCurrent(handle);

		glfwSetKeyCallback(handle, GLFWKeyCallback.create((window, key, scancode, action, mods) -> {
			InputHooks.keyboard(key, scancode, action, mods);
		}));

		glfwSetMouseButtonCallback(handle, GLFWMouseButtonCallback.create((window, button, action, mods) -> {
			InputHooks.mouse(button, action, mods);
		}));

		glfwSetCursorPosCallback(handle, GLFWCursorPosCallback.create((window, x, y) -> {
			InputHooks.cursor(x, y);
		}));

		if (vSync)
			glfwSwapInterval(1);

		GL.createCapabilities(true);
		glViewport(0, 0, width, height);
		glClearColor(1.0f, 1.0f, 1.0f, 0.0f);

		return this;
	}

	/**
	 * A general clear of window's rendering.
	 */
	public void clear() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}

	/**
	 * Upload the rendering procedures to this window.
	 */
	public void update() {
		glfwSwapBuffers(handle);
		glfwPollEvents();
	}

	/**
	 * Get the handle of this window.
	 * 
	 * @return Handle of this window.
	 */
	public long getHandle() {
		return handle;
	}

	/**
	 * Get the width of this window.
	 * 
	 * @return Width of this window.
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Get the height of this window.
	 * 
	 * @return Height of this window.
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Get the closing status of this window.
	 * 
	 * @return Closing status.
	 */
	public boolean isClosing() {
		return glfwWindowShouldClose(handle);
	}

	/**
	 * Set the closing status of this window.
	 * 
	 * @param val Closing status.
	 */
	public void setClosing(boolean val) {
		glfwSetWindowShouldClose(handle, val);
	}

	/**
	 * Get the vertical sync status of this window.
	 * 
	 * @param Vertical sync status.
	 */
	public boolean isVSync() {
		return vSync;
	}

	/**
	 * Set the vertical sync status of this window.
	 * 
	 * @param vSync Vertical sync status.
	 */
	public void setVSync(boolean _vSync) {
		vSync = _vSync;
		if (_vSync)
			glfwSwapInterval(1);
		else
			glfwSwapInterval(0);
	}

	/**
	 * Delete and release resources of this window.
	 */
	public void abrogate() {
		glfwDestroyWindow(handle);
		Callbacks.glfwFreeCallbacks(handle);
	}

}
