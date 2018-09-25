package cn.stateofwar.sowr.gui.render;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import cn.stateofwar.sowr.util.DataUtils;

/**
 * A raw render object with no texture and color(white).
 */
public class RawModel extends Model {

	/** Vertex array object for the model. */
	protected VAO vao;

	/** Vertex data buffer for the model. */
	protected VBO vbo;

	/** Vertex indices data buffer for the model. */
	protected VBO ebo;

	/** Total number of vertices of the object. */
	protected int vertexCount;

	/** Coordinates of the vertices. */
	protected float[] vertices;

	/** Vertex indices. */
	protected int[] indices;

	public RawModel(float[] _vertices, int[] _indices) {
		vertices = _vertices;
		indices = _indices;
		vertexCount = _indices.length;
		create();
	}

	/**
	 * Create the OpenGL render model of the object.
	 */
	public void create() {
		FloatBuffer verticesBuffer = DataUtils.createFloatBuffer(vertices);
		IntBuffer indicesBuffer = DataUtils.createIntBuffer(indices);

		vao = new VAO();
		vao.bind();

		vbo = new VBO();
		vbo.bind(GL_ARRAY_BUFFER);
		vbo.upload(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);

		ebo = new VBO();
		ebo.bind(GL_ELEMENT_ARRAY_BUFFER);
		ebo.upload(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);

		glEnableVertexAttribArray(0);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		vao.unbind();
		glDisableVertexAttribArray(0);
	}

	/**
	 * Delete and release spaces of data from this object.
	 */
	public void abrogate() {
		vao.delete();
		vbo.delete();
		ebo.delete();
	}

}
