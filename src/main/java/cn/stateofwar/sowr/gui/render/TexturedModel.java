package cn.stateofwar.sowr.gui.render;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import cn.stateofwar.sowr.util.DataUtils;

/**
 * A model with texture.
 */
public class TexturedModel extends Model {

	protected VAO vao;

	protected VBO vbo;

	protected VBO ebo;

	protected int vertexCount;

	protected float[] vertices;

	protected float[] texureCoords;

	protected int[] indices;

	protected Texture texture;

	/**
	 * Initialize a textured model.
	 * 
	 * @param _vertices      Coordinates of this model.
	 * @param _textureCoords Parts of the texture need to be rendered.
	 * @param _indices       Indices for vertices.
	 * @param textureFile    File for the texture of this model.
	 */
	public TexturedModel(float[] _vertices, float[] _textureCoords, int[] _indices, String textureFile) {
		vertices = _vertices;
		indices = _indices;
		vertexCount = _indices.length;
		texture = Texture.loadTexture(textureFile);
	}

	@Override
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

	@Override
	public void abrogate() {
		vao.delete();
		vbo.delete();
		ebo.delete();
		texture.delete();
	}

}
