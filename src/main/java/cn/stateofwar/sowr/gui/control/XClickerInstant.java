package cn.stateofwar.sowr.gui.control;

import org.joml.Vector4d;

/**
 * An area responses to mouse button inputs only for when it's just pressed.
 */
public abstract class XClickerInstant extends XClickerHold {

	/** Mark to check if the mouse button is being hold down. */
	private boolean has_clicked;

	public XClickerInstant(String _identifier, Vector4d coord, int mouse_button) {
		super(_identifier, coord, mouse_button);
	}

	/**
	 * Called when the element is clicked.
	 */
	public abstract void clickedX();

	/**
	 * Called when the element is not clicked.
	 */
	public abstract void releasedX();

	/**
	 * Check if the button is being hold.
	 */
	@Override
	public void clicked() {
		if (has_clicked)
			return;
		has_clicked = true;
		clickedX();
	}

	/**
	 * Check if the button is not being hold.
	 */
	@Override
	public void released() {
		has_clicked = false;
		releasedX();
	}

}
