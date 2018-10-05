package cn.stateofwar.sowr.core;

import org.lwjgl.glfw.GLFW;

/**
 * Timer for the game engine.
 */
public class Timer {

	/** Time when last game loop started. */
	private double lastLoopTime;

	/** Time passed since the last game loop. */
	private float timeCount;

	/** Frames per second. */
	private int fps;

	/** Counter for FPS. */
	private int fpsCount;

	/** Game updates per second. */
	private int ups;

	/** Counter for UPS. */
	private int upsCount;

	/**
	 * Get the current time of GLFW timer.
	 * 
	 * @return Current time.
	 */
	public double getTime() {
		return GLFW.glfwGetTime();
	}

	/**
	 * Calculate the total time passed since the last game loop.
	 * 
	 * @return Time passed.
	 */
	public float getDelta() {
		double time = getTime();
		float delta = (float) (time - lastLoopTime);
		lastLoopTime = time;
		timeCount += delta;
		return delta;
	}

	/**
	 * Get the current FPS. If time passed is less then 1 second it will return 0.
	 * 
	 * @return Current FPS.
	 */
	public int getFPS() {
		return fps;
	}

	/**
	 * Get the current UPS. If time passed is less then 1 second it will return 0.
	 * 
	 * @return Current UPS.
	 */
	public int getUPS() {
		return ups;
	}

	/**
	 * Get the time when last loop started.
	 * 
	 * @return Time when last loop started.
	 */
	public double getLastLoopTime() {
		return lastLoopTime;
	}

	/**
	 * Initialize the timer.
	 */
	public void init() {
		lastLoopTime = getTime();
	}

	/**
	 * Update the timer.
	 */
	public void update() {
		getDelta();
		if (timeCount >= 1.0f) {
			fps = fpsCount;
			fpsCount = 0;

			ups = upsCount;
			upsCount = 0;

			timeCount -= 1.0f;
		}
	}

	/**
	 * Add 1 to FPS.
	 */
	public void updateFPS() {
		fpsCount++;
	}

	/**
	 * Add 1 to UPS.
	 */
	public void updateUPS() {
		upsCount++;
	}

}
