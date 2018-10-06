package cn.stateofwar.sowr.gui.control;

import org.joml.Vector2d;

import cn.stateofwar.sowr.core.Inputs;
import cn.stateofwar.sowr.util.DataUtils;

/**
 * An element responses to mouse clicking events
 */
public interface IClickable extends ICursorInductive {

	/**
	 * Called when the element is clicked.
	 */
	public void clicked();

	/**
	 * Called when the element is not clicked.
	 */
	public void released();

	/**
	 * Get the mouse button registered to the element.
	 * 
	 * @return Mouse button registered to the element.
	 */
	public int getButton();

	/**
	 * Update input status of the element.
	 */
	@Override
	public default void updateInputs() {
		if (DataUtils.pir(new Vector2d(Inputs.cursor_x, Inputs.cursor_y), getInductiveArea())) {
			cursorOn();
			if (Inputs.mouse[getButton()]) {
				clicked();
				return;
			} else {
				released();
				return;
			}
		}
		released();
		cursorOff();
	}

}
