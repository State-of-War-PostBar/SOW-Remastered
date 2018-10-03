package cn.stateofwar.sowr.gui;

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 * Current element list.
 */
public class DkElementList {

	/** All the elements. */
	private static Map<String, DkElement> elements = new TreeMap<>();

	/**
	 * Register an element.
	 * 
	 * @param element Registering element.
	 * 
	 * @param parent  Parent of the element. If it's null then it will be a root.
	 */
	public static void registerElement(DkElement element, DkElement parent) {
		if (parent == null)
			;
		else
			element.setParent(parent);

		elements.put(element.getIdentifier(), element);
	}

	/**
	 * Get an element.
	 * 
	 * @param identifier Identifier of the element.
	 * 
	 * @return The element requested.
	 */
	public static DkElement getElement(String identifier) {
		return elements.get(identifier);
	}

	/**
	 * Delete an element and all of its child.
	 * 
	 * @param identifier Identifier of the element.
	 */
	public static void deleteElement(String identifier) {
		elements.get(identifier).abrogate();
		elements.remove(identifier);
	}

	/**
	 * Update all the elements.
	 */
	public static void updateAll() {
		for (Entry<String, DkElement> entry : elements.entrySet()) {
			entry.getValue().update();
			entry.getValue().render();
		}
	}

	/**
	 * Delete all the elements.
	 */
	public static void abrogateAll() {
		for (Entry<String, DkElement> entry : elements.entrySet())
			entry.getValue().abrogate();
	}

}
