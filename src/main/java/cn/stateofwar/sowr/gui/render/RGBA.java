package cn.stateofwar.sowr.gui.render;

import org.joml.Vector3f;
import org.joml.Vector4f;

public class RGBA {

	public static final RGBA WHITE = new RGBA(1.0f, 1.0f, 1.0f);
	public static final RGBA BLACK = new RGBA(0.0f, 0.0f, 0.0f);
	public static final RGBA RED = new RGBA(1.0f, 0.0f, 0.0f);
	public static final RGBA GREEN = new RGBA(0.0f, 1.0f, 0.0f);
	public static final RGBA BLUE = new RGBA(0.0f, 0.0f, 1.0f);

	private float r, g, b, a;

	public RGBA() {
		this(0.0f, 0.0f, 0.0f);
	}

	public RGBA(float r, float g, float b) {
		setRed(r);
		setGreen(g);
		setBlue(b);
		setAlpha(1.0f);
	}

	public RGBA(float r, float g, float b, float a) {
		setRed(r);
		setGreen(g);
		setBlue(b);
		setAlpha(a);
	}

	public float getRed() {
		return r;
	}

	public void setRed(float _r) {
		if (_r < 0.0f)
			_r = 0.0f;

		if (_r > 1.0f)
			_r = 1.0f;

		this.r = _r;
	}

	public float getGreen() {
		return g;
	}

	public void setGreen(float _g) {
		if (_g < 0.0f)
			_g = 0.0f;

		if (_g > 1.0f)
			_g = 1.0f;

		g = _g;
	}

	public float getBlue() {
		return b;
	}

	public void setBlue(float _b) {
		if (_b < 0.0f)
			_b = 0.0f;

		if (_b > 1.0f)
			_b = 1.0f;

		b = _b;
	}

	public float getAlpha() {
		return a;
	}

	public void setAlpha(float _a) {
		if (_a < 0.0f)
			_a = 0.0f;

		if (_a > 1.0f)
			_a = 1.0f;

		a = _a;
	}

	public Vector3f toVec3f() {
		return new Vector3f(r, g, b);
	}

	public Vector4f toVec4f() {
		return new Vector4f(r, g, b, a);
	}

}
