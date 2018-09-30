package cn.stateofwar.sowr.gui.render;

import static org.lwjgl.opengl.GL45.*;

import cn.stateofwar.sowr.gui.render.ogl.Shader;
import cn.stateofwar.sowr.gui.render.ogl.Shader.ShaderType;
import cn.stateofwar.sowr.gui.render.ogl.ShaderProgram;
import cn.stateofwar.sowr.gui.render.ogl.ArrayObj;
import cn.stateofwar.sowr.gui.render.ogl.BufferObj;
import cn.stateofwar.sowr.util.DataUtils;

/**
 * A raw render model with only a color.
 */
public class RawModel extends Model {

	/** Vertices for this model, with a minimum of 3 vertices in total. */
	public float[] vertices;

	/** Indices for lessening vertices. */
	public int[] indices;

	/** Vertex array object. */
	private ArrayObj vao;

	/** Vertex buffer object for vertices. */
	private BufferObj vbo_vertices;

	/** Vertex buffer object for coloring. */
	private BufferObj vbo_color;

	/** Element buffer object for vertex indices. */
	private BufferObj ebo;

	public static final ShaderProgram prog = new ShaderProgram(
			new Shader[] { Shader.createShader("sowr/gui/render/shader/raw.glsl_vertex", ShaderType.VERTEX, true),
					Shader.createShader("sowr/gui/render/shader/raw.glsl_fragment", ShaderType.FRAGMENT, true) });

	/**
	 * Construct the class while building the OpenGL model.
	 * 
	 * @param _vertices Vertices coordinates for the model.
	 * 
	 * @param _indices  Vertex indices for the model.
	 * 
	 * @param color     Color for the model. Will be white if left null.
	 */
	public RawModel(float[] _vertices, int[] _indices, RGBA color) {
		vertices = _vertices;
		indices = _indices;

		vao = new ArrayObj();
		vbo_vertices = new BufferObj();
		vbo_color = new BufferObj();
		ebo = new BufferObj();

		if (color == null)
			color = RGBA.WHITE;

		prog.use();

		vao.bind();
		vbo_vertices.bind(GL_ARRAY_BUFFER);
		vbo_vertices.buffer(GL_ARRAY_BUFFER, DataUtils.createFloatBuffer(vertices), GL_STATIC_DRAW);

		vbo_color.bind(GL_ARRAY_BUFFER);
		vbo_color.buffer(GL_ARRAY_BUFFER, DataUtils.createFloatBuffer(color.toFloatArray()), GL_STATIC_DRAW);

		ebo.bind(GL_ELEMENT_ARRAY_BUFFER);
		ebo.buffer(GL_ELEMENT_ARRAY_BUFFER, DataUtils.createIntBuffer(indices), GL_STATIC_DRAW);

		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);

		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		glVertexAttribPointer(1, 4, GL_FLOAT, false, 0, 0);

		glDisableVertexAttribArray(1);
		glDisableVertexAttribArray(0);

		vbo_color.unBind(GL_ARRAY_BUFFER);
		ebo.unBind(GL_ELEMENT_ARRAY_BUFFER);
		vao.unbind();
	}

	/**
	 * Draw the model.
	 */
	public void draw() {
		prog.use();
		vao.bind();

		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);

		glDrawElements(GL_TRIANGLES, indices.length, GL_UNSIGNED_INT, 0);

		glDisableVertexAttribArray(1);
		glDisableVertexAttribArray(0);

		vao.unbind();
	}

	/**
	 * Delete the model and release spaces.
	 */
	public void abrogate() {
		vbo_vertices.delete();
		vbo_color.delete();
		ebo.delete();
		vao.delete();
	}

}
