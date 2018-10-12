package cn.stateofwar.sowr.gui;

import java.util.HashMap;
import java.util.Map;

import org.joml.Vector4d;

import cn.stateofwar.sowr.gui.control.Sprite;
import cn.stateofwar.sowr.gui.render.Texture;

/**
 * A game menu, which is a wrapper for a sprite (background).
 */
public abstract class Menu extends Sprite {

	/** All the menus of the program. */
	private static Map<String, Menu> menus = new HashMap<>();

	/**
	 * Create a menu.
	 * 
	 * @param _identifier Identifier for GUI element.
	 * 
	 * @param coord       Coordinates of a menu.x-y are coordinate values of the
	 *                    bottom left corner, z-w are its width and height.
	 * 
	 * @param texture     Texture of the background image.
	 */
	public Menu(String _identifier, Vector4d coord, Texture texture) {
		super(_identifier, coord, texture);
	}

	/**
	 * Called when a menu is activated.
	 */
	public abstract Menu switched();

	/**
	 * Register a menu.
	 * 
	 * @param menu Menu to register.
	 */
	public static void registerMenu(Menu menu) {
		menus.put(menu.getIdentifier(), menu);
	}

	/**
	 * Get a menu by its name (element identifier).
	 * 
	 * @param name Name of the menu.
	 * 
	 * @return Instance of the menu.
	 */
	public static Menu getMenu(String name) {
		return menus.get(name);
	}

	/**
	 * Activate a menu.
	 * 
	 * @param name Name of the menu.
	 * 
	 * @return Instance of the menu.
	 */
	public static Menu activateMenu(String name) {
		return ((Menu) menus.get(name).enable().show()).switched();
	}

	/**
	 * Deactivate a menu.
	 * 
	 * @param name Name of the menu.
	 * 
	 * @return Instance of the menu.
	 */
	public static Menu deActivateMenu(String name) {
		return (Menu) menus.get(name).disable().hide();
	}

}
