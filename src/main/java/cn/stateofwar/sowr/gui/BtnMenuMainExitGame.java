package cn.stateofwar.sowr.gui;

import org.joml.Vector4d;

import cn.stateofwar.sowr.core.Game;
import cn.stateofwar.sowr.gui.control.XButtonInstant;

public class BtnMenuMainExitGame extends XButtonInstant {

	public BtnMenuMainExitGame(String _identifier, Vector4d coord, int mouse_button) {
		super(_identifier, coord, mouse_button);
	}

	@Override
	public void clickedX() {
		Game.running = false;
	}

	@Override
	public void releasedX() {
	}

	@Override
	public void onParentResize(Object size) {
		resize(size);
		super.resize(size);
	}

}
