package cn.stateofwar.sowr.gui.render;

/**
 * A template for render models.
 */
public abstract class Model {

	/**
	 * Create the OpenGL render model of the object.
	 */
	public abstract void create();

	/**
	 * Delete and release spaces of data from this object.
	 */
	public abstract void abrogate();

}
