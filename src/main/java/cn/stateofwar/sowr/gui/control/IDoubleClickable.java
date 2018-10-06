package cn.stateofwar.sowr.gui.control;

/**
 * An element responses to double clicking.
 */
public interface IDoubleClickable extends IClickable {

	/**
	 * Called when the element is double clicked.
	 */
	public void doubleClicked();

	/**
	 * Call when the element is somehow not double clicked.
	 */
	public void clickedX();

	/**
	 * Check if the clicking event is double click.
	 */
	public boolean isDoubleClick();

	@Override
	public default void clicked() {
		if (isDoubleClick())
			doubleClicked();
		else
			clickedX();
	}
}
