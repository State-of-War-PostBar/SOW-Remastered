package cn.stateofwar.sowr.gui.control;

/**
 * A key sensor that only activates when the key is just pressed.
 */
public abstract class XKeySensorInstant extends XKeySensor {

	/** A mark to check if the key is holding. */
	private boolean has_pressed;

	public XKeySensorInstant(String _identifier, int _key) {
		super(_identifier, _key);
	}

	/**
	 * Called when the key is pressed.
	 */
	public abstract void pressedX();

	/**
	 * Called when the key is not pressed.
	 */
	public abstract void releasedX();

	/**
	 * Check if the key is being hold.
	 */
	@Override
	public void pressed() {
		if (has_pressed)
			return;
		has_pressed = true;
		pressedX();
	}

	/**
	 * Check if the key is not being hold.
	 */
	@Override
	public void released() {
		has_pressed = false;
		releasedX();
	}

}
