package com.sowpb.sow.graphic.process;

import com.sowpb.sow.References;
import com.sowpb.sow.graphic.window.Window;
import com.sowpb.sow.util.Logger;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.glfw.GLFW.*;

/**
 * Main graphic process.
 */
public class GraphMain {
	
	private static Logger logger = new Logger("Render");

	// Will add configuration-customizing after.
	private static Window mainWin = new Window(References.PROG_NAME, 1, 1, false, true);

	/**
	 * Setup the graphic window.
	 */
	public static void startupGraphic() {
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
