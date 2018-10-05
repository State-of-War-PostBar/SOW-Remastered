package cn.stateofwar.sowr.gui.base;

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

}
