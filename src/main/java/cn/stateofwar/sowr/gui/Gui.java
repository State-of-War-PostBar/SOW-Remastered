package cn.stateofwar.sowr.gui;

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import cn.stateofwar.sowr.gui.control.Element;
import cn.stateofwar.sowr.gui.control.IInputReceiver;
import cn.stateofwar.sowr.gui.render.Textures;

/**
 * Gui controller.
 */
public class Gui {

	/** All the root gui elements. */
	private static Map<String, Element> elements = new TreeMap<>();

	/** All the elements that receive inputs. */
	private static Map<String, IInputReceiver> input_receivers = new TreeMap<>();

	/**
	 * Register an element.
	 * 
	 * @param element Element to register.
	 * 
	 * @return Instance of the element.
	 */
	public static Element register(Element element) {
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
	public static Element registerChild(Element element, Element parent) {
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
		for (Entry<String, Element> e : elements.entrySet())
			e.getValue().abrogate();
		elements.clear();
	}

	/**
	 * Update input listeners.
	 */
	public static void updateInputs() {
		for (Entry<String, IInputReceiver> e : input_receivers.entrySet())
			if (((Element) e).isEnabled())
				e.getValue().updateInputs();
	}

	/**
	 * Update all the elements.
	 */
	public static void update() {
		updateInputs();
		for (Entry<String, Element> e : elements.entrySet())
			e.getValue().update();
	}

	/**
	 * Render all the elements.
	 */
	public static void render() {
		for (Entry<String, Element> e : elements.entrySet())
			e.getValue().render();
	}

	/**
	 * Register default elements of the program.
	 */
	public static void init() {
		Textures.init();
	}

}
