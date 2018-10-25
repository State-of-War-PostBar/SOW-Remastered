package cn.stateofwar.sowr.core;

import org.lwjgl.glfw.GLFW;

/**
 * Central high precision for the game engine.
 */
public class Timer {

	/** The time when last update loop ended. */
	private double last_loop_time;

	/** Time counter for 1 second. */
	private double time_count;

	/** Current frames per second. */
	private int fps;

	/** Counter for frames per second. */
	private int fps_counter;

	/**
	 * Initialize the timer.
	 */
	public void init() {
		last_loop_time = getTime();
	}

	/**
	 * Get the current time passed.
	 * 
	 * @return The current time passed.
	 */
	public double getTime() {
		return GLFW.glfwGetTime();
	}

	/**
	 * Get the time when last loop ended.
	 * 
	 * @return Time when last loop ended.
	 */
	public double getLastLoopTime() {
		return last_loop_time;
	}

	/**
	 * Update the timer.
	 */
	public void update() {
		double time = getTime();
		double delta = (double) (time - last_loop_time);
		last_loop_time = time;
		time_count += delta;
		if (time_count >= 1.0d) {
			fps = fps_counter;
			fps_counter = 0;

			time_count -= 1.0d;
		}
	}

	/**
	 * Update FPS counter.
	 */
	public void updateFPS() {
		fps_counter++;
	}

	/**
	 * Get current FPS.
	 * 
	 * @return Current FPS. If time passed is not long enough, FPS counter is
	 *         returned.
	 */
	public int getFPS() {
		return fps == 0 ? fps_counter : fps;
	}

}
