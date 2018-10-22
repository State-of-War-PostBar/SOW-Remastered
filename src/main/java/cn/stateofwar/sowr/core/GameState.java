package cn.stateofwar.sowr.core;

import cn.stateofwar.sowr.graphic.Graphics;
import cn.stateofwar.sowr.util.Logger;

public class GameState {

	private static final Logger LOGGER = new Logger("Core");

	private InputHook inputs;

	private Graphics graphics;

	public void enter() {
		inputs = new InputHook();
		graphics = new Graphics();

		graphics.initRenderCapabilities();

		LOGGER.info("Started a game state.");
	}

	public void input() {
		if (graphics.window.isClosing())
			Core.stopRunning();
	}

	public void update() {

	}

	public void render() {
		graphics.window.update();
		Core.timer.updateFPS();

		sync(graphics.max_fps);

		graphics.window.clear();
	}

	public InputHook getInputHook() {
		return inputs;
	}

	public Graphics getGraphic() {
		return graphics;
	}

	public void abrogate() {
		graphics.abrogate();

		LOGGER.info("Exited a game state.");
	}

	public static void sync(int fps) {
		double last_loop_time = Core.timer.getLastLoopTime();
		double now = Core.timer.getTime();
		float target_time = 1.0f / fps;
		while (now - last_loop_time < target_time) {
			Thread.yield();
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				LOGGER.fatal(e.getLocalizedMessage());
				e.printStackTrace();
			}
			now = Core.timer.getTime();
		}
	}

}
