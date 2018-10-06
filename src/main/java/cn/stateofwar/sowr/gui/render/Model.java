package cn.stateofwar.sowr.gui.render;

/**
 * A rendering model contains parameters and data for OpenGL rendering.
 */
public abstract class Model {

	/**
	 * Render the model.
	 */
	public abstract void draw();

	/**
	 * Delete the model and release the spaces.
	 */
	public abstract void abrogate();

}
