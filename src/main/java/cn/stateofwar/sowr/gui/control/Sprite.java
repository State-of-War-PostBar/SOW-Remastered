package cn.stateofwar.sowr.gui.control;

import org.joml.Vector4d;

import cn.stateofwar.sowr.core.Game;
import cn.stateofwar.sowr.gui.render.Texture;

/**
 * A sprite image on the screen.
 */
public class Sprite extends Element {

	/** Coordinate of the sprite. */
	private Vector4d coord;

	/** Texture of the sprite. */
	private Texture texture;

	/**
	 * Create a sprite image.
	 * 
	 * @param _identifier Identifier for the element.
	 * 
	 * @param coord       Coordinates of the sprite. x-y are coordinate values of
	 *                    the bottom left corner, z-w are its width and height.
	 * 
	 * @param texture     Texture for the sprite image.
	 */
	public Sprite(String _identifier, Vector4d coord, Texture texture) {
		identifier = _identifier;
		resize(coord);
		setTexture(texture);
	}

	/**
	 * Set the texture of the sprite.
	 * 
	 * @param _texture Texture of the sprite.
	 */
	public void setTexture(Texture _texture) {
		texture = _texture;
	}

	/**
	 * Get the coordinate of the sprite.
	 */
	@Override
	public Object getSize() {
		return coord;
	}

	/**
	 * Render the sprite and its children.
	 */
	@Override
	public void render() {
		if (!isHidden() && isEnabled())
			Game.state.getRenderer().drawTexturedRect((int) coord.x, (int) coord.y, (int) coord.z, (int) coord.w,
					texture);
		super.render();
	}

	/**
	 * Change the coordinate of the sprite.
	 * 
	 * @param size New coordinate.
	 */
	@Override
	public void resize(Object size) {
		coord = (Vector4d) size;
		super.resize(size);
	}

	/**
	 * Called when the parent is resized.<br />
	 * The sprite will resize to its parent's size and resize all of its children.
	 */
	@Override
	public void onParentResize(Object size) {
		resize(size);
	}

}
