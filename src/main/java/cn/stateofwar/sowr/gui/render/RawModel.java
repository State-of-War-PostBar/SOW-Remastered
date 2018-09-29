package cn.stateofwar.sowr.gui.render;

import static org.lwjgl.opengl.GL45.*;

import cn.stateofwar.sowr.gui.render.ogl.Shader;
import cn.stateofwar.sowr.gui.render.ogl.Shader.ShaderType;
import cn.stateofwar.sowr.gui.render.ogl.ShaderProgram;
import cn.stateofwar.sowr.gui.render.ogl.VAO;
import cn.stateofwar.sowr.gui.render.ogl.VBO;
import cn.stateofwar.sowr.util.DataUtils;

/**
 * A raw render model with only a color.
 */
public class RawModel extends Model {

	/** Vertex array object. */
	public VAO vao;

	/** Vertex buffer object for vertices. */
	public VBO vbo;

	/** Element buffer object for vertex indices. */
	public VBO ebo;

	/** Vertices for this model, with a minimum of 3 vertices in total. */
	public float[] vertices;

	/** Indices for lessening vertices. */
	public int[] indices;

	/** Shader program for raw models. */
	public ShaderProgram prog = new ShaderProgram(
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

		vao = new VAO();
		vbo = new VBO();
		ebo = new VBO();

		if (color == null)
			color = RGBA.WHITE;

		prog.use();
		prog.setUniform("color", color.toVec4f());

		vao.bind();
		vbo.bind(GL_ARRAY_BUFFER);
		vbo.buffer(GL_ARRAY_BUFFER, DataUtils.createFloatBuffer(vertices), GL_STATIC_DRAW);

		ebo.bind(GL_ELEMENT_ARRAY_BUFFER);
		ebo.buffer(GL_ELEMENT_ARRAY_BUFFER, DataUtils.createIntBuffer(indices), GL_STATIC_DRAW);

		glEnableVertexAttribArray(0);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		glDisableVertexAttribArray(0);

		vbo.unBind(GL_ARRAY_BUFFER);
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
		glDrawElements(GL_TRIANGLES, indices.length, GL_UNSIGNED_INT, 0);
		glDisableVertexAttribArray(0);
	}

	/**
	 * Delete the model and release spaces.
	 */
	public void abrogate() {
		prog.delete();
		vao.delete();
		vbo.delete();
		ebo.delete();
	}

}
