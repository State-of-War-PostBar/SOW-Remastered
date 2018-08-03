package cn.stateofwar.sowr.core;

import cn.stateofwar.sowr.gui.render.Renderer;

public class GameState {

	protected Renderer renderer;

	// Handle input from the user. This stage is also useful for getting
	// information from the network in multiplayer games. (if that's possible...)
	public void input() {

	}

	// Update the game content. Fixed time step update is good enough.
	public void update() {

	}

	// Render things on the screen...
	public void render() {

	}

	public void enter(Renderer _renderer) {
		renderer = _renderer;
	}

	public void exit() {

	}

}
