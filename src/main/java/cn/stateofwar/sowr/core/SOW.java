package cn.stateofwar.sowr.core;

import cn.stateofwar.sowr.gui.Graphic;
import cn.stateofwar.sowr.gui.render.Renderer;
import cn.stateofwar.sowr.util.Logger;

/**
 * The game process.
 */
public class SOW {

	private static final Logger logger = new Logger("Game");

	/**
	 * If the game is running.
	 */
	public static boolean running;

	/**
	 * Renderer for the game.
	 */
	private static Renderer renderer = new Renderer();

	/**
	 * Timer for syncing fps and ups.
	 */
	private static Timer timer = new Timer();

	public static void startGame() {

		logger.info("The game is starting!");
		running = true;
		timer.init();

		Graphic.initRenderCapabilities();
		renderer.init();

		logger.info("Starting game loop.");

		while (running && !Graphic.win.closing()) {

			renderer.clear();

			renderer.drawText("SB", Graphic.win.getWidth() / 2, Graphic.win.getHeight() / 2);

			Graphic.win.update();

		}

		logger.info("Game loop is broke.");
	}

	public static void dispose() {
		renderer.dispose();
		Graphic.abrogateRender();

		logger.info("The game engine is shut down.");
	}

}
