package cn.stateofwar.sowr.gui.render;

import static cn.stateofwar.sowr.util.Utils.nl;
import static org.lwjgl.opengl.GL45.*;

import cn.stateofwar.sowr.References;
import cn.stateofwar.sowr.gui.render.multimedia.Window;
import cn.stateofwar.sowr.gui.render.ogl.Shaders;
import cn.stateofwar.sowr.util.Config;
import cn.stateofwar.sowr.util.I18n;
import cn.stateofwar.sowr.util.Logger;

/**
 * Graphic capabilities manager of the game engine.
 */
public class Graphic {

	private static final Logger LOGGER = new Logger("Render");

	/** Maximum FPS the program wants to reach. */
	public static int max_fps;

	/** Window of the game engine. */
	public static Window window;

	/**
	 * Register the window and establish render capabilities.
	 */
	public static void initRenderCapabilities() {
		window = new Window(I18n.t(References.PROGRAM_NAME), Integer.parseInt(Config.get("GUI", "Window Width")),
				Integer.parseInt(Config.get("GUI", "Window Height")),
				Boolean.parseBoolean(Config.get("GUI", "Vertical Sync")),
				Boolean.parseBoolean(Config.get("GUI", "Full Screen")));
		window.initWindow();

		max_fps = window.isVSync() ? References.VERTICAL_SYNC_FPS : Integer.parseInt(Config.get("GUI", "Max FPS"));

		StringBuilder s = new StringBuilder();
		s.append("Graphic Adaptor information: ").append(nl());
		s.append("GPU Vendor: ").append(glGetString(GL_VENDOR)).append(nl());
		s.append("GPU Renderer: ").append(glGetString(GL_RENDERER)).append(nl());
		s.append("OpenGL Version: ").append(glGetString(GL_VERSION)).append(nl());
		s.append("GLSL Version: ").append(glGetString(GL_SHADING_LANGUAGE_VERSION));
		LOGGER.info(s.toString());

		Shaders.init();

		LOGGER.info("Initialized rendering capabilities.");
	}

	/**
	 * Unload the graphic capabilities.
	 */
	public static void abrogateRender() {
		Shaders.abrogate();
		window.abrogate();
	}

}
