package cn.stateofwar.sowr.gui.base;

import org.joml.Vector2i;
import org.joml.Vector4f;

import cn.stateofwar.sowr.util.DataUtils;

/**
 * An element that responds to mouse cursor.
 */
public interface DkICursorSensitive {

	/**
	 * Called when the cursor is on the element.
	 */
	public void cursorOn();

	/**
	 * Called when the cursor is not on the element.
	 */
	public void cursorOut();

	/**
	 * Check if the cursor is on the element.
	 * 
	 * @return If the cursor is on the element.
	 */
	public boolean isCursorOn();

	/**
	 * /** Get the coordinate information of the element.<br />
	 * <i>This method is only a reference for the interface to get coordinate.</i>
	 * 
	 * @return Coordinate information.
	 */
	public Object getCoordInfo();

	/**
	 * Update cursor events.
	 */
	public default void updateCursor() {
		if (DataUtils.pir(new Vector2i((int) DkSInputs.cursor_x, (int) DkSInputs.cursor_y), (Vector4f) getCoordInfo()))
			cursorOn();
		else
			cursorOut();
	}

}
