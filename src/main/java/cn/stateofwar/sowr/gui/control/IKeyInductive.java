package cn.stateofwar.sowr.gui.control;

import cn.stateofwar.sowr.core.Inputs;

/**
 * An element that responses to key inputs.
 */
public interface IKeyInductive extends IInputReceiver {

	/**
	 * Called when the registered key is pressed down.
	 */
	public void pressed();

	/**
	 * Called when the registered key is not pressed down.
	 */
	public void released();

	/**
	 * Get the key registered to the element.
	 * 
	 * @return Key registered to the element.
	 */
	public int getKey();

	/**
	 * Update input status of the element.
	 */
	@Override
	public default void updateInputs() {
		if (Inputs.keys[getKey()])
			pressed();
		else
			released();
	}

}
