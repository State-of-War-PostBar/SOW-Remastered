package cn.stateofwar.sowr.gui.control;

/**
 * A key sensor.
 */
public abstract class KeySensor extends Element implements IKeyInductive {

	/** Key binds to the element. */
	private int key;

	public KeySensor(String _identifier, int key) {
		identifier = _identifier;
		setKey(key);
	}

	/**
	 * Called when the key is pressed.
	 */
	public abstract void pressed();

	/**
	 * Called when the key is not pressed.
	 **/
	public abstract void released();

	/**
	 * Get the key binds to the element.
	 * 
	 * @return Key binds to the element.
	 */
	@Override
	public int getKey() {
		return key;
	}

	/**
	 * Set the key binds to the element.
	 * 
	 * @param _key Key binds to the element.
	 */
	public void setKey(int _key) {
		key = _key;
	}

	/**
	 * A key sensor does not have size.
	 * 
	 * @return {@code null}
	 */
	@Override
	public Object getSize() {
		return null;
	}

	/**
	 * A key sensor does not have size, only resize its children.
	 * 
	 * @param size New size.
	 */
	@Override
	public void resize(Object size) {
		super.resize(size);
	}

	/**
	 * Called when the parent is resized. In this case we proceed resize method on
	 * children.
	 * 
	 * @param size New size.
	 */
	@Override
	public void onParentResize(Object size) {
		resize(size);
	}

}
