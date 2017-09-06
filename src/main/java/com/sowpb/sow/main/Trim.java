package com.sowpb.sow.main;

import org.lwjgl.glfw.GLFWErrorCallback;

import com.sowpb.sow.util.Logger;

import static org.lwjgl.glfw.GLFW.*;

public class Trim {
	
	private static final Logger logger = new Logger("Main");
	
	protected static void initiate() {
		logger.logFile();
		
		if (!glfwInit())
			throw new IllegalStateException("Failed to initialize GLFW.");
		GLFWErrorCallback.createPrint(System.err).set();
		logger.info("Successfully initialized the Lwjgl library.");
	}
	
	protected static void abrogate() {
		glfwTerminate();
		logger.info("Successfully unloaded the Lwjgl library.");
	}

}
