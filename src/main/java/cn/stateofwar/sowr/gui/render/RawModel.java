package cn.stateofwar.sowr.gui.render;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import cn.stateofwar.sowr.util.DataUtils;

/**
 * A raw model with only shape and no color(white).
 */
public class RawModel extends Model {

	protected VAO vao;

	protected VBO vbo;

	protected VBO ebo;

	protected int vertexCount;

	protected float[] vertices;

	protected int[] indices;

	public RawModel(float[] _vertices, int[] _indices) {
		vertices = _vertices;
		indices = _indices;
		vertexCount = _indices.length;
	}

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
		vao.unBind();
		glDisableVertexAttribArray(0);
	}

	public void abrogate() {
		vao.delete();
		vbo.delete();
		ebo.delete();
	}

}
