package cn.stateofwar.sowr.gui;

import java.util.Vector;

/**
 * An element of GUI.
 */
public abstract class DkElement {

	/** Parent of this element. */
	private DkElement parent;

	/** Children of this element. */
	private Vector<DkElement> children = new Vector<>();

	/**
	 * Check if this element has parent.
	 * 
	 * @return If this element has parent.
	 */
	public boolean hasParent() {
		return !children.isEmpty();
	}

	/**
	 * Get the parent of this element.
	 * 
	 * @return Parent of this element.
	 */
	public DkElement getParent() {
		return parent;
	}

	/**
	 * Set the parent of this element.
	 * 
	 * @param _parent Parent of this element.
	 */
	public void setParent(DkElement _parent) {
		parent = _parent;
		_parent.addChild(this);
	}

	/**
	 * Add a child to this element.
	 * 
	 * @param child New child.
	 */
	public void addChild(DkElement child) {
		children.addElement(child);
	}

	/**
	 * Get all the children of this element.
	 * 
	 * @param All the children of this element.
	 */
	public Vector<DkElement> getChildren() {
		return children;
	}

	/**
	 * Get the identifier for this element.
	 */
	public abstract String getIdentifier();

	/**
	 * Update the element and all of it's children.
	 */
	public abstract void update();

	/**
	 * Render the element and all of it's children.
	 */
	public abstract void render();

	/**
	 * Delete the element and all of it's children.
	 */
	public abstract void abrogate();

}
