package cn.stateofwar.sowr.graphic.render;

import java.nio.FloatBuffer;

import cn.stateofwar.sowr.util.DataUtils;

/** A Red-Green-Blue-Alpha color. */
public class RGBA {

	/** A preset of black color. */
	public static final RGBA BLACK = new RGBA(0.0f, 0.0f, 0.0f);

	/** A preset of red color. */
	public static final RGBA RED = new RGBA(1.0f, 0.0f, 0.0f);

	/** A preset of green color. */
	public static final RGBA GREEN = new RGBA(0.0f, 1.0f, 0.0f);

	/** A preset of blue color. */
	public static final RGBA BLUE = new RGBA(0.0f, 0.0f, 1.0f);

	/** A preset of white color. */
	public static final RGBA WHITE = new RGBA(1.0f, 1.0f, 1.0f);

	/** Color component. */
	private float r, g, b, a;

	/**
	 * Create a RGBA color of black.
	 */
	public RGBA() {
		this(0.0f, 0.0f, 0.0f);
	}

	/**
	 * Create a RGBA color with alpha set to 1.
	 * 
	 * @param r The red component.
	 * 
	 * @param g The green component.
	 * 
	 * @param b The blue component.
	 */
	public RGBA(float r, float g, float b) {
		setR(r);
		setG(g);
		setB(b);
		setA(1.0f);
	}

	/**
	 * Create a RGBA color.
	 * 
	 * @param r The red component.
	 * 
	 * @param g The green component.
	 * 
	 * @param b The blue component.
	 * 
	 * @param a The alpha component.
	 */
	public RGBA(float r, float g, float b, float a) {
		setR(r);
		setG(g);
		setB(b);
		setA(a);
	}

	/**
	 * Get the red component.
	 * 
	 * @return Red component.
	 */
	public float getR() {
		return r;
	}

	/**
	 * Set the red component.
	 * 
	 * @param _r Red component.
	 */
	public void setR(float _r) {
		if (_r < 0.0f)
			_r = 0.0f;

		if (_r > 1.0f)
			_r = 1.0f;

		r = _r;
	}

	/**
	 * Get the green component.
	 * 
	 * @return Green component.
	 */
	public float getG() {
		return g;
	}

	/**
	 * Set the green component.
	 * 
	 * @param _g Green component.
	 */
	public void setG(float _g) {
		if (_g < 0.0f)
			_g = 0.0f;

		if (_g > 1.0f)
			_g = 1.0f;

		g = _g;
	}

	/**
	 * Get the blue component.
	 * 
	 * @return Blue component.
	 */
	public float getB() {
		return b;
	}

	/**
	 * Set the blue component.
	 * 
	 * @param _b Blue component.
	 */
	public void setB(float _b) {
		if (_b < 0.0f)
			_b = 0.0f;

		if (_b > 1.0f)
			_b = 1.0f;

		b = _b;
	}

	/**
	 * Get the alpha component.
	 * 
	 * @return Alpha component.
	 */
	public float getA() {
		return a;
	}

	/**
	 * Set the alpha component.
	 * 
	 * @param _a Alpha component.
	 */
	public void setA(float _a) {
		if (_a < 0.0f)
			_a = 0.0f;

		if (_a > 1.0f)
			_a = 1.0f;

		a = _a;
	}

	/**
	 * Convert the color components to a float array with 4 elements (red, green,
	 * blue, alpha) repeated for 4 times.
	 * 
	 * @return Array created.
	 */
	public float[] toFloatArray44() {
		return new float[] { r, g, b, a, r, g, b, a, r, g, b, a, r, g, b, a };
	}

	/**
	 * Convert the color components to a float buffer with 4 elements (red, green,
	 * blue, alpha).
	 * 
	 * @return Float buffer created.
	 */
	public FloatBuffer toFloatBuffer() {
		return DataUtils.createFloatBuffer(new float[] { r, g, b, a });
	}

}
