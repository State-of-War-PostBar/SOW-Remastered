package cn.stateofwar.sowr.graphic.render;

import static org.lwjgl.opengl.GL45.*;
import static org.lwjgl.stb.STBImage.stbi_failure_reason;
import static org.lwjgl.stb.STBImage.stbi_load;
import static org.lwjgl.stb.STBImage.stbi_load_from_memory;
import static org.lwjgl.stb.STBImage.stbi_set_flip_vertically_on_load;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.system.MemoryStack;

import cn.stateofwar.sowr.util.Logger;
import cn.stateofwar.sowr.util.Utils;

public class Texture2D extends Texture {

	private static final Logger LOGGER = new Logger("Render");

	private final int id;

	private int width;

	private int height;

	private Texture2D() {
		id = glCreateTextures(GL_TEXTURE_2D);
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int _width) {
		if (_width > 0)
			width = _width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int _height) {
		if (_height > 0)
			height = _height;
	}

	public void bind() {
		glBindTexture(GL_TEXTURE_2D, id);
	}

	public void bind(int sampler) {
		if (sampler >= 0 && sampler <= 31)
			glActiveTexture(GL_TEXTURE0 + sampler);
		glBindTexture(GL_TEXTURE_2D, id);
	}

	public void setPar(int parameter, int value) {
		glTexParameteri(GL_TEXTURE_2D, parameter, value);
	}

	public void setPar(int parameter, FloatBuffer value) {
		glTexParameterfv(GL_TEXTURE_2D, parameter, value);
	}

	public void upload(int width, int height, ByteBuffer data) {
		upload(GL_RGBA8, width, height, GL_RGBA, data);
	}

	public void upload(int internalFormat, int width, int height, int format, ByteBuffer data) {
		glTexImage2D(GL_TEXTURE_2D, 0, internalFormat, width, height, 0, format, GL_UNSIGNED_BYTE, data);
		glGenerateMipmap(GL_TEXTURE_2D);
	}

	public void unbind() {
		glBindTexture(GL_TEXTURE_2D, 0);
	}

	@Override
	public void delete() {
		glDeleteTextures(id);
	}

	public static Texture2D loadTexture(String path) {
		ByteBuffer data;
		int width, height;

		try (MemoryStack stack = MemoryStack.stackPush()) {
			IntBuffer image_width = stack.mallocInt(1);
			IntBuffer image_height = stack.mallocInt(1);
			IntBuffer components = stack.mallocInt(1);

			stbi_set_flip_vertically_on_load(true);
			data = stbi_load(path, image_width, image_height, components, 4);

			if (data == null) {
				LOGGER.error("Failed to load a texture file from " + path + " !");
				LOGGER.error(stbi_failure_reason());
				return null;
			}

			width = image_width.get();
			height = image_height.get();
		}

		LOGGER.info("Loaded a texture at path " + path + ".");
		return createTexture(width, height, data);
	}

	public static Texture2D loadTextureA(String path) {
		ByteBuffer data = null;
		int width = 0, height = 0;

		try (MemoryStack stack = MemoryStack.stackPush()) {
			IntBuffer image_width = stack.mallocInt(1);
			IntBuffer image_height = stack.mallocInt(1);
			IntBuffer components = stack.mallocInt(1);

			ByteBuffer image = Utils.getResource(path).flip();

			stbi_set_flip_vertically_on_load(true);
			data = stbi_load_from_memory(image, image_width, image_height, components, 4);

			if (data == null) {
				LOGGER.error("Failed to load a texture file from " + path + " !");
				LOGGER.error(stbi_failure_reason());
				return null;
			}

			width = image_width.get();
			height = image_height.get();
		} catch (IOException e) {
			LOGGER.error("Failed to load a texture file from " + path + " !");
			LOGGER.error(e.getLocalizedMessage());
			e.printStackTrace();
		}

		LOGGER.info("Loaded a texture at path [jar]" + path + ".");
		return createTexture(width, height, data);
	}

	private static Texture2D createTexture(int width, int height, ByteBuffer data) {
		Texture2D texture = new Texture2D();

		texture.setWidth(width);
		texture.setHeight(height);
		texture.bind();
		texture.setPar(GL_TEXTURE_WRAP_S, GL_CLAMP_TO_BORDER);
		texture.setPar(GL_TEXTURE_WRAP_T, GL_CLAMP_TO_BORDER);
		texture.setPar(GL_TEXTURE_BORDER_COLOR, RGBA.BLACK.toFloatBuffer().flip());
		texture.setPar(GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		texture.setPar(GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		texture.upload(GL_RGBA8, width, height, GL_RGBA, data);
		texture.unbind();

		return texture;
	}

}
