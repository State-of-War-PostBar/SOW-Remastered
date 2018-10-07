package cn.stateofwar.sowr.gui.control;

import org.joml.Vector2d;
import org.joml.Vector4d;

import cn.stateofwar.sowr.core.Inputs;
import cn.stateofwar.sowr.util.DataUtils;

/**
 * An element responses to cursor events.
 */
public interface ICursorInductive extends IInputReceiver {

	/**
	 * Get the area the element would detect the cursor.
	 * 
	 * @return Area the element would detect the cursor.
	 */
	public Vector4d getInductiveArea();

	/**
	 * Called when the mouse cursor is in the inductive area.
	 */
	public void cursorOn();

	/**
	 * Called when the mouse cursor is not in the inductive area.
	 */
	public void cursorOff();

	/**
	 * Update input status of the element.
	 */
	@Override
	public default void updateInputs() {
		if (DataUtils.pir(new Vector2d(Inputs.cursor_x, Inputs.cursor_y), getInductiveArea())) {
			cursorOn();
			return;
		}
		cursorOff();
	}

}
