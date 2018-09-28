package cn.stateofwar.sowr.core;

import cn.stateofwar.sowr.gui.Graphic;
import cn.stateofwar.sowr.gui.multimedia.Window;
import cn.stateofwar.sowr.util.Logger;

/**
 * Manager of input, update and render.
 */
public class Game {

	private static final Logger logger = new Logger("Game");

	/** If the game is running. */
	public static boolean running;

	/** Timer for the game engine. */
	public static Timer timer = new Timer();

	/** Current game state. */
	protected static GameState currentState = new GameState();

	/** Window of the game engine. */
	protected static Window win;

	/**
	 * Start the game engine.
	 */
	public static void startGame() {
		logger.info("The game is starting.");

		running = true;
		Graphic.initRenderCapabilities();
		win = Graphic.win;

		currentState.enter();
		timer.init();

		while (running && !win.isClosing()) {
			win.clear();

			currentState.input();

			currentState.update();
			timer.updateUPS();

			currentState.render();
			win.update();
			timer.updateFPS();
			if (win.isVSync())
				sync(Graphic.desiredFPS);

			timer.update();
		}

		currentState.exit();

	}

	/**
	 * End the game engine.
	 */
	public static void abrogate() {
		Graphic.abrogateRender();

		logger.info("The game is shut down.");
	}

	/**
	 * Eliminating over frames and lock in vertical sync (only if enabled).
	 */
	private static void sync(int fps) {
		double lastLoopTime = timer.getLastLoopTime();
		double now = timer.getTime();
		float targetTime = 1.0f / fps;

		while (now - lastLoopTime < targetTime) {
			Thread.yield();
			try {
				Thread.sleep(1);
			} catch (InterruptedException ex) {
				logger.fatal(ex.getLocalizedMessage());
			}
			now = timer.getTime();
		}
	}

}
