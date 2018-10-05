package cn.stateofwar.sowr.gui.render;

import java.nio.FloatBuffer;

import org.joml.Vector3f;
import org.joml.Vector4f;

import cn.stateofwar.sowr.util.DataUtils;

/**
 * Red, Green, Blue and Alpha value storage.
 */
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

	/**
	 * Get the red component.
	 * 
	 * @return Red component.
	 */
	public float getR() {
		return r;
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
	 * Get the blue component.
	 * 
	 * @return Blue component.
	 */
	public float getB() {
		return b;
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
	 * blue).
	 * 
	 * @return Array created.
	 */
	public float[] toFloatArray3() {
		return new float[] { r, g, b };
	}

	/**
	 * Convert the color components to a float array with 4 elements (red, green,
	 * blue, alpha).
	 * 
	 * @return Array created.
	 */
	public float[] toFloatArray4() {
		return new float[] { r, g, b, a };
	}

	/**
	 * Convert the color components to a float array with 4 elements (red, green,
	 * blue, alpha) repeated for 4 times.<br />
	 * <i>This is used for rendering 4 vertices of a rectangle.</i>
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

	/**
	 * Convert the color components to a float vector with 3 elements (red, green,
	 * blue).
	 * 
	 * @return Float vector created.
	 */
	public Vector3f toVec3f() {
		return new Vector3f(r, g, b);
	}

	/**
	 * Convert the color components to a float vector with 4 elements (red, green,
	 * blue, alpha).
	 * 
	 * @return Float vector created.
	 */
	public Vector4f toVec4f() {
		return new Vector4f(r, g, b, a);
	}

	/**
	 * Convert a grate value (0.0 - 255.0) to percentage (0.0 - 1.0) value.
	 * 
	 * @param value The great value.
	 * 
	 * @return Percentage value.
	 */
	public static float to100(float value) {
		return value / 255.0f;
	}

	/**
	 * Convert a percentage (0.0 - 1.0) value to great value (0.0 - 255.0).
	 * 
	 * @param value The percentage value.
	 * 
	 * @return Great value.
	 */
	public static float to255(float value) {
		return value * 255.0f;
	}

}
