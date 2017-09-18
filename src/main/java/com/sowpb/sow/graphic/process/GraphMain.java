package com.sowpb.sow.graphic.process;

import com.sowpb.sow.References;
import com.sowpb.sow.graphic.window.Window;
import com.sowpb.sow.util.Config;
import com.sowpb.sow.util.Logger;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.glfw.GLFW.*;

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

		logger.info("Starting game rendering loop.");

		while (!glfwWindowShouldClose(mainWin.getHandle())) {
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

			/* Render things here. */

			mainWin.update();
		}

		logger.info("Game rendering loop is broken.");

	}

}
