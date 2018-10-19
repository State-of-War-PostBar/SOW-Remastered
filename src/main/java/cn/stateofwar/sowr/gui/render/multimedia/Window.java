package cn.stateofwar.sowr.gui.render.multimedia;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL46.*;
import static org.lwjgl.system.MemoryUtil.NULL;

import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.opengl.GL;

import cn.stateofwar.sowr.core.Inputs;
import cn.stateofwar.sowr.util.Logger;
import cn.stateofwar.sowr.util.Utils;
import cn.stateofwar.sowr.util.Utils.OSType;

/**
 * An instance of a window.
 */
public class Window {

	private static final Logger LOGGER = new Logger("Render");

	/** Handle of the window. */
	private long handle;

	/** Title of the window. */
	private String title;

	/** Width in pixel of the window. */
	private int width;

	/** Height in pixel of the window. */
	private int height;

	/** If the window enabled vertical sync. */
	private boolean vertical_sync;

	/** If the window takes all the screen. */
	private boolean full_screen;

	/**
	 * Initialize window properties.
	 * 
	 * @param _title         Title of the program.
	 * 
	 * @param _width         With of the window.
	 * 
	 * @param _height        Height of the window.
	 * 
	 * @param _vertical_sync Vertical sync for the window.
	 * 
	 * @param _full_screen   Full screen capturing.
	 */
	public Window(String _title, int _width, int _height, boolean _vertical_sync, boolean _full_screen) {
		title = _title;
		width = _width;
		height = _height;
		vertical_sync = _vertical_sync;
		full_screen = _full_screen;
	}

	/**
	 * Create the window.
	 */
	public Window initWindow() {
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GL_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GL_FALSE);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 6);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		glfwWindowHint(GLFW_OPENGL_DEBUG_CONTEXT, GLFW_TRUE);

		if (Utils.getOS() == OSType.MAC_OS)
			glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);

		handle = glfwCreateWindow(width, height, title, full_screen ? glfwGetPrimaryMonitor() : NULL, NULL);

		if (handle == NULL) {
			LOGGER.fatal("Failed to create a GLFW window!");
			throw new RuntimeException("Failed to create a GLFW window.");
		}

		LOGGER.info("Created a GLFW window with handle " + handle + ".");
		glfwShowWindow(handle);
		glfwMakeContextCurrent(handle);

		glfwSetKeyCallback(handle, GLFWKeyCallback.create((window, key, scancode, action, mods) -> {
			Inputs.keyboard(key, scancode, action, mods);
		}));

		glfwSetMouseButtonCallback(handle, GLFWMouseButtonCallback.create((window, button, action, mods) -> {
			Inputs.mouse(button, action, mods);
		}));

		glfwSetCursorPosCallback(handle, GLFWCursorPosCallback.create((window, x, y) -> {
			Inputs.cursor(x, y);
		}));

		if (vertical_sync)
			glfwSwapInterval(1);

		GL.createCapabilities();
		glViewport(0, 0, width, height);

		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		glClearDepth(1.0f);
		glEnable(GL_CULL_FACE);
		glCullFace(GL_BACK);

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
	 * Get the handle of the window.
	 * 
	 * @return Handle of the window.
	 */
	public long getHandle() {
		return handle;
	}

	/**
	 * Get the width of the window.
	 * 
	 * @return Width of the window.
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Get the height of the window.
	 * 
	 * @return Height of the window.
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Get the closing status of the window.
	 * 
	 * @return Closing status.
	 */
	public boolean isClosing() {
		return glfwWindowShouldClose(handle);
	}

	/**
	 * Set the closing status of the window.
	 * 
	 * @param value Closing status.
	 */
	public void setClosing(boolean value) {
		glfwSetWindowShouldClose(handle, value);
	}

	/**
	 * Get the vertical sync status of the window.
	 * 
	 * @return Vertical sync status.
	 */
	public boolean isVSync() {
		return vertical_sync;
	}

	/**
	 * Set the vertical sync status of the window.
	 * 
	 * @param _vertical_sync Vertical sync status.
	 */
	public void setVSync(boolean _vertical_sync) {
		vertical_sync = _vertical_sync;
		if (_vertical_sync)
			glfwSwapInterval(1);
		else
			glfwSwapInterval(0);
	}

	/**
	 * Delete and release resources of the window.
	 */
	public void abrogate() {
		Callbacks.glfwFreeCallbacks(handle);
		glfwDestroyWindow(handle);
	}

}
