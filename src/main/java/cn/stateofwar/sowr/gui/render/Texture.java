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

/**
 * A reference of the 2D texture of OpenGL. All textures of SOW-R must be .png
 * format.
 */
public class Texture {

	private static final Logger logger = new Logger("Render");

	/**
	 * ID of this texture.
	 */
	private int id;

	private int width, height;

	public Texture() {
		id = glGenTextures();
	}

	/**
	 * Bind the texture.
	 */
	public void bind() {
		glBindTexture(GL_TEXTURE_2D, id);
	}

	/**
	 * Set a parameter of the texture.
	 * 
	 * @param name  Name of the parameter.
	 * 
	 * @param value Value to set.
	 */
	public void setParameter(int name, int val) {
		glTexParameteri(GL_TEXTURE_2D, name, val);
	}

	/**
	 * Upload image data with specified width and height.
	 * 
	 * @param width  Width of the image.
	 * 
	 * @param height Height of the image.
	 * 
	 * @param data   Pixel data of the image.
	 */
	public void uploadData(int width, int height, ByteBuffer data) {
		uploadData(GL_RGBA8, width, height, GL_RGBA, data);
	}

	/**
	 * Upload image data with specified internal format, width, height and image
	 * format.
	 * 
	 * @param internalFormat Internal format of the image data.
	 * 
	 * @param width          Width of the image.
	 * 
	 * @param height         Height of the image.
	 * 
	 * @param format         Format of the image data.
	 * 
	 * @param data           Pixel data of the image.
	 */
	public void uploadData(int internalFormat, int width, int height, int format, ByteBuffer data) {
		glTexImage2D(GL_TEXTURE_2D, 0, internalFormat, width, height, 0, format, GL_UNSIGNED_BYTE, data);
	}

	/**
	 * Delete the texture.
	 */
	public void delete() {
		glDeleteTextures(id);
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		if (width > 0)
			this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		if (height > 0)
			this.height = height;
	}

	/**
	 * Create a texture with specified width, height and data.
	 * 
	 * @param width  Width of the texture.
	 * 
	 * @param height Height of the texture.
	 * 
	 * @param data   Picture Data in RGBA format.
	 */
	public static Texture createTexture(int width, int height, ByteBuffer data) {
		Texture texture = new Texture();
		texture.setWidth(width);
		texture.setHeight(height);

		texture.bind();

		texture.setParameter(GL_TEXTURE_WRAP_S, GL_CLAMP_TO_BORDER);
		texture.setParameter(GL_TEXTURE_WRAP_T, GL_CLAMP_TO_BORDER);
		texture.setParameter(GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		texture.setParameter(GL_TEXTURE_MAG_FILTER, GL_NEAREST);

		texture.uploadData(GL_RGBA8, width, height, GL_RGBA, data);

		return texture;
	}

	/**
	 * Load texture from file.
	 *
	 * @param fp File path of the texture.
	 */
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
				throw new RuntimeException("Failed to load a texture file!" + Utils.nl() + stbi_failure_reason());
			}

			width = w.get();
			height = h.get();
		}

		logger.info("Loaded a texture at path " + fp + ".");

		return createTexture(width, height, image);
	}

}
