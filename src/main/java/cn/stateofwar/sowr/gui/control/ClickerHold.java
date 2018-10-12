package cn.stateofwar.sowr.gui.control;

import org.joml.Vector4d;

/**
 * An area responses to mouse button inputs.
 */
public abstract class ClickerHold extends Element implements IClickable {

	/** Coordinate of the element. */
	private Vector4d coord;

	/** If the cursor is on the element. */
	private boolean cursor_on;

	/** Mouse button that can trigger the element. */
	private int mouse_button;

	/**
	 * Create a holding clicker.
	 * 
	 * @param _identifier  Identifier for GUI element.
	 * 
	 * @param coord        Coordinates of inductive area. x-y are coordinate values
	 *                     of the bottom left corner, z-w are its width and height.
	 * 
	 * @param mouse_button Mouse button the clicker responses.
	 */
	public ClickerHold(String _identifier, Vector4d coord, int mouse_button) {
		identifier = _identifier;
		resize(coord);
		setButton(mouse_button);
	}

	/**
	 * Get the area the element responses.
	 */
	@Override
	public Vector4d getInductiveArea() {
		return coord;
	}

	/**
	 * Called when the cursor is on the element.
	 */
	@Override
	public void cursorOn() {
		cursor_on = true;
	}

	/**
	 * Check if the cursor is on the element.
	 * 
	 * @return If the cursor is on the element.
	 */
	public boolean isCursorOn() {
		return cursor_on;
	}

	/**
	 * Called when the cursor is not on the element.
	 */
	@Override
	public void cursorOff() {
		cursor_on = false;
	}

	/**
	 * Update the element and all of its children.
	 */
	@Override
	public void update() {
		super.update();
	}

	/**
	 * Called when the mouse button is pressed down on the element.
	 */
	public abstract void clicked();

	/**
	 * Called when the mouse button is not pressed down on the element.
	 */
	public abstract void released();

	/**
	 * Get the mouse button registered to the element.
	 * 
	 * @return Mouse button registered to the element.
	 */
	@Override
	public int getButton() {
		return mouse_button;
	}

	/**
	 * Set the mouse button registered to the element.
	 * 
	 * @param _mouse_button Mouse button registered to the element.
	 */
	public void setButton(int _mouse_button) {
		mouse_button = _mouse_button;
	}

	/**
	 * Get the size of the element.
	 * 
	 * @return size of the element.
	 */
	@Override
	public Object getSize() {
		return coord;
	}

	/**
	 * Resize the element.
	 */
	@Override
	public void resize(Object size) {
		coord = (Vector4d) size;
		super.resize(size);
	}

}
