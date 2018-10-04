package cn.stateofwar.sowr.gui;

import org.joml.Vector2i;
import org.joml.Vector4f;

import cn.stateofwar.sowr.util.DataUtils;

/**
 * An element that responses to mouse clicking and cursor.
 */
public interface DkIClickable {

	/**
	 * Called when the cursor is on the element.
	 */
	public void cursorOn();

	/**
	 * Called when the cursor is not on the element.
	 */
	public void cursorOut();

	/**
	 * Called when the cursor is clicked (mouse button pressed down).
	 */
	public void clicked();

	/**
	 * /** Get the coordinate information of the element. <i>This method is only a
	 * reference for the interface to get coordinate.</i>
	 * 
	 * @return Coordinate information.
	 */
	public Object getCoordInfo();

	/**
	 * Register buttons to the element.
	 */
	public void registerButtons(int[] buttons);

	/**
	 * Get the buttons that are registered to the element.
	 */
	public int[] getButtons();

	/**
	 * Update cursor events.
	 */
	public default void updateCursor() {
		if (DataUtils.pir(new Vector2i((int) DkSInputs.cursPosX, (int) DkSInputs.cursPosY),
				(Vector4f) getCoordInfo())) {
			cursorOn();
			for (int button : getButtons())
				if (DkSInputs.DK_Mouse[button]) {
					clicked();
					break;
				}
		}
		cursorOut();
	}

}
