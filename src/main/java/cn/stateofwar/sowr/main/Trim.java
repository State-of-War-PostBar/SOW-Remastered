package cn.stateofwar.sowr.main;

import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwTerminate;

import org.lwjgl.system.Library;

import cn.stateofwar.sowr.util.Config;
import cn.stateofwar.sowr.util.Logger;

public class Trim {

	private static final Logger logger = new Logger("Main");

	protected static void init() {
		Library.initialize();
		if (!glfwInit()) {
			throw new IllegalStateException("Failed to initialize GLFW.");
		}
		logger.info("Initialized the Lwjgl library.");

		Config.init();
		logger.info("Initialized the configuration system.");
	}

	protected static void abrogate() {
		glfwTerminate();
		logger.info("Unloaded the Lwjgl library.");

		Config.abrogate();
		logger.info("Configurations saved.");
	}

}
