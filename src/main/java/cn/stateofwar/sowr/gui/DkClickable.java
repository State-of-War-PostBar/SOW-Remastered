package cn.stateofwar.sowr.gui;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2i;
import org.joml.Vector4f;

import cn.stateofwar.sowr.util.DataUtils;

/**
 * An element that responses to cursor and mouse clicking events.
 */
public abstract class DkClickable extends DkXElement {

	/** Coordinate of the element. */
	private Vector4f coord;

	/** Mouse buttons that can click on the button. */
	private List<Integer> buttons = new ArrayList<>();

	public DkClickable(String _identifier, Vector4f _coord) {
		identifier = _identifier;
		coord = _coord;
	}

	/**
	 * Get the coordinate information of the element.
	 * 
	 * @return Coordinate information.
	 */
	public Object getCoordInfo() {
		return coord;
	}

	/**
	 * Register a mouse button to the button.
	 */
	public void registerButton(int[] _buttons) {
		for (int button : _buttons)
			buttons.add(button);
	}

	/**
	 * Update the element and all of its children.
	 */
	public void update() {
		if (DataUtils.pir(new Vector2i((int) DkSInputs.cursPosX, (int) DkSInputs.cursPosY), coord)) {
			cursorOn();
			for (int button : buttons)
				if (DkSInputs.DK_Mouse[button])
					click();
		}
		cursorOut();
		super.update();
	}

	/**
	 * Called when the button is clicked (pressed down by the mouse button).
	 */
	public abstract void click();

	/**
	 * Called when the cursor is on the button.
	 */
	public abstract void cursorOn();

	/**
	 * Called when the cursor is not on the button.
	 */
	public abstract void cursorOut();

}
