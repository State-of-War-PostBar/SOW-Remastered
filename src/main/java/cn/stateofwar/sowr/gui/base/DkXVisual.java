package cn.stateofwar.sowr.gui.base;

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
	 * @param coord New coordinate information.
	 */
	public void resize(Object coord) {
		if (hasChild())
			for (DkXElement e : children)
				if (e instanceof DkXVisual)
					((DkXVisual) e).onParentResize(coord);
				else
					continue;
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
	 * Hide the element and all of its children from rendering.
	 */
	public void hide() {
		hidden = true;
		for (DkXElement e : children)
			if (e instanceof DkXVisual)
				((DkXVisual) e).hide();
			else
				continue;
	}

	/**
	 * Show the element and all of its children from rendering.
	 */
	public void show() {
		hidden = false;
		for (DkXElement e : children)
			if (e instanceof DkXVisual)
				((DkXVisual) e).show();
			else
				continue;
	}

	/**
	 * Render the element and all of its children.
	 */
	public void render() {
		if (hasChild())
			for (DkXElement e : children)
				if (e instanceof DkXVisual)
					((DkXVisual) e).render();
				else
					continue;
	}

}
