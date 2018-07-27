package cn.stateofwar.sowr.core;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

/**
 * The timer for FPS and UPS calculating and updating.
 */
public class Timer {

	/**
	 * The time when last game update happened.
	 */
	private double lastLoopTime;

	/**
	 * Counter for time.
	 */
	private float timeCount;

	private int fps, fpsCount;

	private int ups, upsCount;

	public void init() {
		lastLoopTime = getTime();
	}

	/**
	 * Get tge current time.
	 */
	public double getTime() {
		return glfwGetTime();
	}

	/**
	 * Get the time has passed since last game update.
	 */
	public float getDelta() {
		double time = getTime();
		float delta = (float) (time - lastLoopTime);
		lastLoopTime = time;
		timeCount += delta;
		return delta;
	}

	/**
	 * Check if one second has passed and update fps and ups if so.
	 */
	public void update() {
		if (timeCount > 1f) {
			fps = fpsCount;
			fpsCount = 0;

			ups = upsCount;
			upsCount = 0;

			timeCount -= 1f;
		}
	}

	public void updateFPS() {
		fpsCount++;
	}

	public void updateUPS() {
		upsCount++;
	}

	public int getFPS() {
		return fps > 0 ? fps : fpsCount;
	}

	public int getUPS() {
		return ups > 0 ? ups : upsCount;
	}

	public double getLastLoopTime() {
		return lastLoopTime;
	}

}
