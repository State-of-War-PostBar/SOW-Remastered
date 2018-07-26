package cn.stateofwar.sowr.main;

import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwTerminate;

import org.lwjgl.system.Library;

import cn.stateofwar.sowr.util.Config;
import cn.stateofwar.sowr.util.Logger;

/**
 * Preparations and terminations of the program.
 */
public class Trim {

	private static final Logger logger = new Logger("Main");

	protected static void init() {
		Library.initialize();
		if (!glfwInit())
			throw new IllegalStateException("Failed to initialize GLFW.");
		logger.info("Successfully initialized the Lwjgl library.");

		Config.init();
		logger.info("Initialized the configuration system.");
	}

	protected static void abrogate() {
		glfwTerminate();
		logger.info("Successfully unloaded the Lwjgl library.");

		Config.abrogate();
		logger.info("Configurations saved.");
	}

}
