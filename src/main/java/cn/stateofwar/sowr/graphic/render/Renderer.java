package cn.stateofwar.sowr.graphic.render;

/** Renderer for the program. */
public class Renderer {

  /**
   * Vertex indices for a rectangle; for 4 vertices, their listing is <b>Top Right</b> → <b>Bottom
   * Right</b> → <b>Bottom Left</b> → <b>Top Left</b>.
   */
  public static final int[] INDICES_RECTANGLE = new int[] {0, 1, 3, 1, 2, 3};

  /**
   * Draw a model.
   *
   * @param model Model to draw.
   */
  public void draw(Model2D model) {
    model.draw();
  }

}
