package cn.stateofwar.sowr.gui.render;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.stb.STBImage.stbi_failure_reason;
import static org.lwjgl.stb.STBImage.stbi_load;
import static org.lwjgl.stb.STBImage.stbi_set_flip_vertically_on_load;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.system.MemoryStack;

import cn.stateofwar.sowr.util.Logger;
import cn.stateofwar.sowr.util.Utils;

public class Texture {

	private static final Logger logger = new Logger("Render");

	private int id;

	private int width, height;

	public Texture() {
		id = glGenTextures();
	}

	public void bind() {
		glBindTexture(GL_TEXTURE_2D, id);
	}

	public void bind(int sampler) {
		if (sampler >= 0 && sampler <= 31) {
			glActiveTexture(GL_TEXTURE0 + sampler);
		}
		glBindTexture(GL_TEXTURE_2D, id);
	}

	public void setPar(int par, int val) {
		glTexParameteri(GL_TEXTURE_2D, par, val);
	}

	public void upload(int width, int height, ByteBuffer data) {
		upload(GL_RGBA8, width, height, GL_RGBA, data);
	}

	public void upload(int internalFormat, int width, int height, int format, ByteBuffer data) {
		glTexImage2D(GL_TEXTURE_2D, 0, internalFormat, width, height, 0, format, GL_UNSIGNED_BYTE, data);
	}

	public void delete() {
		glDeleteTextures(id);
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		if (width > 0) {
			this.width = width;
		}
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		if (height > 0) {
			this.height = height;
		}
	}

	public static Texture createTexture(int width, int height, ByteBuffer data) {
		Texture texture = new Texture();
		texture.setWidth(width);
		texture.setHeight(height);

		texture.bind();
		texture.setPar(GL_TEXTURE_WRAP_S, GL_CLAMP_TO_BORDER);
		texture.setPar(GL_TEXTURE_WRAP_T, GL_CLAMP_TO_BORDER);
		texture.setPar(GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		texture.setPar(GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		texture.upload(GL_RGBA8, width, height, GL_RGBA, data);

		return texture;
	}

	public static Texture loadTexture(String fp) {
		ByteBuffer image;
		int width, height;
		try (MemoryStack stack = MemoryStack.stackPush()) {
			IntBuffer w = stack.mallocInt(1);
			IntBuffer h = stack.mallocInt(1);
			IntBuffer comp = stack.mallocInt(1);

			stbi_set_flip_vertically_on_load(true);
			image = stbi_load(fp, w, h, comp, 4);

			if (image == null) {
				logger.error("Failed to load a texture file!" + Utils.nl() + stbi_failure_reason());
			}

			width = w.get();
			height = h.get();
		}

		logger.info("Loaded a texture at path " + fp + ".");
		return createTexture(width, height, image);
	}

}
