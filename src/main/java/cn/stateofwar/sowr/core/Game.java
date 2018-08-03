package cn.stateofwar.sowr.core;

import cn.stateofwar.sowr.gui.Graphic;
import cn.stateofwar.sowr.gui.multimedia.Window;
import cn.stateofwar.sowr.gui.render.Renderer;
import cn.stateofwar.sowr.util.Config;
import cn.stateofwar.sowr.util.Logger;

public class Game {

	private static final Logger logger = new Logger("Game");

	public static boolean running;

	public static Timer timer = new Timer();

	protected static GameState currentState = new GameState();

	protected static Window win;

	private static Renderer renderer;

	public static void startGame() {
		logger.info("The game is starting!");

		running = true;
		Graphic.initRenderCapabilities();
		win = Graphic.win;
		renderer = new Renderer();

		currentState.enter(renderer);
		timer.init();

		while (running && !win.closing()) {
			win.clear();

			currentState.input();

			currentState.update();
			timer.updateUPS();

			currentState.render();
			win.update();
			timer.updateFPS();
			if (win.isVSync())
				sync(Integer.parseInt(Config.get("GUI", "Max FPS")));

			timer.update();
		}
	}

	public static void abrogate() {
		Graphic.abrogateRender();

		logger.info("The game is shut down.");
	}

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
