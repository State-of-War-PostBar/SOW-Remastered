package com.sowpb.sow.graphic.process;

import com.sowpb.sow.References;
import com.sowpb.sow.graphic.window.Window;
import com.sowpb.sow.util.Config;
import com.sowpb.sow.util.Logger;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import static org.lwjgl.glfw.GLFW.*;

import static com.sowpb.sow.util.Utils.nl;

/**
 * Main graphic process.
 */
public class GraphMain {

	private static final Logger logger = new Logger("Render");

	private static Window mainWin;

	/**
	 * Setup the graphic window.
	 */
	public static void startupGraphic() {
		mainWin = new Window(References.PROG_NAME, Integer.parseInt(Config.get("GUI", "WinWidth")),
				Integer.parseInt(Config.get("GUI", "WinHeight")), Boolean.parseBoolean(Config.get("GUI", "VSync")),
				Boolean.parseBoolean(Config.get("GUI", "FullScreen")));
		mainWin.initWindow();
	}

	/**
	 * Start the main graphic loop.
	 */
	public static void startGLoop() {

		StringBuilder s = new StringBuilder();
		s.append("OpenGL information: ").append(nl());
		s.append("OpenGL Vendor: ").append(glGetString(GL_VENDOR)).append(nl());
		s.append("OpenGL Renderer: ").append(glGetString(GL_RENDERER)).append(nl());
		s.append("OpenGL Version: ").append(glGetString(GL_VERSION)).append(nl());
		s.append("GLSL Version: ").append(glGetString(GL_SHADING_LANGUAGE_VERSION)).append(nl());
		logger.info(s.toString());

		logger.info("Starting game rendering loop.");

		while (!glfwWindowShouldClose(mainWin.getHandle())) {
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

			/* Render things here. */

			mainWin.update();
		}

		glfwDestroyWindow(mainWin.getHandle());

		logger.info("Game rendering loop is broken.");

	}

}
