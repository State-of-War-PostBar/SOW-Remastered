package cn.stateofwar.sowr.core;

import cn.stateofwar.sowr.gui.render.Renderer;
import cn.stateofwar.sowr.util.Logger;

/**
 * A state controller of the game.
 */
public class GameState {

	private static final Logger logger = new Logger("Game");

	private Renderer renderer;

	/**
	 * Enter this game state controller.
	 */
	public void enter(Renderer _renderer) {
		renderer = _renderer;

		logger.info("Initialized a game state.");
	}

	/**
	 * Exit out of this game state controller.
	 */
	public void exit() {

	}

	/**
	 * Get a renderer for current game state.
	 * 
	 * @return Current renderer.
	 */
	public Renderer getRenderer() {
		return renderer;
	}

	/**
	 * Handle input from the user.
	 */
	public void input() {

	}

	/**
	 * Update the game contents.
	 */
	public void update() {

	}

	/**
	 * Render the screen contents.
	 */
	public void render() {

	}

}
