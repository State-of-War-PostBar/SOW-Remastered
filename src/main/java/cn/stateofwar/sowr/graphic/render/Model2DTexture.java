package cn.stateofwar.sowr.graphic.render;

import static org.lwjgl.opengl.GL45.*;

import cn.stateofwar.sowr.graphic.ogl.BufferObject;
import cn.stateofwar.sowr.graphic.ogl.Shader;
import cn.stateofwar.sowr.graphic.ogl.ShaderBus;
import cn.stateofwar.sowr.graphic.ogl.ShaderProgram;
import cn.stateofwar.sowr.graphic.ogl.VertexArray;
import cn.stateofwar.sowr.util.DataUtils;

public class Model2DTexture extends Model2D {

	/** Vertices of the model. */
	private float[] vertices;

	/** Indices for vertices. */
	private int[] indices;

	/** Vertex array for the model. */
	private VertexArray vao;

	/** Buffer object for vertices. */
	private BufferObject vbo_vertices;

	/** Buffer object for indices. */
	private BufferObject ebo;

	/** Texture of the model. */
	private Texture2D texture;

	/** Coordinates of used texture area. */
	private float[] texture_coordinates;

	/** Buffer object for texture coordinates. */
	private BufferObject vbo_texcoords;

	/** Shader program for raw texture models. */
	private static final ShaderProgram SHADER = new ShaderProgram(
			new Shader[] { ShaderBus.vertex_raw_texture_2d, ShaderBus.fragment_raw_texture_2d });

	/**
	 * Create a 2D raw colored render model.
	 * 
	 * @param _vertices            Vertices of the model.
	 * 
	 * @param _indices             Indices for vertices.
	 * 
	 * @param _texture             The texture to use.
	 * 
	 * @param _texture_coordinates Coordinates of used texture area.
	 */
	public Model2DTexture(float[] _vertices, int[] _indices, Texture2D _texture, float[] _texture_coordinates) {
		vertices = _vertices;
		indices = _indices;
		texture = _texture;
		texture_coordinates = _texture_coordinates;

		vao = new VertexArray();
		vbo_vertices = new BufferObject();
		vbo_texcoords = new BufferObject();
		ebo = new BufferObject();

		SHADER.bind();
		vao.bind();

		vbo_vertices.bind(GL_ARRAY_BUFFER);
		vbo_vertices.buffer(DataUtils.createFloatBuffer(vertices).flip(), GL_STATIC_DRAW);

		vbo_texcoords.bind(GL_ARRAY_BUFFER);
		vbo_texcoords.buffer(DataUtils.createFloatBuffer(texture_coordinates).flip(), GL_STATIC_DRAW);

		ebo.bind(GL_ELEMENT_ARRAY_BUFFER);
		ebo.buffer(DataUtils.createIntBuffer(indices).flip(), GL_STATIC_DRAW);

		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);

		vbo_vertices.bind(GL_ARRAY_BUFFER);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

		vbo_texcoords.bind(GL_ARRAY_BUFFER);
		glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);

		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);

		vbo_texcoords.unbind();
		vao.unbind();
		SHADER.unbind();
	}

	/**
	 * Draw the model.
	 */
	public void draw() {
		texture.bind();
		SHADER.bind();
		vao.bind();

		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);

		glDrawElements(GL_TRIANGLES, indices.length, GL_UNSIGNED_INT, 0);

		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);

		vao.unbind();
		SHADER.bind();
		texture.unbind();
	}

	/**
	 * Update vertices information of the model.
	 * 
	 * @param _vertices New vertices.
	 */
	public void changeVertices(float[] _vertices) {
		vertices = _vertices;

		vao.bind();

		vbo_vertices.bind(GL_ARRAY_BUFFER);
		vbo_vertices.bufferSub(0, DataUtils.createFloatBuffer(vertices).flip());
		vbo_vertices.unbind();

		vao.unbind();
	}

	/**
	 * Update texture of the model.
	 * 
	 * @param _texture New texture.
	 */
	public void changeTexture(Texture2D _texture) {
		texture = _texture;
	}

	/**
	 * Update texture coordinates information of the model.
	 * 
	 * @param _texture_coordinates New texture coordinates.
	 */
	public void changeTexCoord(float[] _texture_coordinates) {
		texture_coordinates = _texture_coordinates;

		vao.bind();

		vbo_texcoords.bind(GL_ARRAY_BUFFER);
		vbo_texcoords.bufferSub(0, DataUtils.createFloatBuffer(texture_coordinates).flip());
		vbo_texcoords.unbind();

		vao.unbind();
	}

	/**
	 * Clean up the model.
	 */
	public void abrogate() {
		vbo_vertices.abrogate();
		vbo_texcoords.abrogate();
		ebo.abrogate();
		vao.abrogate();
	}

}
