package cn.stateofwar.sowr.core;

import cn.stateofwar.sowr.gui.Gui;
import cn.stateofwar.sowr.gui.render.Renderer;
import cn.stateofwar.sowr.util.Logger;

/**
 * A state controller of the game.
 */
public class GameState {

	private static final Logger LOGGER = new Logger("Game");

	/** Renderer for the frame. */
	private Renderer renderer;

	/**
	 * Enter the game state controller.
	 */
	public void enter(Renderer _renderer) {
		renderer = _renderer;

		LOGGER.info("Initialized a game state.");
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
	 * Handle input from user.
	 */
	public void input() {
		Gui.updateInputs();
		Gui.update();
	}

	/**
	 * Update game contents.
	 */
	public void update() {

	}

	/**
	 * Render screen contents.
	 */
	public void render() {
		Gui.render();
	}

	/**
	 * Exit out game state controller.
	 */
	public void exit() {
		Gui.deleteAll();
	}

}
