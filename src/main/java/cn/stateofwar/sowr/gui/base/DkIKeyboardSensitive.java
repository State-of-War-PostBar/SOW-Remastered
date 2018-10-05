package cn.stateofwar.sowr.gui.base;

/**
 * An element that responses to keyboard inputs.
 */
public interface DkIKeyboardSensitive {

	/**
	 * Called when the registered key(s) is pressed.
	 */
	public void pressed();

	/**
	 * Called when the registered key(s) is not pressed.
	 */
	public void released();

	/**
	 * Register key(s) to the element.
	 * 
	 * @param keys Keys to register.
	 */
	public void registerKeys(int[] keys);

	/**
	 * Get the keys bind to the element.
	 */
	public int[] getKeys();

	/**
	 * Update keyboard status.
	 */
	public default void updateKey() {
		for (int key : getKeys())
			if (DkSInputs.DK_Keys[key]) {
				pressed();
				return;
			}
		released();
	}

}
