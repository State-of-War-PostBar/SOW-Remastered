package cn.stateofwar.sowr.gui;

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import cn.stateofwar.sowr.gui.base.DkIClickable;
import cn.stateofwar.sowr.gui.base.DkICursorSensitive;
import cn.stateofwar.sowr.gui.base.DkIKeyboardSensitive;
import cn.stateofwar.sowr.gui.base.DkSTimer;
import cn.stateofwar.sowr.gui.base.DkXElement;
import cn.stateofwar.sowr.gui.base.DkXVisual;

public class GuiManager {

	/** All the <b>ROOT</b> elements. */
	private static Map<String, DkXElement> roots = new TreeMap<>();

	/**
	 * Register an element.
	 * 
	 * @param element Registering element.
	 */
	public static void register(DkXElement element) {
		roots.put(element.getIdentifier(), element);
	}

	/**
	 * Register an element as another one's child.
	 * 
	 * @param element Registering element.
	 * 
	 * @param parent  Parent of the element. If it's null then it will be a root.
	 */
	public static void registerChild(DkXElement element, DkXElement parent) {
		if (parent == null)
			register(element);
		element.setParent(parent);
	}

	/**
	 * Get an element.
	 * 
	 * @param identifier Identifier of the element.
	 * 
	 * @return Element requested.
	 */
	public static DkXElement getElement(String identifier) {
		return roots.get(identifier);
	}

	/**
	 * Delete an element and all of its child.
	 * 
	 * @param identifier Identifier of the element.
	 */
	public static void deleteElement(String identifier) {
		roots.get(identifier).abrogate();
		roots.remove(identifier);
	}

	/**
	 * Update all the input listeners.
	 */
	public static void updateInputs() {
		for (Entry<String, DkXElement> entry : roots.entrySet()) {
			DkXElement e = entry.getValue();

			if (e instanceof DkICursorSensitive)
				((DkICursorSensitive) e).updateCursor();

			if (e instanceof DkIClickable)
				((DkIClickable) e).updateCursor();

			if (e instanceof DkIKeyboardSensitive)
				((DkIKeyboardSensitive) e).updateKey();

		}
	}

	/**
	 * Update all the elements.
	 */
	public static void updateAll() {
		DkSTimer.update();
		for (Entry<String, DkXElement> entry : roots.entrySet())
			entry.getValue().update();
	}

	/**
	 * Render all the visual elements.
	 */
	public static void renderVisuals() {
		for (Entry<String, DkXElement> entry : roots.entrySet()) {
			DkXElement e = entry.getValue();

			if (e instanceof DkXVisual)
				((DkXVisual) e).render();
		}
	}

	/**
	 * Delete all the elements.
	 */
	public static void abrogateAll() {
		for (Entry<String, DkXElement> entry : roots.entrySet())
			entry.getValue().abrogate();
	}

}
