package cn.stateofwar.sowr.main;

import cn.stateofwar.sowr.util.Logger;
import cn.stateofwar.sowr.util.Utils;

public class Main {

	private static final Logger logger = new Logger("Main");

	public static void main(String[] args) {

		logger.init();

		if (!Utils.inNormalUniverse()) {
			logger.fatal("You cannot run this program in an abnormal universe!");
			System.exit(-1);
		}

		logger.info("Welcome to the new State of War!");
		logger.info("Initializing the program...");
		Trim.init();

		logger.info("Shutting down client...");
		Trim.abrogate();
		logger.info("The client is shut down. Thank you for playing!");

	}

}
