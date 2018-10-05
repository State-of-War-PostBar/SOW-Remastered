package cn.stateofwar.sowr.gui.base;

/**
 * An element that responses to double clicking events.
 */
public interface DkIDoubleClickable extends DkIClickable {

	/**
	 * Called when the element is double clicked.
	 */
	public void doubleClicked();

	/**
	 * Called when the element is first clicked.
	 */
	public void clickedX();

	/**
	 * Called when the element is released.
	 */
	public void releasedX();

	/**
	 * Check if the element has a double click interval mark.
	 * 
	 * @return If the element has a double click interval mark.
	 */
	public boolean hasClickMark();

	/**
	 * Set the double click interval mark.
	 */
	public void setClickMark();

	/**
	 * Remove the double click interval mark.
	 */
	public void rmClickMark();

	/**
	 * Start the double click timer.
	 */
	public void startClickTimer();

	/**
	 * Update the double click timer.
	 */
	public void updateClickTimer();

	/**
	 * Get the time passed after one click.
	 * 
	 * @return Time passed after the first click.
	 */
	public double getClickInterval();

	@Override
	public default void clicked() {
		if (hasClickMark()) {
			rmClickMark();
			doubleClicked();
			/* Proceed double click event, remove timer and double click mark. */
		} else {
			setClickMark();
			startClickTimer();
			clickedX();
			/*
			 * First clicked, setup double click timer and mark and proceed first click
			 * event.
			 */
		}
	}

	@Override
	public default void released() {
		if (hasClickMark()) {
			updateClickTimer();
			if (getClickInterval() > DkSInputs.double_click_interval)
				rmClickMark();
			/* Double click mark is expired, remove it. */
		}
		releasedX();
	}
}
