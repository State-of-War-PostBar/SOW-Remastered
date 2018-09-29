package cn.stateofwar.sowr.gui.render;

import static org.lwjgl.opengl.GL45.*;
import static org.lwjgl.stb.STBImage.stbi_failure_reason;
import static org.lwjgl.stb.STBImage.stbi_load;
import static org.lwjgl.stb.STBImage.stbi_set_flip_vertically_on_load;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
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

	/** ID of this texture. */
	private int id;

	/** Width of the texture. */
	private int width;

	/** Height of the texture. */
	private int height;

	public Texture() {
		id = glGenTextures();
	}

	/**
	 * Bind the texture to current render procedure.
	 */
	public void bind() {
		glBindTexture(GL_TEXTURE_2D, id);
	}

	/**
	 * Bind the texture to current render procedure with sampler.
	 */
	public void bind(int sampler) {
		if (sampler >= 0 && sampler <= 31)
			glActiveTexture(GL_TEXTURE0 + sampler);
		glBindTexture(GL_TEXTURE_2D, id);
	}

	/**
	 * Set a parameter of the texture.
	 * 
	 * @param par Name of the parameter.
	 * 
	 * @param val Value to set.
	 */
	public void setPar(int par, int val) {
		glTexParameteri(GL_TEXTURE_2D, par, val);
	}

	public void setPar(int par, FloatBuffer val) {
		glTexParameterfv(GL_TEXTURE_2D, par, val);
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
	public void upload(int width, int height, ByteBuffer data) {
		upload(GL_RGBA8, width, height, GL_RGBA, data);
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
	public void upload(int internalFormat, int width, int height, int format, ByteBuffer data) {
		glTexImage2D(GL_TEXTURE_2D, 0, internalFormat, width, height, 0, format, GL_UNSIGNED_BYTE, data);
		glGenerateMipmap(GL_TEXTURE_2D);
	}

	/**
	 * Delete the texture.
	 */
	public void delete() {
		glDeleteTextures(id);
	}

	/**
	 * Get the width of the texture.
	 * 
	 * @return Width of the texture.
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Set the width of the texture.
	 * 
	 * @param width Width of the texture.
	 */
	public void setWidth(int _width) {
		if (_width > 0)
			width = _width;
	}

	/**
	 * Get the height of the texture.
	 * 
	 * @return Height of the texture.
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Get the height of the texture.
	 * 
	 * @return Height of the texture.
	 */
	public void setHeight(int _height) {
		if (_height > 0)
			height = _height;
	}

	/**
	 * Load texture from file.
	 *
	 * @param fp Path of the texture file.
	 * 
	 * @return Instance of the created texture.
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

			if (image == null)
				logger.error("Failed to load a texture file from " + fp + " !" + Utils.nl() + stbi_failure_reason());

			width = w.get();
			height = h.get();
		}

		logger.info("Loaded a texture at path " + fp + ".");
		return createTexture(width, height, image);
	}

	/**
	 * Create a texture with specified width, height and data.
	 * 
	 * @param width  Width of the texture.
	 * 
	 * @param height Height of the texture.
	 * 
	 * @param data   Picture Data in RGBA format.
	 * 
	 * @return Instance of the created texture.
	 */
	private static Texture createTexture(int width, int height, ByteBuffer data) {
		Texture texture = new Texture();
		texture.setWidth(width);
		texture.setHeight(height);

		texture.bind();
		texture.setPar(GL_TEXTURE_WRAP_S, GL_CLAMP_TO_BORDER);
		texture.setPar(GL_TEXTURE_WRAP_T, GL_CLAMP_TO_BORDER);
		texture.setPar(GL_TEXTURE_BORDER_COLOR, RGBA.WHITE.toFloatBuffer());
		texture.setPar(GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		texture.setPar(GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		texture.upload(GL_RGBA8, width, height, GL_RGBA, data);

		return texture;
	}

}
