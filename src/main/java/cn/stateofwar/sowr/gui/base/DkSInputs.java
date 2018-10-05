package cn.stateofwar.sowr.gui.base;

import cn.stateofwar.sowr.util.Config;

/**
 * Input manager for DKUI.
 */
public class DkSInputs {

	/** QWER keyboard keys; implement them from GLFW directly. */
	public static boolean[] DK_Keys = new boolean[350];

	/** Mouse buttons; implement them from GLFW directly. */
	public static boolean[] DK_Mouse = new boolean[11];

	/** Cursor position. */
	public static double cursPosX, cursPosY;

	/** Interval between double clicking. */
	public static double double_click_interval = Double.parseDouble(Config.get("Control", "Double Click Interval"));

}
