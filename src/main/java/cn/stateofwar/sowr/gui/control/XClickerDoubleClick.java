package cn.stateofwar.sowr.gui.control;

import org.joml.Vector4d;

import cn.stateofwar.sowr.core.Game;
import cn.stateofwar.sowr.core.Inputs;

/**
 * An area responses to mouse button inputs only for when it's double clicked.
 */
public abstract class XClickerDoubleClick extends XClickerInstant {

	/** Mark to check if it's between two clicking. */
	private boolean click_mark;

	/** A timer to record the time of first clicking. */
	private double click_time;

	/** A timer to check if the click mark is expired. */
	private double click_timer;

	public XClickerDoubleClick(String _identifier, Vector4d coord, int _mouse_button) {
		super(_identifier, coord, _mouse_button);
	}

	/**
	 * Called when the element is double clicked.
	 */
	public abstract void doubleClicked();

	/**
	 * Called when the element is clicked for first time.
	 */
	public abstract void firstClicked();

	/**
	 * Called when the element is not clicked.
	 */
	public abstract void releasedY();

	/**
	 * Check if it's double click.
	 */
	@Override
	public void clickedX() {
		if (click_mark) {
			doubleClicked();
			click_mark = false;
		} else {
			firstClicked();
			click_mark = true;
			click_time = Game.timer.getTime();
		}
	}

	/**
	 * Check if the double click mark is expired.
	 */
	@Override
	public void releasedX() {
		if (click_mark) {
			click_timer = Game.timer.getTime();
			if (click_timer - click_time > Inputs.double_click_interval)
				click_mark = false;
		}
		releasedY();
	}

}
