package cn.stateofwar.sowr.core;

import static org.lwjgl.opengl.GL11.*;

import cn.stateofwar.sowr.gui.Graphic;
import cn.stateofwar.sowr.gui.multimedia.Window;
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
	 * Timer for syncing fps and ups.
	 */
	private static Timer timer = new Timer();

	private static Window win;

	public static void startGame() {

		logger.info("The game is starting!");
		running = true;
		timer.init();

		Graphic.initRenderCapabilities();
		win = Graphic.win;

		logger.info("Starting game loop.");

		while (running && !win.closing()) {

			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

			win.update();

		}

		logger.info("Game loop is broke.");
	}

	public static void dispose() {
		Graphic.abrogateRender();

		logger.info("The game engine is shut down.");
	}

}
