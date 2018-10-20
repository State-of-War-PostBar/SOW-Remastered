package cn.stateofwar.sowr.core;

import cn.stateofwar.sowr.util.Logger;

public class Core {

	private static final Logger LOGGER = new Logger("Core");

	public static GameState state;

	public static Timer timer;

	private static boolean running;

	public static void start() {
		LOGGER.info("The game is starting.");

		state = new GameState();
		state.enter();

		running = true;
		timer = new Timer();
		timer.init();

		while (running) {
			state.input();

			state.update();

			state.render();

			timer.update();
		}

	}

	public static void abrogate() {
		LOGGER.info("The game is shut down.");
	}

	public static boolean isRunning() {
		return running;
	}

	public static void stopRunning() {
		running = false;
	}

}
