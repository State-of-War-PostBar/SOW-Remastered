package cn.stateofwar.sowr.gui.render;

import static org.lwjgl.opengl.GL45.*;

import cn.stateofwar.sowr.gui.render.ogl.Shader;
import cn.stateofwar.sowr.gui.render.ogl.Shader.ShaderType;
import cn.stateofwar.sowr.gui.render.ogl.ShaderProgram;
import cn.stateofwar.sowr.gui.render.ogl.VAO;
import cn.stateofwar.sowr.gui.render.ogl.VBO;
import cn.stateofwar.sowr.util.DataUtils;

/**
 * A render model with texture.
 */
public class TexturedModel extends Model {

	/**
	 * Vertex array object for this model.
	 */
	public VAO vao;

	/** Vertices buffer object. */
	public VBO vbo_vertices;

	/** Coordinates of textures buffer object. */
	public VBO vbo_texcoords;

	/** Vertex indices buffer object. */
	public VBO ebo;

	/** Coordinates of vertices. */
	private float[] vertices;

	/** Vertex indices. */
	private int[] indices;

	/** Coordinates of used texture part. */
	private float[] texture_coords;

	/** The texture for the model. */
	public Texture texture;

	/** Preset shader program for the model. */
	public ShaderProgram prog = new ShaderProgram(new Shader[] {
			Shader.createShader("sowr/gui/render/shader/samplerless_texture.glsl_vertex", ShaderType.VERTEX, true),
			Shader.createShader("sowr/gui/render/shader/samplerless_texture.glsl_fragment", ShaderType.FRAGMENT,
					true) });

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

		vao = new VAO();
		vbo_vertices = new VBO();
		vbo_texcoords = new VBO();
		ebo = new VBO();

		prog.use();

		vao.bind();

		vbo_vertices.bind(GL_ARRAY_BUFFER);
		vbo_vertices.buffer(GL_ARRAY_BUFFER, DataUtils.createFloatBuffer(vertices), GL_STATIC_DRAW);

		ebo.bind(GL_ELEMENT_ARRAY_BUFFER);
		ebo.buffer(GL_ELEMENT_ARRAY_BUFFER, DataUtils.createIntBuffer(indices), GL_STATIC_DRAW);

		vbo_texcoords.bind(GL_ARRAY_BUFFER);
		vbo_texcoords.buffer(GL_ARRAY_BUFFER, DataUtils.createFloatBuffer(texture_coords), GL_STATIC_DRAW);

		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);

		vbo_vertices.bind(GL_ARRAY_BUFFER);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

		vbo_texcoords.bind(GL_ARRAY_BUFFER);
		glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);

		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);

		vbo_vertices.unBind(GL_ARRAY_BUFFER);
		ebo.unBind(GL_ARRAY_BUFFER);
		vao.unbind();
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
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
	}

	/**
	 * Delete this model and release the spaces.
	 */
	@Override
	public void abrogate() {
		vao.delete();
		vbo_vertices.delete();
		vbo_texcoords.delete();
		ebo.delete();
		prog.delete();
	}

}
