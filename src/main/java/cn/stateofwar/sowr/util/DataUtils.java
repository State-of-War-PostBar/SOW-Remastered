package cn.stateofwar.sowr.util;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.joml.Vector2f;
import org.lwjgl.BufferUtils;

import cn.stateofwar.sowr.core.Core;
import cn.stateofwar.sowr.graphic.render.Texture2D;

public class DataUtils {

	public static ByteBuffer createByteBuffer(byte[] data) {
		return (ByteBuffer) BufferUtils.createByteBuffer(data.length).put(data);
	}

	public static FloatBuffer createFloatBuffer(float[] data) {
		return (FloatBuffer) BufferUtils.createFloatBuffer(data.length).put(data);
	}

	public static DoubleBuffer createDoubleBuffer(double[] data) {
		return (DoubleBuffer) BufferUtils.createDoubleBuffer(data.length).put(data);
	}

	public static IntBuffer createIntBuffer(int[] data) {
		return (IntBuffer) BufferUtils.createIntBuffer(data.length).put(data);
	}

	public static Vector2f toScreenPercentage(int x, int y) {
		float x_percentage = (float) x / (float) Core.state.getWindow().getWidth();
		float y_percentage = (float) y / (float) Core.state.getWindow().getHeight();

		return new Vector2f(x_percentage, y_percentage);
	}

	public static Vector2f toGLCoord(int x, int y) {
		int w = Core.state.getWindow().getWidth();
		int h = Core.state.getWindow().getHeight();

		x = x > w ? w : x;
		y = y > h ? h : y;

		Vector2f vec = toScreenPercentage(x, y);
		return vec.set(vec.x * 2.0f - 1.0f, vec.y * 2.0f - 1.0f);
	}

	public static Vector2f to2DTexCoord(int s, int t, Texture2D texture) {
		int w = texture.getWidth();
		int h = texture.getHeight();

		s = s > w ? w : s;
		t = t > h ? h : t;

		float s_percentage = (float) s / (float) w;
		float t_percentage = (float) t / (float) h;

		return new Vector2f(s_percentage, t_percentage);
	}

}
