package cn.stateofwar.sowr.graphic.render;

import static org.lwjgl.opengl.GL45.*;
import static org.lwjgl.stb.STBImage.stbi_failure_reason;
import static org.lwjgl.stb.STBImage.stbi_load;
import static org.lwjgl.stb.STBImage.stbi_set_flip_vertically_on_load;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.system.MemoryStack;

import cn.stateofwar.sowr.util.Logger;

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
		ByteBuffer image;
		int width, height;

		try (MemoryStack stack = MemoryStack.stackPush()) {
			IntBuffer w = stack.mallocInt(1);
			IntBuffer h = stack.mallocInt(1);
			IntBuffer comp = stack.mallocInt(1);

			stbi_set_flip_vertically_on_load(true);
			image = stbi_load(path, w, h, comp, 4);

			if (image == null) {
				LOGGER.error("Failed to load a texture file from " + path + " !");
				LOGGER.error(stbi_failure_reason());
			}

			width = w.get();
			height = h.get();
		}

		LOGGER.info("Loaded a texture at path " + path + ".");
		return createTexture(width, height, image);
	}

	public static Texture2D loadTextureA(String path) {
		ByteBuffer pixels = null;
		int width = 0, height = 0;

		try {
			InputStream stream = ClassLoader.getSystemResourceAsStream(path);
			BufferedImage image = ImageIO.read(stream);

			width = image.getWidth();
			height = image.getHeight();

			int[] raw_pixels = image.getRGB(0, 0, width, height, null, 0, width);
			pixels = BufferUtils.createByteBuffer(width * height * 4);

			for (int i = 0; i < width; i++)
				for (int j = 0; j < height; j++) {
					int pixel = raw_pixels[i * width + j];
					pixels.put((byte) ((pixel >> 020) & 0xFF));
					pixels.put((byte) ((pixel >> 010) & 0xFF));
					pixels.put((byte) (pixel & 0xFF));
					pixels.put((byte) ((pixel >> 030) & 0xFF));
				}

			pixels.flip();
		} catch (IOException e) {
			LOGGER.error("Failed to load a texture file from [jar]" + path + " !");
			LOGGER.error(e.getLocalizedMessage());
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			LOGGER.fatal("The texture file of path " + path + " is too large! Try to add it in local path.");
			LOGGER.error(e.getLocalizedMessage());
			e.printStackTrace();
		}

		LOGGER.info("Loaded a texture at path [jar]" + path + ".");
		return createTexture(width, height, pixels);
	}

	private static Texture2D createTexture(int width, int height, ByteBuffer data) {
		Texture2D texture = new Texture2D();

		texture.setWidth(width);
		texture.setHeight(height);
		texture.bind();
		texture.setPar(GL_TEXTURE_WRAP_S, GL_CLAMP_TO_BORDER);
		texture.setPar(GL_TEXTURE_WRAP_T, GL_CLAMP_TO_BORDER);
		texture.setPar(GL_TEXTURE_BORDER_COLOR, RGBA.BLACK.toFloatBuffer());
		texture.setPar(GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		texture.setPar(GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		texture.upload(GL_RGBA8, width, height, GL_RGBA, data);
		texture.unbind();

		return texture;
	}

}
