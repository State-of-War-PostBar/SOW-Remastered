package cn.stateofwar.sowr.gui.control;

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 * An element of the gui.
 */
public abstract class XElement {

	/** Identifier of the element. */
	protected String identifier;

	/** Parent of the element. */
	private XElement parent;

	/** Children of the element. */
	private Map<String, XElement> children = new TreeMap<>();

	/** If the element is enabled. */
	private boolean enabled = false;

	/** If the element has size. */
	private boolean has_size = false;

	/** If the element is hidden from rendering. */
	private boolean hidden = true;

	/**
	 * Get the identifier of the element.
	 * 
	 * @return Identifier of the element.
	 */
	public String getIdentifier() {
		return identifier;
	}

	/**
	 * Check if the element has a parent.
	 * 
	 * @return If the element has a parent.
	 */
	public boolean hasParent() {
		return parent != null;
	}

	/**
	 * Get the parent of the element.
	 * 
	 * @return Parent of the element, {@code null} if none.
	 */
	public XElement getParent() {
		return parent;
	}

	/**
	 * Set the parent of the element.
	 * 
	 * @param _parent Parent of the element.
	 */
	public void setParent(XElement _parent) {
		parent = _parent;
		parent.addChild(this);
	}

	/**
	 * Check if the element has child.
	 * 
	 * @return If the element has child.
	 */
	public boolean hasChild() {
		return !children.isEmpty();
	}

	/**
	 * Add a child to the element.
	 * 
	 * @param child Child to add.
	 */
	public void addChild(XElement child) {
		children.put(child.getIdentifier(), child);
	}

	/**
	 * Get a child of the element.
	 * 
	 * @param name Name of the child.
	 */
	public XElement getChild(String name) {
		return children.get(name);
	}

	/**
	 * Get the children of the element.
	 * 
	 * @return List of children of the element.
	 */
	public Map<String, XElement> getChildren() {
		return children;
	}

	/**
	 * Check if the element is enabled.
	 * 
	 * @return If the element is enabled.
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * Enable the element.
	 * 
	 * @return Instance of the element.
	 */
	public XElement enable() {
		enabled = true;
		if (hasChild())
			for (Entry<String, XElement> e : children.entrySet())
				e.getValue().enable();
		return this;
	}

	/**
	 * Disable the element.
	 * 
	 * @return Instance of the element.
	 */
	public XElement disable() {
		enabled = false;
		if (hasChild())
			for (Entry<String, XElement> e : children.entrySet())
				e.getValue().disable();
		return this;
	}

	/**
	 * Check if the element has size.
	 * 
	 * @return If the element has size.
	 */
	public boolean hasSize() {
		return has_size;
	}

	/**
	 * Check if the element is hidden.
	 * 
	 * @return If the element is hidden.
	 */
	public boolean isHidden() {
		return hidden;
	}

	/**
	 * Hide the element and all of its children.
	 * 
	 * @return Instance of the element.
	 */
	public XElement hide() {
		hidden = true;
		if (hasChild())
			for (Entry<String, XElement> e : children.entrySet())
				e.getValue().hide();
		return this;
	}

	/**
	 * Show the element and all of its children.
	 * 
	 * @return Instance of the element.
	 */
	public XElement show() {
		hidden = false;
		if (hasChild())
			for (Entry<String, XElement> e : children.entrySet())
				e.getValue().show();
		return this;
	}

	/**
	 * Update the element and all of its children.
	 */
	public void update() {
		if (isEnabled())
			if (hasChild())
				for (Entry<String, XElement> e : children.entrySet())
					e.getValue().update();
	}

	/**
	 * Render the element and all of its children.
	 */
	public void render() {
		if (!isHidden())
			if (hasChild())
				for (Entry<String, XElement> e : children.entrySet())
					e.getValue().render();
	}

	/**
	 * Delete the element and all of its children.
	 */
	public void abrogate() {
		if (hasChild())
			for (Entry<String, XElement> e : children.entrySet())
				e.getValue().abrogate();
	}

	/**
	 * Get the size information of the element.
	 */
	public abstract Object getSize();

	/**
	 * Resize the element.
	 * 
	 * @param size New size.
	 */
	public void resize(Object size) {
		if (hasChild())
			for (Entry<String, XElement> e : children.entrySet())
				e.getValue().resize(size);
	}

	/**
	 * Called when the parent is resized.
	 */
	public abstract void onParentResize(Object size);

}
