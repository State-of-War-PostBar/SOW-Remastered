package cn.stateofwar.sowr.gui.base;

/**
 * An element that responses to mouse clicking.
 */
public interface DkIClickable extends DkICursorSensitive {

	/**
	 * Called when the mouse button is clicked.
	 */
	public void clicked();

	/**
	 * Called when the mouse button is not clicked.
	 */
	public void released();

	/**
	 * Register buttons to the element.
	 */
	public void registerButtons(int[] buttons);

	/**
	 * Get the buttons that are registered to the element.
	 */
	public int[] getButtons();

	/**
	 * Update mouse button status.
	 */
	public default void updateButton() {
		if (isCursorOn())
			for (int button : getButtons())
				if (DkSInputs.DK_mouse[button]) {
					clicked();
					return;
				}

		released();
	}

}
