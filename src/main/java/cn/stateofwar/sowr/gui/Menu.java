package cn.stateofwar.sowr.gui;

import org.joml.Vector4d;

import cn.stateofwar.sowr.gui.control.XSprite;
import cn.stateofwar.sowr.gui.render.Texture;

/**
 * A game menu, which is a wrapper for a sprite (background).
 */
public class Menu extends XSprite {

	public Menu(String _identifier, Vector4d coord, Texture texture) {
		super(_identifier, coord, texture);
	}

}
