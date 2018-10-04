package cn.stateofwar.sowr.gui;

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 * Current element list.
 */
public class DkSElementManager {

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
	 * @return The element requested.
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
	 * Update all the elements.
	 */
	public static void updateAll() {
		for (Entry<String, DkXElement> entry : roots.entrySet()) {
			DkXElement e = entry.getValue();
			e.update();
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
