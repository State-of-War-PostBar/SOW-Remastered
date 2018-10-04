package cn.stateofwar.sowr.gui;

import org.joml.Vector2i;
import org.joml.Vector4f;

import cn.stateofwar.sowr.util.DataUtils;

public interface DkIClickable {

	public void cursorOn();

	public void cursorOut();

	public void clicked();

	public Object getCoordInfo();

	public void registerButtons(int[] buttons);

	public int[] getButtons();

	public default void updateCursor() {
		if (DataUtils.pir(new Vector2i((int) DkSInputs.cursPosX, (int) DkSInputs.cursPosY),
				(Vector4f) getCoordInfo())) {
			cursorOn();
			for (int button : getButtons())
				if (DkSInputs.DK_Mouse[button])
					clicked();
		}
		cursorOut();
	}

}
