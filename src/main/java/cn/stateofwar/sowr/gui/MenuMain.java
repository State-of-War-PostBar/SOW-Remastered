package cn.stateofwar.sowr.gui;

import org.joml.Vector4d;

import cn.stateofwar.sowr.gui.render.Texture;

/**
 * Main menu (the one opens in the beginning).
 */
public class MenuMain extends Menu {

	public MenuMain(String _identifier, Vector4d coord, Texture texture) {
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
