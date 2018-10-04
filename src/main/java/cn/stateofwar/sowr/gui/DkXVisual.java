package cn.stateofwar.sowr.gui;

/**
 * A visible element.
 */
public abstract class DkXVisual extends DkXElement {

	/** If this element is hidden. */
	protected boolean hidden;

	/**
	 * Get the coordinate information of the element.
	 * 
	 * @return Coordinate information.
	 */
	public abstract Object getCoordInfo();

	/**
	 * Resize the element.
	 * 
	 * @param coord The new coordinate information.
	 */
	public void resize(Object coord) {
		if (hasChild())
			for (DkXElement e : children)
				((DkXVisual) e).onParentResize(coord);
	}

	/**
	 * Called when the element's parent is resized.
	 */
	public void onParentResize(Object coord) {
		resize(coord);
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
	 * Hide the element from rendering.
	 */
	public void hide() {
		hidden = true;
	}

	/**
	 * Show the element from rendering.
	 */
	public void show() {
		hidden = false;
	}

	/**
	 * Render the element and all of its children.
	 */
	public void render() {
		if (hasChild())
			for (DkXElement e : children)
				((DkXVisual) e).render();
	}

}
