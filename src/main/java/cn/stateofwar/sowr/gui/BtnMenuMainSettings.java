package cn.stateofwar.sowr.gui;

import org.joml.Vector4d;

import cn.stateofwar.sowr.gui.control.XButtonInstant;

public class BtnMenuMainSettings extends XButtonInstant {

	public BtnMenuMainSettings(String _identifier, Vector4d coord, int mouse_button) {
		super(_identifier, coord, mouse_button);
	}

	@Override
	public void clickedX() {
		// TODO

	}

	@Override
	public void releasedX() {
		// TODO
	}

	@Override
	public void onParentResize(Object size) {
		resize(size);
		super.resize(size);
	}

}
