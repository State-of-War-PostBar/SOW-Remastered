package cn.stateofwar.sowr.gui.render;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

/**
 * Renderer for the models.
 */
public class Renderer {

	/**
	 * Draw a textureless and colorless model with white color.
	 * 
	 * @param model The model to draw.
	 */
	public void drawRawModel(RawModel model) {
		model.vao.bind();

		glEnableVertexAttribArray(0);
		glDrawElements(GL_TRIANGLES, model.vertexCount, GL_UNSIGNED_INT, 0);
		glDisableVertexAttribArray(0);

		model.vao.unbind();
	}

	/**
	 * Draw a textured model.
	 * 
	 * @param model   The model to draw.
	 * 
	 * @param sampler The OpenGL sampler to use. For default, put 0.
	 */
	public void drawTexturedModel(TexturedModel model, int sampler) {
		model.vao.bind();

		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		model.texture.bind(sampler);
		glDrawElements(GL_TRIANGLES, model.vertexCount, GL_UNSIGNED_INT, 0);
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);

		model.vao.unbind();
	}

	/**
	 * Render mode of OpenGL.
	 */
	static enum RenderMode {
		LINE, LINE_STRIP, TRIANGLE, TRIANGLE_FAN, QUAD, POLYGON;

		/**
		 * Convert a render mode to an OpenGL constant.
		 * 
		 * @param arg The render mode.
		 * 
		 * @return OpenGL constant of the render mode.
		 */
		static int toGLConst(RenderMode arg) {
			switch (arg) {
			case LINE:
				return GL_LINE;
			case LINE_STRIP:
				return GL_LINE_STRIP;
			case TRIANGLE:
				return GL_TRIANGLES;
			case TRIANGLE_FAN:
				return GL_TRIANGLE_FAN;
			case QUAD:
				return GL_QUADS;
			case POLYGON:
				return GL_POLYGON;
			default:
				return GL_TRIANGLES;
			}
		}
	}
}
