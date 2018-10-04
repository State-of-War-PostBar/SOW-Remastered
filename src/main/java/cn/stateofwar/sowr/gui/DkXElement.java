package cn.stateofwar.sowr.gui;

import java.util.ArrayList;
import java.util.List;

/**
 * An element of GUI. This is the basis for all the elements and should never be
 * constructed as a formal one.
 */
public abstract class DkXElement {

	/** Identifier of the element. */
	protected String identifier;

	/** Parent of the element. */
	protected DkXElement parent;

	/** Children of the element. */
	protected List<DkXElement> children = new ArrayList<>();

	/**
	 * Get the identifier for the element.
	 * 
	 * @return Identifier of the element.
	 */
	public String getIdentifier() {
		return identifier;
	}

	/**
	 * Check if the element has parent.
	 * 
	 * @return If the element has parent.
	 */
	public boolean hasParent() {
		return parent != null;
	}

	/**
	 * Get the parent of the element.
	 * 
	 * @return Parent of the element.
	 */
	public DkXElement getParent() {
		return parent;
	}

	/**
	 * Set the parent of the element.
	 * 
	 * @param _parent Parent of the element.
	 */
	public void setParent(DkXElement _parent) {
		parent = _parent;
		_parent.addChild(this);
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
	 * @param child New child.
	 */
	public void addChild(DkXElement child) {
		children.add(child);
	}

	/**
	 * Get all the children of the element.
	 * 
	 * @return All the children of the element.
	 */
	public List<DkXElement> getChildren() {
		return children;
	}

	/**
	 * Update the element and all of its children.
	 */
	public void update() {
		for (DkXElement e : children)
			e.update();
	}

	/**
	 * Delete the element and all of its children.
	 */
	public void abrogate() {
		for (DkXElement e : children)
			e.abrogate();
	}

}
