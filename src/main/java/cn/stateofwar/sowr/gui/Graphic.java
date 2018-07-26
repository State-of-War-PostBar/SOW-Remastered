package cn.stateofwar.sowr.gui;

import static cn.stateofwar.sowr.util.Utils.nl;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import org.lwjgl.glfw.Callbacks;

import cn.stateofwar.sowr.References;
import cn.stateofwar.sowr.gui.multimedia.Window;
import cn.stateofwar.sowr.util.Config;
import cn.stateofwar.sowr.util.Logger;

/**
 * Manager of the current graphic(window).
 */
public class Graphic {

	public static final Logger logger = new Logger("Render");

	/**
	 * The steady FPS the program attemps to achieve.
	 */
	public static int desiredFPS;

	/**
	 * Window of the program.
	 */
	public static Window win;

	/**
	 * Initialize capabilities for rendering, such as window and OpenGL contexts.
	 */
	public static void initRenderCapabilities() {
		win = new Window(References.PROGRAM_NAME, Integer.parseInt(Config.get("GUI", "Window Width")),
				Integer.parseInt(Config.get("GUI", "Window Height")),
				Boolean.parseBoolean(Config.get("GUI", "Vertical Sync")),
				Boolean.parseBoolean(Config.get("GUI", "Full Screen")));
		win.initWindow();

		desiredFPS = win.isVSync() ? Integer.parseInt(Config.get("GUI", "Max FPS")) : References.VERTICAL_SYNC_FPS;

		/* Record OpenGL and GPU information of the user. */
		StringBuilder s = new StringBuilder();
		s.append("OpenGL information: ").append(nl());
		s.append("OpenGL Vendor: ").append(glGetString(GL_VENDOR)).append(nl());
		s.append("OpenGL Renderer: ").append(glGetString(GL_RENDERER)).append(nl());
		s.append("OpenGL Version: ").append(glGetString(GL_VERSION)).append(nl());
		s.append("GLSL Version: ").append(glGetString(GL_SHADING_LANGUAGE_VERSION));
		logger.info(s.toString());

		logger.info("Successfully initialized rendering capabilities.");
	}

	/**
	 * Finalize and clear up rendering process.
	 */
	public static void abrogateRender() {
		glfwDestroyWindow(win.getHandle());
		Callbacks.glfwFreeCallbacks(win.getHandle());
	}

}
