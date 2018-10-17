package cn.stateofwar.sowr.core;

import cn.stateofwar.sowr.References;
import cn.stateofwar.sowr.gui.Gui;
import cn.stateofwar.sowr.gui.render.Graphic;
import cn.stateofwar.sowr.gui.render.Renderer;
import cn.stateofwar.sowr.gui.render.multimedia.Window;
import cn.stateofwar.sowr.util.Logger;

/**
 * Manager of input, update and render.
 */
public class Game {

	private static final Logger LOGGER = new Logger("Game");

	/** If the game is running. */
	public static boolean running;

	/** Current game state. */
	public static GameState state;

	/** Timer for the game engine. */
	public static Timer timer;

	/** Window of the game engine. */
	private static Window window;

	/**
	 * Start the game engine.
	 */
	public static void startGame() {
		LOGGER.info("The game is starting.");

		running = true;

		Graphic.initRenderCapabilities();
		window = Graphic.window;
		Gui.init();

		state = new GameState();
		timer = new Timer();
		state.enter(new Renderer());
		timer.init();

		while (running && !window.isClosing()) {
			window.clear();

			state.input();

			state.update();
			timer.updateUPS();

			state.render();

			window.update();
			timer.updateFPS();
			if (window.isVSync())
				syncFrame(References.VERTICAL_SYNC_FPS);
			else
				syncFrame(Graphic.max_fps);

			timer.update();
		}

		state.exit();
	}

	/**
	 * End the game engine.
	 */
	public static void abrogate() {
		Graphic.abrogateRender();

		LOGGER.info("The game is shut down.");
	}

	/**
	 * Eliminating over frames.
	 * 
	 * @param fps The frames per second limit.
	 */
	private static void syncFrame(int fps) {
		double last_loop_time = timer.getLastLoopTime();
		double now = timer.getTime();
		float target_time = 1.0f / fps;

		while (now - last_loop_time < target_time) {
			Thread.yield();
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				LOGGER.fatal(e.getLocalizedMessage());
			}
			now = timer.getTime();
		}
	}

}
