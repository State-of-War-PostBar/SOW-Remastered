package cn.stateofwar.sowr.main;

import static org.lwjgl.glfw.GLFW.*;

import cn.stateofwar.sowr.util.Config;
import cn.stateofwar.sowr.util.I18n;
import cn.stateofwar.sowr.util.Logger;

/**
 * Procedures of preparing and ending of the program.
 */
public class Trim {

	private static final Logger LOGGER = new Logger("Main");

	/**
	 * Initialize the program.
	 * 
	 * @throws IllegalStateException Unable to initialize GLFW library.
	 */
	protected static void init() {
		Config.init();
		LOGGER.info("Loaded user configurations.");

		I18n.init();
		LOGGER.info("Loaded internationalization files.");
		LOGGER.info("Current language setting is: " + I18n.getLocaleName() + ".");

		if (!glfwInit()) {
			LOGGER.fatal("Cannot initialize GLFW!");
			throw new IllegalStateException("Failed to initialize GLFW.");
		}
	}

	/**
	 * Abrogate the program.
	 */
	protected static void abrogate() {
		glfwTerminate();
		LOGGER.info("Unloaded the Lwjgl library.");

		Config.abrogate();
		LOGGER.info("Configurations saved.");
	}

}
