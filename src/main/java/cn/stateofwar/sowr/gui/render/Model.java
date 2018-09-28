package cn.stateofwar.sowr.gui.render;

/**
 * A rendering model which contains parameters for OpenGL rendering.
 */
public abstract class Model {

	/**
	 * Render the model.
	 */
	public abstract void draw();

	/**
	 * Delete this model and release the spaces.
	 */
	public abstract void abrogate();

}
