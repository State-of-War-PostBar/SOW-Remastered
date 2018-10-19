package cn.stateofwar.sowr.gui.render;

import static org.lwjgl.opengl.GL46.*;

import cn.stateofwar.sowr.gui.render.ogl.ArrayObject;
import cn.stateofwar.sowr.gui.render.ogl.BufferObject;
import cn.stateofwar.sowr.gui.render.ogl.Shader;
import cn.stateofwar.sowr.gui.render.ogl.ShaderProgram;
import cn.stateofwar.sowr.gui.render.ogl.Shaders;
import cn.stateofwar.sowr.util.DataUtils;

/**
 * A raw render model with only a color.
 */
public class RawModel extends Model {

	/** Preset shader program for the model. */
	private static final ShaderProgram SHADER_PROGRAM = new ShaderProgram(
			new Shader[] { Shaders.vertex_raw, Shaders.fragment_raw });

	/** Vertex array object. */
	private ArrayObject vao;

	/** Vertex buffer object for vertices. */
	private BufferObject vbo_vertices;

	/** Vertex buffer object for coloring. */
	private BufferObject vbo_color;

	/** Element buffer object for vertex indices. */
	private BufferObject ebo;

	/** Vertices for the model. */
	private float[] vertices;

	/** Vertex indices. */
	private int[] indices;

	/**
	 * Construct the class while building the OpenGL model.
	 * 
	 * @param _vertices Vertices coordinates for the model.
	 * 
	 * @param _indices  Vertex indices for the model.
	 * 
	 * @param color     Color for the model. Will be white if it's null.
	 */
	public RawModel(float[] _vertices, int[] _indices, RGBA color) {
		vertices = _vertices;
		indices = _indices;

		vao = new ArrayObject();
		vbo_vertices = new BufferObject();
		vbo_color = new BufferObject();
		ebo = new BufferObject();

		if (color == null)
			color = RGBA.WHITE;

		SHADER_PROGRAM.use();
		vao.bind();

		vbo_vertices.bind(GL_ARRAY_BUFFER);
		vbo_vertices.buffer(GL_ARRAY_BUFFER, DataUtils.createFloatBuffer(vertices), GL_STATIC_DRAW);

		vbo_color.bind(GL_ARRAY_BUFFER);
		vbo_color.buffer(GL_ARRAY_BUFFER, DataUtils.createFloatBuffer(color.toFloatArray44()), GL_STATIC_DRAW);

		ebo.bind(GL_ELEMENT_ARRAY_BUFFER);
		ebo.buffer(GL_ELEMENT_ARRAY_BUFFER, DataUtils.createIntBuffer(indices), GL_STATIC_DRAW);

		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);

		vbo_vertices.bind(GL_ARRAY_BUFFER);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

		vbo_color.bind(GL_ARRAY_BUFFER);
		glVertexAttribPointer(1, 4, GL_FLOAT, false, 0, 0);

		glDisableVertexAttribArray(1);
		glDisableVertexAttribArray(0);

		vbo_color.unbind(GL_ARRAY_BUFFER);
		vao.unbind();
		SHADER_PROGRAM.unuse();
	}

	/**
	 * Draw the model.
	 */
	@Override
	public void draw() {
		SHADER_PROGRAM.use();
		vao.bind();

		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);

		glDrawElements(GL_TRIANGLES, indices.length, GL_UNSIGNED_INT, 0);

		glDisableVertexAttribArray(1);
		glDisableVertexAttribArray(0);

		vao.unbind();
		SHADER_PROGRAM.unuse();
	}

	/**
	 * Delete the model and release spaces.
	 */
	@Override
	public void abrogate() {
		vbo_vertices.abrogate();
		vbo_color.abrogate();
		ebo.abrogate();
		vao.abrogate();
	}

}
