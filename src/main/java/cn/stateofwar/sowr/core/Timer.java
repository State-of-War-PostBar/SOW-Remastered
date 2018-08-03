package cn.stateofwar.sowr.core;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

public class Timer {

	private double lastLoopTime;

	private float timeCount;

	private int fps, fpsCount;

	private int ups, upsCount;

	public void init() {
		lastLoopTime = getTime();
	}

	public double getTime() {
		return glfwGetTime();
	}

	public float getDelta() {
		double time = getTime();
		float delta = (float) (time - lastLoopTime);
		lastLoopTime = time;
		timeCount += delta;
		return delta;
	}

	public void update() {
		getDelta();
		if (timeCount >= 1.0f) {
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
