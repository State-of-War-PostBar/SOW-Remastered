package cn.stateofwar.sowr.gui;

import org.joml.Vector4d;

import cn.stateofwar.sowr.gui.render.Texture;

/**
 * Multi-player games menu.
 */
public class MenuMultiGame extends Menu {

	public MenuMultiGame(String _identifier, Vector4d coord, Texture texture) {
		super(_identifier, coord, texture);
	}

	/**
	 * Called when a menu is activated.
	 */
	@Override
	public Menu switched() {
		return this;
	}

}
