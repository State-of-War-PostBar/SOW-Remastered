package cn.stateofwar.sowr.gui;

import org.joml.Vector4d;

import cn.stateofwar.sowr.core.Game;
import cn.stateofwar.sowr.gui.control.ClickerInstant;

/**
 * Button to exit the game.
 */
public class BtnMenuMainExitGame extends ClickerInstant {

	public BtnMenuMainExitGame(String _identifier, Vector4d coord, int mouse_button) {
		super(_identifier, coord, mouse_button);
	}

	/**
	 * Called when the element is clicked.
	 */
	@Override
	public void clickedX() {
		Game.running = false;
	}

	/**
	 * Called when the element is not clicked.
	 */
	@Override
	public void releasedX() {
	}

	@Override
	public void onParentResize(Object size) {
		super.resize(size);
	}

}
