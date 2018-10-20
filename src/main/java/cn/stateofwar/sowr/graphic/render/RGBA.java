package cn.stateofwar.sowr.graphic.render;

import java.nio.FloatBuffer;

import cn.stateofwar.sowr.util.DataUtils;

public class RGBA {

	public static final RGBA BLACK = new RGBA(0.0f, 0.0f, 0.0f);

	public static final RGBA RED = new RGBA(1.0f, 0.0f, 0.0f);

	public static final RGBA GREEN = new RGBA(0.0f, 1.0f, 0.0f);

	public static final RGBA BLUE = new RGBA(0.0f, 0.0f, 1.0f);

	public static final RGBA WHITE = new RGBA(1.0f, 1.0f, 1.0f);

	private float r, g, b, a;

	public RGBA() {
		this(0.0f, 0.0f, 0.0f);
	}

	public RGBA(float r, float g, float b) {
		setR(r);
		setG(g);
		setB(b);
		setA(1.0f);
	}

	public RGBA(float r, float g, float b, float a) {
		setR(r);
		setG(g);
		setB(b);
		setA(a);
	}

	public float getR() {
		return r;
	}

	public float getG() {
		return g;
	}

	public float getB() {
		return b;
	}

	public float getA() {
		return a;
	}

	public void setR(float _r) {
		if (_r < 0.0f)
			_r = 0.0f;

		if (_r > 1.0f)
			_r = 1.0f;

		r = _r;
	}

	public void setG(float _g) {
		if (_g < 0.0f)
			_g = 0.0f;

		if (_g > 1.0f)
			_g = 1.0f;

		g = _g;
	}

	public void setB(float _b) {
		if (_b < 0.0f)
			_b = 0.0f;

		if (_b > 1.0f)
			_b = 1.0f;

		b = _b;
	}

	public void setA(float _a) {
		if (_a < 0.0f)
			_a = 0.0f;

		if (_a > 1.0f)
			_a = 1.0f;

		a = _a;
	}

	public FloatBuffer toFloatBuffer() {
		return DataUtils.createFloatBuffer(new float[] { r, g, b, a });
	}

}
