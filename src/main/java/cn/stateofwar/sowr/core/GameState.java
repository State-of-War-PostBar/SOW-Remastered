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

		LOGGER.info("Enabled a game state.");
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

}
