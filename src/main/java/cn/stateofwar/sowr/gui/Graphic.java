package cn.stateofwar.sowr.gui;

import static cn.stateofwar.sowr.util.Utils.nl;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import cn.stateofwar.sowr.References;
import cn.stateofwar.sowr.gui.multimedia.Window;
import cn.stateofwar.sowr.util.Config;
import cn.stateofwar.sowr.util.Logger;

public class Graphic {

	private static final Logger logger = new Logger("Render");

	public static int desiredFPS;

	public static Window win;

	public static void initRenderCapabilities() {
		win = new Window(References.PROGRAM_NAME, Integer.parseInt(Config.get("GUI", "Window Width")),
				Integer.parseInt(Config.get("GUI", "Window Height")),
				Boolean.parseBoolean(Config.get("GUI", "Vertical Sync")),
				Boolean.parseBoolean(Config.get("GUI", "Full Screen")));
		win.initWindow();

		desiredFPS = win.isVSync() ? References.VERTICAL_SYNC_FPS : Integer.parseInt(Config.get("GUI", "Max FPS"));

		StringBuilder s = new StringBuilder();
		s.append("OpenGL information: ").append(nl());
		s.append("OpenGL Vendor: ").append(glGetString(GL_VENDOR)).append(nl());
		s.append("OpenGL Renderer: ").append(glGetString(GL_RENDERER)).append(nl());
		s.append("OpenGL Version: ").append(glGetString(GL_VERSION)).append(nl());
		s.append("GLSL Version: ").append(glGetString(GL_SHADING_LANGUAGE_VERSION));
		logger.info(s.toString());

		logger.info("Initialized rendering capabilities.");
	}

	public static void abrogateRender() {
		win.abrogate();
	}

}
