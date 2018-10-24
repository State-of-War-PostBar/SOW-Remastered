package cn.stateofwar.sowr.graphic.render;

import static org.lwjgl.opengl.GL45.*;

import cn.stateofwar.sowr.graphic.ogl.BufferObject;
import cn.stateofwar.sowr.graphic.ogl.Shader;
import cn.stateofwar.sowr.graphic.ogl.ShaderBus;
import cn.stateofwar.sowr.graphic.ogl.ShaderProgram;
import cn.stateofwar.sowr.graphic.ogl.VertexArray;
import cn.stateofwar.sowr.util.DataUtils;

public class Model2DTexture extends Model2D {

	private static final ShaderProgram SHADER = new ShaderProgram(
			new Shader[] { ShaderBus.vertex_raw_texture_2d, ShaderBus.fragment_raw_texture_2d });

	private float[] vertices;

	private int[] indices;

	private VertexArray vao;

	private BufferObject vbo_vertices;

	private BufferObject ebo;

	private Texture2D texture;

	private float[] texture_coordinates;

	private BufferObject vbo_texcoords;

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

		glDisableVertexAttribArray(1);
		glDisableVertexAttribArray(0);

		vbo_texcoords.unbind();
		vao.unbind();
		SHADER.unbind();
	}

	@Override
	public void draw() {
		SHADER.bind();
		texture.bind();
		vao.bind();

		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glDrawElements(GL_TRIANGLES, indices.length, GL_UNSIGNED_INT, 0);
		glDisableVertexAttribArray(1);
		glDisableVertexAttribArray(0);

		vao.unbind();
		texture.unbind();
		SHADER.bind();
	}

	public void updateVertices(float[] _vertices) {
		vertices = _vertices;

		vao.bind();

		vbo_vertices.bind(GL_ARRAY_BUFFER);
		vbo_vertices.buffer(DataUtils.createFloatBuffer(vertices).flip(), GL_STATIC_DRAW);
		vbo_vertices.unbind();

		vao.unbind();
	}

	public void abrogate() {
		vbo_vertices.abrogate();
		vbo_texcoords.abrogate();
		ebo.abrogate();
		vao.abrogate();
	}

}
