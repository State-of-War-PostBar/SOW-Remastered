package cn.stateofwar.sowr.gui.render;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

public class Renderer {

	public void drawRawModel(RawModel model) {
		model.vao.bind();

		glEnableVertexAttribArray(0);
		glDrawElements(GL_TRIANGLES, model.vertexCount, GL_UNSIGNED_INT, 0);
		glDisableVertexAttribArray(0);

		model.vao.unBind();
	}

	public void drawTexturedModel(TexturedModel model, int sampler) {
		model.vao.bind();

		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		model.texture.bind(sampler);
		glDrawElements(GL_TRIANGLES, model.vertexCount, GL_UNSIGNED_INT, 0);
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);

		model.vao.unBind();
	}
}
