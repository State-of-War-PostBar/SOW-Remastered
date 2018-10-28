package cn.stateofwar.sowr.core;

import cn.stateofwar.sowr.graphic.Graphics;
import cn.stateofwar.sowr.graphic.multimedia.Window;
import cn.stateofwar.sowr.graphic.render.Renderer;
import cn.stateofwar.sowr.util.Logger;

/**
 * A game state moderator.
 */
public class GameState {

	private static final Logger LOGGER = new Logger("Core");

	/** Hook for user inputs. */
	private InputHook inputs;

	/** Graphical processor for the game state. */
	private Graphics graphics;

	/**
	 * Enter a game state.
	 */
	public void enter() {
		inputs = new InputHook();
		graphics = new Graphics();

		graphics.initRenderCapabilities();

		LOGGER.info("Started a game state.");
	}

	/**
	 * Handle user inputs.
	 */
	public void input() {
		if (graphics.window.isClosing())
			Core.stop();
	}

	/**
	 * Update game contents and UI.
	 */
	public void update() {

	}

	/**
	 * Render the UI.
	 */
	public void render() {
		graphics.window.clear();

		sync(graphics.max_fps);
		graphics.window.update();
		Core.timer.updateFPS();
	}

	/**
	 * Get the input hook.
	 * 
	 * @return The input hook.
	 */
	public InputHook getInputHook() {
		return inputs;
	}

	/**
	 * Get the graphic processor.
	 * 
	 * @return The current graphic processor.
	 */
	public Graphics getGraphic() {
		return graphics;
	}

	/**
	 * Get the game window.
	 * 
	 * @return The current game window.
	 */
	public Window getWindow() {
		return graphics.window;
	}

	/**
	 * Get the UI renderer.
	 * 
	 * @return The current renderer.
	 */
	public Renderer getRenderer() {
		return graphics.renderer;
	}

	/**
	 * Exit a game state.
	 */
	public void abrogate() {
		graphics.abrogate();

		LOGGER.info("Exited a game state.");
	}

	/**
	 * Sync a window and filter out over-frames.
	 * 
	 * @param fps The maximum frames per second.
	 */
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
