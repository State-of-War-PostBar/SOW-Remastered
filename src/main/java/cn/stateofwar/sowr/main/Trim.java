package cn.stateofwar.sowr.main;

import cn.stateofwar.sowr.util.Config;
import cn.stateofwar.sowr.util.I18n;
import cn.stateofwar.sowr.util.Logger;

public class Trim {

	private static final Logger LOGGER = new Logger("Main");

	protected static void init() {
		Config.init();
		LOGGER.info("Loaded user configurations.");

		I18n.init();
		LOGGER.info("Loaded internationalization files.");
		LOGGER.info("Current language setting is: " + I18n.getLocale().getName() + ".");
	}

	protected static void abrogate() {
		Config.abrogate();
		LOGGER.info("Configurations saved.");
	}

}
