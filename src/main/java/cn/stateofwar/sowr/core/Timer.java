package cn.stateofwar.sowr.core;

import org.lwjgl.glfw.GLFW;

/**
 * Timer for the game engine.
 */
public class Timer {

	/** Time when last game loop started. */
	private double time_last_loop;

	/** Time passed since the last game loop. */
	private double time_count;

	/** Frames per second. */
	private int fps;

	/** Counter for FPS. */
	private int fps_count;

	/** Game updates per second. */
	private int ups;

	/** Counter for UPS. */
	private int ups_count;

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
	public double getDelta() {
		double time = getTime();
		double delta = (double) (time - time_last_loop);
		time_last_loop = time;
		time_count += delta;
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
		return time_last_loop;
	}

	/**
	 * Initialize the timer.
	 */
	public void init() {
		time_last_loop = getTime();
	}

	/**
	 * Update the timer.
	 */
	public void update() {
		getDelta();
		if (time_count >= 1.0d) {
			fps = fps_count;
			fps_count = 0;

			ups = ups_count;
			ups_count = 0;

			time_count -= 1.0d;
		}
	}

	/**
	 * Add 1 to FPS.
	 */
	public void updateFPS() {
		fps_count++;
	}

	/**
	 * Add 1 to UPS.
	 */
	public void updateUPS() {
		ups_count++;
	}

}
