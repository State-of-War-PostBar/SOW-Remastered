package cn.stateofwar.sowr.gui.render;

import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL45.*;

import cn.stateofwar.sowr.gui.render.ogl.Shader;
import cn.stateofwar.sowr.gui.render.ogl.Shader.ShaderType;
import cn.stateofwar.sowr.gui.render.ogl.ShaderProgram;
import cn.stateofwar.sowr.gui.render.ogl.ArrayObj;
import cn.stateofwar.sowr.gui.render.ogl.BufferObj;
import cn.stateofwar.sowr.util.DataUtils;

/**
 * A render model with texture.
 */
public class TexturedModel extends Model {

	/**
	 * Vertex array object for this model.
	 */
	public ArrayObj vao;

	/** Buffer for vertices. */
	public BufferObj vbo_vertices;

	/** Buffer for coordinates of the texture. */
	public BufferObj vbo_texcoords;

	/** Buffer for vertex indices. */
	public BufferObj ebo;

	/** Coordinates of vertices. */
	private float[] vertices;

	/** Vertex indices. */
	private int[] indices;

	/** Coordinates of used texture part. */
	private float[] texture_coords;

	/** The texture for the model. */
	public Texture texture;

	/** Preset shader program for the model. */
	private static final ShaderProgram prog = new ShaderProgram(new Shader[] {
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

		vao = new ArrayObj();
		vbo_vertices = new BufferObj();
		vbo_texcoords = new BufferObj();
		ebo = new BufferObj();

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
	}

	/**
	 * Change the vertices of the model.
	 * 
	 * @param _vertices New vertices.
	 */
	public void modifyVertices(float[] _vertices, int[] _indices) {
		vertices = _vertices;
		indices = _indices;

		vao.bind();

		vbo_vertices.bind(GL_ARRAY_BUFFER);
		vbo_vertices.bufferSub(GL_ARRAY_BUFFER, 0, DataUtils.createFloatBuffer(vertices));

		ebo.bind(GL_ELEMENT_ARRAY_BUFFER);
		ebo.bufferSub(GL_ELEMENT_ARRAY_BUFFER, 0, DataUtils.createIntBuffer(indices));

		vbo_vertices.unbind(GL_ARRAY_BUFFER);
		ebo.unbind(GL_ELEMENT_ARRAY_BUFFER);

		vao.unbind();
	}

	/**
	 * Change the texture coordinates of the model.
	 * 
	 * @param _texture_coords New texture coordinates.
	 */
	public void modifyTexCoords(float[] _texture_coords) {
		texture_coords = _texture_coords;

		vao.bind();

		vbo_texcoords.bind(GL_ARRAY_BUFFER);
		vbo_texcoords.bufferSub(GL_ARRAY_BUFFER, 0, DataUtils.createFloatBuffer(texture_coords));
		vbo_texcoords.unbind(GL_ARRAY_BUFFER);

		vao.unbind();
	}

	/**
	 * Change the texture.
	 * 
	 * @param _texture New texture.
	 */
	public void modifyTexture(Texture _texture) {
		texture = _texture;
	}

	/**
	 * Delete this model and release the spaces.
	 */
	@Override
	public void abrogate() {
		vbo_vertices.delete();
		vbo_texcoords.delete();
		ebo.delete();
		vao.delete();
	}

}
