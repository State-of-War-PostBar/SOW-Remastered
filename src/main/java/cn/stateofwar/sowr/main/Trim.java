package cn.stateofwar.sowr.main;

import static org.lwjgl.glfw.GLFW.*;

import cn.stateofwar.sowr.util.Config;
import cn.stateofwar.sowr.util.I18n;
import cn.stateofwar.sowr.util.Logger;

/**
 * Procedures of preparing and ending of the program.
 */
public class Trim {

	private static final Logger logger = new Logger("Main");

	/**
	 * Initialize the program.
	 * 
	 * @throws IllegalStateException Unable to initialize GLFW library.
	 */
	protected static void init() {
		Config.init();
		logger.info("Loaded user configurations.");

		I18n.init();
		logger.info("Loaded internationalization files.");
		logger.info("Current language setting is: " + I18n.getLocaleName() + ".");

		if (!glfwInit()) {
			logger.fatal("Cannot initialize GLFW!");
			throw new IllegalStateException("Failed to initialize GLFW.");
		}
	}

	/**
	 * Abrogate the program.
	 */
	protected static void abrogate() {
		glfwTerminate();
		logger.info("Unloaded the Lwjgl library.");

		Config.abrogate();
		logger.info("Configurations saved.");
	}

}
