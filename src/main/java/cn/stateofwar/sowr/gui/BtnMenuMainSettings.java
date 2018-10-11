package cn.stateofwar.sowr.gui;

import org.joml.Vector4d;

import cn.stateofwar.sowr.gui.control.XClickerInstant;

/**
 * Button to switch to settings menu.
 */
public class BtnMenuMainSettings extends XClickerInstant {

	public BtnMenuMainSettings(String _identifier, Vector4d coord, int mouse_button) {
		super(_identifier, coord, mouse_button);
	}

	/**
	 * Called when the element is clicked.
	 */
	@Override
	public void clickedX() {
		Menu.deActivateMenu(getParent().getIdentifier());
		Menu.activateMenu("menu.settings");
	}

	/**
	 * Called when the element is not clicked.
	 */
	@Override
	public void releasedX() {
	}

	@Override
	public void onParentResize(Object size) {
		resize(size);
		super.resize(size);
	}

}
