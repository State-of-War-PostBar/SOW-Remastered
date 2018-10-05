package cn.stateofwar.sowr.core;

import cn.stateofwar.sowr.References;
import cn.stateofwar.sowr.gui.multimedia.Window;
import cn.stateofwar.sowr.gui.render.Graphic;
import cn.stateofwar.sowr.gui.render.Renderer;
import cn.stateofwar.sowr.util.Logger;

/**
 * Manager of input, update and render.
 */
public class Game {

	private static final Logger logger = new Logger("Game");

	/** If the game is running. */
	public static boolean running;

	/** Current game state. */
	public static GameState state = new GameState();

	/** Renderer for the game. */
	public static Renderer renderer = new Renderer();

	/** Timer for the game engine. */
	public static Timer timer = new Timer();

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

		state.enter(renderer);
		timer.init();

		while (running && !win.isClosing()) {
			win.clear();

			state.input();

			state.update();
			timer.updateUPS();

			state.render();
			win.update();
			timer.updateFPS();
			if (win.isVSync())
				syncFrame(References.VERTICAL_SYNC_FPS);
			else
				syncFrame(Graphic.desiredFPS);

			timer.update();
		}

		state.exit();
	}

	/**
	 * End the game engine.
	 */
	public static void abrogate() {
		Graphic.abrogateRender();

		logger.info("The game is shut down.");
	}

	/**
	 * Eliminating over frames and lock in vertical sync (only if it's enabled).
	 */
	private static void syncFrame(int fps) {
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
