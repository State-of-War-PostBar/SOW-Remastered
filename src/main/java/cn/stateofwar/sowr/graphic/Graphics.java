package cn.stateofwar.sowr.graphic;

import static org.lwjgl.opengl.GL45.*;
import static org.lwjgl.glfw.GLFW.*;
import static cn.stateofwar.sowr.util.Utils.nl;

import cn.stateofwar.sowr.References;
import cn.stateofwar.sowr.graphic.multimedia.Window;
import cn.stateofwar.sowr.util.Config;
import cn.stateofwar.sowr.util.I18n;
import cn.stateofwar.sowr.util.Logger;

public class Graphics {

	private static final Logger LOGGER = new Logger("Render");

	public int max_fps;

	public Window window;

	public void initRenderCapabilities() {

		if (!glfwInit()) {
			LOGGER.fatal("Cannot initialize GLFW!");
			throw new IllegalStateException("Failed to initialize GLFW.");
		}

		window = new Window(I18n.t(References.PROGRAM_NAME), Integer.parseInt(Config.get("GUI", "Window Width")),
				Integer.parseInt(Config.get("GUI", "Window Height")),
				Boolean.parseBoolean(Config.get("GUI", "Vertical Sync")),
				Boolean.parseBoolean(Config.get("GUI", "Full Screen")));

		max_fps = window.isVerticalSync() ? References.VERTICAL_SYNC_FPS
				: Integer.parseInt(Config.get("GUI", "Max FPS"));

		StringBuilder s = new StringBuilder();
		s.append("Graphic Adaptor information: ").append(nl());
		s.append("GPU Vendor: ").append(glGetString(GL_VENDOR)).append(nl());
		s.append("GPU Renderer: ").append(glGetString(GL_RENDERER)).append(nl());
		s.append("OpenGL Version: ").append(glGetString(GL_VERSION)).append(nl());
		s.append("GLSL Version: ").append(glGetString(GL_SHADING_LANGUAGE_VERSION));
		LOGGER.info(s.toString());

		LOGGER.info("Initialized rendering capabilities.");
	}

	public void abrogate() {
		glfwTerminate();
		window.abrogate();
	}

}
