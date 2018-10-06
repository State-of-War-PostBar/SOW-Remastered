package cn.stateofwar.sowr.gui.base;

import org.joml.Vector4i;

import cn.stateofwar.sowr.core.Game;
import cn.stateofwar.sowr.gui.render.Texture;

/**
 * An image.
 */
public class DkImage extends DkXVisual {

	/**
	 * Coordinate of the element.<br />
	 * x-y are values the coordinate of the bottom left corner, and z-w values are
	 * its width and height.
	 */
	private Vector4i coord;

	/** Texture for this image. */
	private Texture texture;

	public DkImage(String _identifier, Vector4i coordinate, Texture _texture) {
		identifier = _identifier;
		coord = coordinate;
		texture = _texture;
	}

	/**
	 * Get the coordinate information of the element.
	 * 
	 * @return Coordinate information.
	 */
	@Override
	public Object getCoordInfo() {
		return coord;
	}

	/**
	 * Resize the element.
	 * 
	 * @param _coord New coordinate information.
	 */
	public void resize(Vector4i _coord) {
		coord = _coord;
		super.resize(_coord);
	}

	/**
	 * Change the texture for the image.
	 */
	public void changeTexture(Texture _texture) {
		texture = _texture;
	}

	/**
	 * Render the element and all of its children.
	 */
	@Override
	public void render() {
		if (hidden) {
			super.render();
			return;
		}

		Game.state.getRenderer().drawTexturedRect(coord.x, coord.y, coord.z, coord.w, texture);

		super.render();
	}

}
