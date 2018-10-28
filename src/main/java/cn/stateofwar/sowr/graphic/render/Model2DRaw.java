package cn.stateofwar.sowr.graphic.render;

import static org.lwjgl.opengl.GL45.*;

import cn.stateofwar.sowr.graphic.ogl.BufferObject;
import cn.stateofwar.sowr.graphic.ogl.Shader;
import cn.stateofwar.sowr.graphic.ogl.ShaderBus;
import cn.stateofwar.sowr.graphic.ogl.ShaderProgram;
import cn.stateofwar.sowr.graphic.ogl.VertexArray;
import cn.stateofwar.sowr.util.DataUtils;

/**
 * A 2D model with only one color.
 */
public class Model2DRaw extends Model2D {

	/** Vertices of the model. */
	private float[] vertices;

	/** Indices for vertices. */
	private int[] indices;

	/** Vertex array for the model. */
	private VertexArray vao;

	/** Buffer object for vertices. */
	private BufferObject vbo_vertices;

	/** Buffer object for color. */
	private BufferObject vbo_color;

	/** Buffer object for indices. */
	private BufferObject ebo;

	/** Shader program for raw color models. */
	private static final ShaderProgram SHADER = new ShaderProgram(
			new Shader[] { ShaderBus.vertex_raw_color_2d, ShaderBus.fragment_raw_color_2d });

	/**
	 * Create a 2D raw colored render model.
	 * 
	 * @param _vertices Vertices of the model.
	 * 
	 * @param _indices  Indices for vertices.
	 * 
	 * @param color     Color of the model.
	 */
	public Model2DRaw(float[] _vertices, int[] _indices, RGBA color) {
		vertices = _vertices;
		indices = _indices;

		vao = new VertexArray();
		vbo_vertices = new BufferObject();
		vbo_color = new BufferObject();
		ebo = new BufferObject();

		if (color == null)
			color = RGBA.BLACK;

		SHADER.bind();
		vao.bind();

		vbo_vertices.bind(GL_ARRAY_BUFFER);
		vbo_vertices.buffer(DataUtils.createFloatBuffer(vertices).flip(), GL_STATIC_DRAW);

		vbo_color.bind(GL_ARRAY_BUFFER);
		vbo_color.buffer(DataUtils.createFloatBuffer(color.toFloatArray44()).flip(), GL_STATIC_DRAW);

		ebo.bind(GL_ELEMENT_ARRAY_BUFFER);
		ebo.buffer(DataUtils.createIntBuffer(indices).flip(), GL_STATIC_DRAW);

		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);

		vbo_vertices.bind(GL_ARRAY_BUFFER);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

		vbo_color.bind(GL_ARRAY_BUFFER);
		glVertexAttribPointer(1, 4, GL_FLOAT, false, 0, 0);

		glDisableVertexAttribArray(1);
		glDisableVertexAttribArray(0);

		vbo_color.unbind();
		vao.unbind();
		SHADER.unbind();
	}

	/**
	 * Draw the model.
	 */
	public void draw() {
		SHADER.bind();
		vao.bind();

		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);

		glDrawElements(GL_TRIANGLES, indices.length, GL_UNSIGNED_INT, 0);

		glDisableVertexAttribArray(1);
		glDisableVertexAttribArray(0);

		vao.unbind();
		SHADER.unbind();
	}

	/**
	 * Update vertices information of the model.
	 * 
	 * @param _vertices New vertices.
	 */
	public void updateVertices(float[] _vertices) {
		vertices = _vertices;

		vao.bind();

		vbo_vertices.bind(GL_ARRAY_BUFFER);
		vbo_vertices.bufferSub(0, DataUtils.createFloatBuffer(vertices).flip());
		vbo_vertices.unbind();

		vao.unbind();
	}

	/**
	 * Clean up the model.
	 */
	public void abrogate() {
		vbo_vertices.abrogate();
		vbo_color.abrogate();
		ebo.abrogate();
		vao.abrogate();
	}

}
