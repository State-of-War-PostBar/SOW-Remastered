package cn.stateofwar.sowr.gui.render;

import static org.lwjgl.opengl.GL45.*;

import cn.stateofwar.sowr.gui.render.ogl.ArrayObject;
import cn.stateofwar.sowr.gui.render.ogl.BufferObject;
import cn.stateofwar.sowr.gui.render.ogl.Shader;
import cn.stateofwar.sowr.gui.render.ogl.ShaderProgram;
import cn.stateofwar.sowr.gui.render.ogl.Shaders;
import cn.stateofwar.sowr.util.DataUtils;

/**
 * A render model with texture.
 */
public class TexturedModel extends Model {

	/** The texture for the model. */
	private Texture texture;

	/** Vertex array object for this model. */
	private ArrayObject vao;

	/** Buffer for coordinates of the texture. */
	private BufferObject vbo_texcoords;

	/** Buffer for vertices. */
	private BufferObject vbo_vertices;

	/** Buffer for vertex indices. */
	private BufferObject ebo;

	/** Coordinates of vertices, with a minimum of 3 vertices in total. */
	private float[] vertices;

	/** Vertex indices. */
	private int[] indices;

	/** Coordinates of used texture part. */
	private float[] texture_coords;

	/** Preset shader program for the model. */
	private static final ShaderProgram prog = new ShaderProgram(
			new Shader[] { Shaders.VERTEX_SAMPLERLESS_TEXTURE, Shaders.FRAGMENT_SAMPLERLESS_TEXTURE });

	/**
	 * Create a model with texture.
	 * 
	 * @param _vertices       Vertices of the model.
	 * 
	 * @param _indices        Vertex indices of the model.
	 * 
	 * @param _texture        Texture of the model.
	 * 
	 * @param _texture_coords Coordinates of used texture area.
	 */
	public TexturedModel(float[] _vertices, int[] _indices, Texture _texture, float[] _texture_coords) {
		vertices = _vertices;
		indices = _indices;
		texture = _texture;
		texture_coords = _texture_coords;

		vao = new ArrayObject();
		vbo_vertices = new BufferObject();
		vbo_texcoords = new BufferObject();
		ebo = new BufferObject();

		prog.use();

		vao.bind();

		vbo_vertices.bind(GL_ARRAY_BUFFER);
		vbo_vertices.buffer(GL_ARRAY_BUFFER, DataUtils.createFloatBuffer(vertices), GL_STATIC_DRAW);

		vbo_texcoords.bind(GL_ARRAY_BUFFER);
		vbo_texcoords.buffer(GL_ARRAY_BUFFER, DataUtils.createFloatBuffer(texture_coords), GL_STATIC_DRAW);

		ebo.bind(GL_ELEMENT_ARRAY_BUFFER);
		ebo.buffer(GL_ELEMENT_ARRAY_BUFFER, DataUtils.createIntBuffer(indices), GL_STATIC_DRAW);

		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);

		vbo_vertices.bind(GL_ARRAY_BUFFER);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

		vbo_texcoords.bind(GL_ARRAY_BUFFER);
		glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);

		glDisableVertexAttribArray(1);
		glDisableVertexAttribArray(0);

		vbo_texcoords.unbind(GL_ARRAY_BUFFER);
		ebo.unbind(GL_ARRAY_BUFFER);
		vao.unbind();
		prog.unuse();
	}

	/**
	 * Render the model.
	 */
	@Override
	public void draw() {
		prog.use();
		texture.bind();
		vao.bind();

		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);

		glDrawElements(GL_TRIANGLES, indices.length, GL_UNSIGNED_INT, 0);

		glDisableVertexAttribArray(1);
		glDisableVertexAttribArray(0);

		vao.unbind();
		prog.unuse();
	}

	/**
	 * Delete this model and release the spaces.
	 */
	@Override
	public void abrogate() {
		vbo_vertices.abrogate();
		vbo_texcoords.abrogate();
		ebo.abrogate();
		vao.abrogate();
	}

}
