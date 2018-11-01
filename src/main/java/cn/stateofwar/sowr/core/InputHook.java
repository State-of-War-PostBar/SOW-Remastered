package cn.stateofwar.sowr.core;

import static org.lwjgl.glfw.GLFW.*;

/** A storage for user inputs data. */
public class InputHook {

  /** Current X position of the cursor. */
  public float cursor_x;

  /** Current Y position of the cursor. */
  public float cursor_y;

  /** Inputs of keyboard. Implement the keys from GLFW directly. */
  public boolean[] keys = new boolean[365];

  /** Inputs of mouse. Implement the buttons from GLFW directly. */
  public boolean[] mouse_buttons = new boolean[11];

  /** Interval between two clicks in a double click event. */
  public double double_click_interval;

  /**
   * Update current cursor position.
   *
   * @param x The X position.
   * @param y The y position.
   */
  public void updateCursorPos(double x, double y) {
    cursor_x = (float) x;
    cursor_y = (float) (Core.state.getWindow().getHeight() - y);
  }

  /**
   * Update keyboard.
   *
   * @param key The key updating.
   * @param action Action of the key.
   */
  public void updateKeys(int key, int action) {
    if (action == GLFW_PRESS) keys[key] = true;
    if (action == GLFW_RELEASE) keys[key] = false;
  }

  /**
   * Update mouse buttons.
   *
   * @param key The button updating.
   * @param action Action of the button.
   */
  public void updateMouseButton(int button, int action) {
    if (action == GLFW_PRESS) mouse_buttons[button] = true;
    if (action == GLFW_RELEASE) mouse_buttons[button] = false;
  }

}
