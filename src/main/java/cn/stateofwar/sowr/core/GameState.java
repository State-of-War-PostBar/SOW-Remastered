package cn.stateofwar.sowr.core;

import cn.stateofwar.sowr.gui.Gui;
import cn.stateofwar.sowr.gui.render.Renderer;
import cn.stateofwar.sowr.util.Logger;

/**
 * A state controller of the game.
 */
public class GameState {

	private static final Logger logger = new Logger("Game");

	/** Renderer for the frame. */
	private Renderer renderer;

	/**
	 * Enter the game state controller.
	 */
	public void enter(Renderer _renderer) {
		renderer = _renderer;
		Gui.registerElements();

		logger.info("Initialized a game state.");
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
	}

	/**
	 * Update game contents.
	 */
	public void update() {
		Gui.updateElements();
	}

	/**
	 * Render screen contents.
	 */
	public void render() {
		Gui.renderElements();
	}

	/**
	 * Exit out game state controller.
	 */
	public void exit() {
		Gui.deleteAll();
	}

}
