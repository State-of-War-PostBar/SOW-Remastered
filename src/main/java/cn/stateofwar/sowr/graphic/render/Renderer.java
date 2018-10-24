package cn.stateofwar.sowr.graphic.render;

public class Renderer {

	public static final int[] INDICES_RECTANGLE = new int[] { 0, 1, 3, 1, 2, 3 };

	public void draw(Model2D model) {
		model.draw();
	}

}
