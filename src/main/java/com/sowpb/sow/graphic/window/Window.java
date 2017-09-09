package com.sowpb.sow.graphic.window;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import com.sowpb.sow.util.Logger;

/**
 * The window class. Be caution that windows are not resizable.
 */
public class Window {

	private static final Logger logger = new Logger("Render");

	private long handle;

	private int width, height;

	private boolean vSync;

	private boolean fullScreen;

	public String title;

	public Window(String title, int width, int height, boolean vSync) {
		this.title = title;
		this.width = width;
		this.height = height;
		this.vSync = vSync;
		this.fullScreen = true;
	}

	public Window(String title, int width, int height, boolean vSync, boolean fs) {
		this.title = title;
		this.width = width;
		this.height = height;
		this.vSync = vSync;
		this.fullScreen = fs;
	}

	public void initWindow() {
		glfwWindowHint(GLFW_VISIBLE, GL_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GL_FALSE);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);

		GLFWVidMode vid = glfwGetVideoMode(glfwGetPrimaryMonitor());

		if (fullScreen)
			handle = glfwCreateWindow(vid.width(), vid.height(), title, glfwGetPrimaryMonitor(), NULL);
		else
			handle = glfwCreateWindow(width, height, title, NULL, NULL);
		if (handle == NULL)
			throw new RuntimeException("Failed to create a GLFW window.");

		logger.info("Successfully created a window with handle " + Double.toString(handle) + ".");
		glfwShowWindow(handle);
		glfwMakeContextCurrent(handle);

		if (vSync)
			glfwSwapInterval(1);

		GL.createCapabilities();
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
	}

	public boolean shouldClose() {
		return glfwWindowShouldClose(handle);
	}

	public void makeShouldClose(boolean value) {
		glfwSetWindowShouldClose(handle, value);
	}

	public long getHandle() {
		return this.handle;
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}

	public boolean isvSync() {
		return this.vSync;
	}

	public void setvSync(boolean vSync) {
		this.vSync = vSync;
	}

	public void update() {
		glfwSwapBuffers(handle);
		glfwPollEvents();
	}

}
