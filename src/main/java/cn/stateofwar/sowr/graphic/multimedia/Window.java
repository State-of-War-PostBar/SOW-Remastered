package cn.stateofwar.sowr.graphic.multimedia;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL45.*;
import static org.lwjgl.system.MemoryUtil.NULL;

import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWFramebufferSizeCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;

import cn.stateofwar.sowr.core.Core;
import cn.stateofwar.sowr.util.Logger;
import cn.stateofwar.sowr.util.Utils;
import cn.stateofwar.sowr.util.Utils.OSType;

/** A game window. */
public class Window {

  private static final Logger LOGGER = new Logger("Render");

  /** Handle for the window. */
  private long handle;

  /** Title of the window. */
  private String title;

  /** Width of the window. */
  private int width;

  /** Height of the window. */
  private int height;

  /** If vertical sync is enabled. */
  private boolean vertical_sync;

  /** If the window captures all display. */
  private boolean full_screen;

  /**
   * Initialize properties of the window.
   *
   * @param _title Title of the window.
   * @param _width Width of the window.
   * @param _height Height of the window.
   * @param _vertical_sync Enable vertical sync.
   * @param _full_screen Enable full screen capture.
   */
  public Window(
      String _title, int _width, int _height, boolean _vertical_sync, boolean _full_screen) {
    title = _title;
    width = _width;
    height = _height;
    vertical_sync = _vertical_sync;
    full_screen = _full_screen;
  }

  /** Initialize the window. */
  public void init() {
    glfwDefaultWindowHints();
    glfwWindowHint(GLFW_VISIBLE, GL_FALSE);
    glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
    glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
    glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 5);
    glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
    if (Utils.getOS() == OSType.MAC) glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);

    handle =
        glfwCreateWindow(width, height, title, full_screen ? glfwGetPrimaryMonitor() : NULL, NULL);

    if (handle == NULL) {
      LOGGER.fatal("Failed to create a GLFW window!");
      throw new RuntimeException("Failed to create a GLFW window!");
    }

    GLFWVidMode video = glfwGetVideoMode(glfwGetPrimaryMonitor());
    glfwSetWindowPos(handle, (video.width() - width) / 2, (video.height() - height) / 2);

    LOGGER.info("Created a GLFW window.");

    glfwSetCursorPosCallback(
        handle,
        GLFWCursorPosCallback.create(
            (window, x, y) -> {
              Core.state.getInputHook().updateCursorPos(x, y);
            }));

    glfwSetKeyCallback(
        handle,
        GLFWKeyCallback.create(
            (window, key, scancode, action, mods) -> {
              Core.state.getInputHook().updateKeys(key, action);
            }));

    glfwSetMouseButtonCallback(
        handle,
        GLFWMouseButtonCallback.create(
            (window, button, action, mods) -> {
              Core.state.getInputHook().updateMouseButton(button, action);
            }));

    glfwSetWindowSizeCallback(
        handle,
        GLFWWindowSizeCallback.create(
            (window, width, height) -> {
              Core.state.getGraphic().window.resize(width, height);
            }));

    glfwSetFramebufferSizeCallback(
        handle,
        GLFWFramebufferSizeCallback.create(
            (window, width, height) -> {
              Core.state.getGraphic().window.resize(width, height);
            }));

    if (vertical_sync) glfwSwapInterval(1);

    glfwMakeContextCurrent(handle);
    GL.createCapabilities();
    glViewport(0, 0, width, height);
    glDepthFunc(GL_LEQUAL);
    glClearDepth(1.0f);

    glClearColor(1.0f, 1.0f, 1.0f, 0.0f);

    glfwShowWindow(handle);
  }

  /** Clear color buffer and depth buffer of the window. */
  public void clear() {
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
  }

  /** Update drawn elements to the window. */
  public void update() {
    glfwSwapBuffers(handle);
    glfwPollEvents();
  }

  /**
   * Get the handle of the window.
   *
   * @return Handle of the window.
   */
  public long getHandle() {
    return handle;
  }

  /**
   * Get the width of the window.
   *
   * @return Width of the window.
   */
  public int getWidth() {
    return width;
  }

  /**
   * Set the width of the window.
   *
   * @param _width New width of the window.
   */
  public void setWidth(int _width) {
    resize(_width, height);
  }

  /**
   * Get the height of the window.
   *
   * @return Height of the window.
   */
  public int getHeight() {
    return height;
  }

  /**
   * Set the height of the window.
   *
   * @param _height New height of the window.
   */
  public void setHeight(int _height) {
    resize(width, _height);
  }

  /**
   * Set the size of the window.
   *
   * @param _width New width of the window.
   * @param _height New height of the window.
   */
  public void resize(int _width, int _height) {
    width = _width;
    height = _height;
    glViewport(0, 0, width, height);
  }

  /**
   * Check if the window is set to be closing.
   *
   * @return If the window is closing.
   */
  public boolean isClosing() {
    return glfwWindowShouldClose(handle);
  }

  /** Close the window. */
  public void close() {
    glfwSetWindowShouldClose(handle, true);
  }

  /**
   * Check if the window is vertical sync.
   *
   * @return If vertical sync is enabled.
   */
  public boolean isVerticalSync() {
    return vertical_sync;
  }

  /**
   * Set the vertical sync of the window.
   *
   * @param _vertical_sync If vertical sync is enabled.
   */
  public void setVerticalSync(boolean _vertical_sync) {
    vertical_sync = _vertical_sync;
    if (_vertical_sync) glfwSwapInterval(1);
    else glfwSwapInterval(0);
  }

  /** Clean up the window. */
  public void abrogate() {
    Callbacks.glfwFreeCallbacks(handle);
    glfwDestroyWindow(handle);
  }

}
