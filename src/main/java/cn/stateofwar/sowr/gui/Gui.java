package cn.stateofwar.sowr.gui;

import static org.lwjgl.glfw.GLFW.*;

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.joml.Vector4d;

import cn.stateofwar.sowr.gui.control.IInputReceiver;
import cn.stateofwar.sowr.gui.control.XClickerHold;
import cn.stateofwar.sowr.gui.control.XClickerInstant;
import cn.stateofwar.sowr.gui.control.XElement;
import cn.stateofwar.sowr.gui.control.XSprite;
import cn.stateofwar.sowr.gui.render.Graphic;
import cn.stateofwar.sowr.gui.render.Textures;

/**
 * Gui controller.
 */
public class Gui {

	/** All the root gui elements. */
	private static Map<String, XElement> elements = new TreeMap<>();

	/** All the elements that receive inputs. */
	private static Map<String, IInputReceiver> input_receivers = new TreeMap<>();

	/**
	 * Register an element.
	 * 
	 * @param element Element to register.
	 * 
	 * @return Instance of the element.
	 */
	public static XElement register(XElement element) {
		elements.put(element.getIdentifier(), element);
		return element;
	}

	/**
	 * Register a child element.
	 * 
	 * @param element Element to register.
	 * 
	 * @param parent  Parent of the element.
	 * 
	 * @return Instance of the element.
	 */
	public static XElement registerChild(XElement element, XElement parent) {
		if (parent == null)
			register(element);
		element.setParent(parent);
		return element;
	}

	/**
	 * Register an input receiver.
	 * 
	 * @param name  Name of the element.
	 * 
	 * @param input Element to register.
	 */
	public static void registerInput(String name, IInputReceiver input) {
		input_receivers.put(name, input);
	}

	/**
	 * Delete an element and all of its children.
	 * 
	 * @param name Name of the element.
	 */
	public static void delete(String name) {
		elements.get(name).abrogate();
		elements.remove(name);
	}

	/**
	 * Remove an element from input listening.
	 * 
	 * @param name Name of the element.
	 */
	public static void deleteInput(String name) {
		input_receivers.remove(name);
	}

	/**
	 * Delete all the elements and their children.
	 */
	public static void deleteAll() {
		input_receivers.clear();
		for (Entry<String, XElement> e : elements.entrySet())
			e.getValue().abrogate();
		elements.clear();
	}

	/**
	 * Update input listeners.
	 */
	public static void updateInputs() {
		for (Entry<String, IInputReceiver> e : input_receivers.entrySet())
			if (((XElement) e).isEnabled())
				e.getValue().updateInputs();
	}

	/**
	 * Update all the elements.
	 */
	public static void update() {
		for (Entry<String, XElement> e : elements.entrySet())
			e.getValue().update();
	}

	/**
	 * Render all the elements.
	 */
	public static void render() {
		for (Entry<String, XElement> e : elements.entrySet())
			e.getValue().render();
	}

	/**
	 * Register default elements of the program.
	 */
	public static void init() {
		double x_max = Graphic.win.getWidth();
		double y_max = Graphic.win.getHeight();

		Textures.init();

		Vector4d coord_menu_main = new Vector4d(0, 0, x_max, y_max);
		Vector4d coord_menu_main_btn_new_game = new Vector4d(0.065 * x_max, 0.85 * y_max, 0.20 * x_max, 0.072 * y_max);
		Vector4d coord_menu_main_btn_load_game = new Vector4d(0.065 * x_max, 0.70 * y_max, 0.20 * x_max, 0.072 * y_max);
		Vector4d coord_menu_main_btn_multi_game = new Vector4d(0.065 * x_max, 0.55 * y_max, 0.20 * x_max,
				0.072 * y_max);
		Vector4d coord_menu_main_btn_mod_manage = new Vector4d(0.065 * x_max, 0.40 * y_max, 0.20 * x_max,
				0.072 * y_max);
		Vector4d coord_menu_main_btn_settings = new Vector4d(0.065 * x_max, 0.25 * y_max, 0.20 * x_max, 0.072 * y_max);
		Vector4d coord_menu_main_btn_exit_game = new Vector4d(0.065 * x_max, 0.10 * y_max, 0.20 * x_max, 0.072 * y_max);

		Menu menu_main = (Menu) register(new MenuMain("menu.main", coord_menu_main, null));

		XClickerHold main_btn_new_game = (XClickerInstant) registerChild(
				new BtnMenuMainNewGame("menu.main.new_game", coord_menu_main_btn_new_game, GLFW_MOUSE_BUTTON_1),
				menu_main);
		registerInput(main_btn_new_game.getIdentifier(), main_btn_new_game);
		registerChild(new XSprite("menu.main.new_game.img", coord_menu_main_btn_new_game, null), main_btn_new_game);

		XClickerHold main_btn_load_game = (XClickerInstant) registerChild(
				new BtnMenuMainLoadGame("menu.main.load_game", coord_menu_main_btn_load_game, GLFW_MOUSE_BUTTON_1),
				menu_main);
		registerInput(main_btn_load_game.getIdentifier(), main_btn_load_game);
		registerChild(new XSprite("menu.main.load_game.img", coord_menu_main_btn_load_game, null), main_btn_load_game);

		XClickerHold main_btn_multi_game = (XClickerInstant) registerChild(
				new BtnMenuMainMultiGame("menu.main.multi_game", coord_menu_main_btn_multi_game, GLFW_MOUSE_BUTTON_1),
				menu_main);
		registerInput(main_btn_multi_game.getIdentifier(), main_btn_multi_game);
		registerChild(new XSprite("menu.main.multi_game.img", coord_menu_main_btn_multi_game, null),
				main_btn_multi_game);

		XClickerHold main_btn_mod_manage = (XClickerInstant) registerChild(
				new BtnMenuMainModManage("menu.main.mod_manage", coord_menu_main_btn_mod_manage, GLFW_MOUSE_BUTTON_1),
				menu_main);
		registerInput(main_btn_mod_manage.getIdentifier(), main_btn_mod_manage);
		registerChild(new XSprite("menu.main.mod_manage.img", coord_menu_main_btn_mod_manage, null),
				main_btn_mod_manage);

		XClickerHold main_btn_settings = (XClickerInstant) registerChild(
				new BtnMenuMainSettings("menu.main.settings", coord_menu_main_btn_settings, GLFW_MOUSE_BUTTON_1),
				menu_main);
		registerInput(main_btn_settings.getIdentifier(), main_btn_settings);
		registerChild(new XSprite("menu.main.settings.img", coord_menu_main_btn_settings, null), main_btn_settings);

		XClickerHold main_btn_exit_game = (XClickerInstant) registerChild(
				new BtnMenuMainExitGame("menu.main.exit_game", coord_menu_main_btn_exit_game, GLFW_MOUSE_BUTTON_1),
				menu_main);
		registerInput(main_btn_exit_game.getIdentifier(), main_btn_exit_game);
		registerChild(new XSprite("menu.main.exit_game.img", coord_menu_main_btn_exit_game, null), main_btn_exit_game);

		menu_main.enable().show();
		Menu.registerMenu(menu_main);

		Menu menu_new_game = (Menu) register(new MenuMain("menu.new_game", coord_menu_main, null));
		Menu.registerMenu(menu_new_game);

		Menu menu_load_game = (Menu) register(new MenuMain("menu.load_game", coord_menu_main, null));
		Menu.registerMenu(menu_load_game);

		Menu menu_multi_game = (Menu) register(new MenuMain("menu.multi_game", coord_menu_main, null));
		Menu.registerMenu(menu_multi_game);

		Menu menu_mod_manage = (Menu) register(new MenuMain("menu.mod_manage", coord_menu_main, null));
		Menu.registerMenu(menu_mod_manage);

		Menu menu_settings = (Menu) register(new MenuMain("menu.settings", coord_menu_main, null));
		Menu.registerMenu(menu_settings);

	}

}
