package cn.stateofwar.sowr.core;

import cn.stateofwar.sowr.gui.render.Renderer;

/**
 * A state controller of the game.
 */
public class GameState {

	private Renderer renderer;

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

	/**
	 * Enter this game state controller.
	 */
	public void enter(Renderer _renderer) {
		renderer = _renderer;
	}

	/**
	 * Exit out of this game state controller.
	 */
	public void exit() {

	}

}
