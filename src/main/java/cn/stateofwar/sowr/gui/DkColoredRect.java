package cn.stateofwar.sowr.gui;

import org.joml.Vector4i;

import cn.stateofwar.sowr.core.Game;
import cn.stateofwar.sowr.gui.render.RGBA;

/**
 * A colored rectangle.
 */
public class DkColoredRect extends DkXVisual {

	/**
	 * Coordinate of the rectangle.<br />
	 * x-y values are the coordinate of the bottom left corner, and z-w values are
	 * its width and height.
	 */
	private Vector4i coord;

	/** Color of the rectangle. */
	private RGBA color;

	public DkColoredRect(String _identifier, Vector4i _coord, RGBA _color) {
		identifier = _identifier;
		coord = _coord;
		color = _color;
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
	 * Get the color of the rectangle.
	 * 
	 * @return Color of the rectangle.
	 */
	public RGBA getColor() {
		return color;
	}

	/**
	 * Set the color of the rectangle.
	 * 
	 * @param _color Color of the rectangle.
	 */
	public void setColor(RGBA _color) {
		color = _color;
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
		Game.state.getRenderer().drawColoredRect(coord.x, coord.y, coord.z, coord.w, color);

		super.render();
	}

}
