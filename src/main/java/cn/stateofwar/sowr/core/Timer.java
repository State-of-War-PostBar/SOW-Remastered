package cn.stateofwar.sowr.core;

import org.lwjgl.glfw.GLFW;

public class Timer {

	private double last_loop_time;

	private double time_count;

	public void init() {
		last_loop_time = getTime();
	}

	public double getTime() {
		return GLFW.glfwGetTime();
	}

	public double getDelta() {
		double time = getTime();
		double delta = (double) (time - last_loop_time);
		last_loop_time = time;
		time_count += delta;
		return delta;
	}

	public double getLastLoopTime() {
		return last_loop_time;
	}

	public void update() {
		getDelta();
		if (time_count >= 1.0d)
			time_count -= 1.0d;
	}

}
