package cn.stateofwar.sowr.main;

import cn.stateofwar.sowr.core.Game;
import cn.stateofwar.sowr.util.Logger;

public class Main {

	private static final Logger logger = new Logger("Main");

	public static void main(String[] args) {

		logger.init();
		logger.info("Welcome to the new State of War!");
		logger.info("Initializing the program...");
		Trim.init();

		Game.startGame();

		Game.abrogate();

		logger.info("Shutting down client...");
		Trim.abrogate();
		logger.info("The client is shut down.");

	}

}
