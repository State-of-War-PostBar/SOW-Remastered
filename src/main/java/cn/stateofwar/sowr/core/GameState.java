package cn.stateofwar.sowr.core;

import cn.stateofwar.sowr.gui.render.Renderer;

public class GameState {

	protected Renderer renderer;

	public void input() {

	}

	public void update() {

	}

	public void render() {

	}

	public void enter(Renderer _renderer) {
		renderer = _renderer;
	}

	public void exit() {

	}

}
