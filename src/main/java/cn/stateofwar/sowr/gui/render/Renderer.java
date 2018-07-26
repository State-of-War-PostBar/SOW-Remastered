package cn.stateofwar.sowr.gui.render;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import cn.stateofwar.sowr.gui.Graphic;
import cn.stateofwar.sowr.gui.render.Shader.ShaderType;
import cn.stateofwar.sowr.gui.render.text.Font;
import cn.stateofwar.sowr.util.DataUtils;
import cn.stateofwar.sowr.util.Logger;

public class Renderer {

	private static final Logger logger = new Logger("Render");

	private VAO vao;
	private VBO vbo;
	private ShaderProgram prog;

	private FloatBuffer vertices;
	private int numVertices;
	private boolean drawing;

	private Font font;

	/** Initializes the renderer. */
	public void init() {
		/* Setup shader programs */
		setupShaderProgram();

		/* Enable blending */
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		/* Create fonts */
		try {
			font = new Font(ClassLoader.getSystemClassLoader().getResourceAsStream("gui/font/Inconsolata.otf"), 16);
		} catch (Exception ex) {
			logger.error("Failed to load build-in font. Loading a system font...");
			font = new Font();
		}

		logger.info("Initialized a renderer.");

	}

	/**
	 * Clears the drawing area.
	 */
	public void clear() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}

	/**
	 * Begin rendering.
	 */
	public void start() {
		if (drawing) {
			throw new IllegalStateException("Renderer is already drawing!");
		}
		drawing = true;
		numVertices = 0;
	}

	/**
	 * End rendering and pass parameters to GPU.
	 */
	public void end() {
		if (!drawing) {
			throw new IllegalStateException("Renderer isn't drawing!");
		}
		drawing = false;
		flush();
	}

	/**
	 * Flush the data to the GPU to let it get rendered.
	 */
	public void flush() {
		if (numVertices > 0) {
			vertices.flip();

			if (vao != null)
				vao.bind();
			else {
				vbo.bind(GL_ARRAY_BUFFER);
				specifyVertexAttributes();
			}
			prog.use();

			/* Upload the new vertex data. */
			vbo.bind(GL_ARRAY_BUFFER);
			vbo.uploadSubData(GL_ARRAY_BUFFER, 0, vertices);

			/* Draw batch. */
			glDrawArrays(GL_TRIANGLES, 0, numVertices);

			/* Clear vertex data for next batch. */
			vertices.clear();
			numVertices = 0;
		}
	}

	/**
	 * Draw the currently bound texture on specified coordinates.
	 *
	 * @param texture Used for getting width and height of the texture.
	 * 
	 * @param x       X position of the texture.
	 * 
	 * @param y       Y position of the texture.
	 */
	public void drawTexture(Texture texture, float x, float y) {
		drawTexture(texture, x, y, RGBAColor.WHITE);
	}

	/**
	 * Draw the currently bound texture on specified coordinates and with specified
	 * color.
	 *
	 * @param texture Used for getting width and height of the texture.
	 * 
	 * @param x       X position of the texture.
	 * 
	 * @param y       Y position of the texture.
	 * 
	 * @param c       The color to use.
	 */
	public void drawTexture(Texture texture, float x, float y, RGBAColor c) {
		/* Vertex positions */
		float x1 = x;
		float y1 = y;
		float x2 = x1 + texture.getWidth();
		float y2 = y1 + texture.getHeight();

		/* Texture coordinates */
		float s1 = 0.0f;
		float t1 = 0.0f;
		float s2 = 1.0f;
		float t2 = 1.0f;

		drawTextureRegion(x1, y1, x2, y2, s1, t1, s2, t2, c);
	}

	/**
	 * Draw a texture region with the currently bound texture on specified
	 * coordinates.
	 *
	 * @param texture   Used for getting width and height of the texture.
	 * 
	 * @param x         X position of the texture.
	 * 
	 * @param y         Y position of the texture.
	 * 
	 * @param regX      X position of the texture region.
	 * 
	 * @param regY      Y position of the texture region.
	 * 
	 * @param regWidth  Width of the texture region.
	 * 
	 * @param regHeight Height of the texture region.
	 */
	public void drawTextureRegion(Texture texture, float x, float y, float regX, float regY, float regWidth,
			float regHeight) {
		drawTextureRegion(texture, x, y, regX, regY, regWidth, regHeight, RGBAColor.WHITE);
	}

	/**
	 * Draw a texture region with the currently bound texture on specified
	 * coordinates.
	 *
	 * @param texture   Used for getting width and height of the texture.
	 * 
	 * @param x         X position of the texture.
	 * 
	 * @param y         Y position of the texture.
	 * 
	 * @param regX      X position of the texture region.
	 * 
	 * @param regY      Y position of the texture region.
	 * 
	 * @param regWidth  Width of the texture region.
	 * 
	 * @param regHeight Height of the texture region.
	 * 
	 * @param c         The color to use.
	 */
	public void drawTextureRegion(Texture texture, float x, float y, float regX, float regY, float regWidth,
			float regHeight, RGBAColor c) {
		/* Vertex positions */
		float x1 = x;
		float y1 = y;
		float x2 = x + regWidth;
		float y2 = y + regHeight;

		/* Texture coordinates */
		float s1 = regX / texture.getWidth();
		float t1 = regY / texture.getHeight();
		float s2 = (regX + regWidth) / texture.getWidth();
		float t2 = (regY + regHeight) / texture.getHeight();

		drawTextureRegion(x1, y1, x2, y2, s1, t1, s2, t2, c);
	}

	/**
	 * Draw a texture region with the currently bound texture on specified
	 * coordinates.
	 *
	 * @param x1 Bottom left x position.
	 * 
	 * @param y1 Bottom left y position.
	 * 
	 * @param x2 Top right x position.
	 * 
	 * @param y2 Top right y position.
	 * 
	 * @param s1 Bottom left s coordinate.
	 * 
	 * @param t1 Bottom left t coordinate.
	 * 
	 * @param s2 Top right s coordinate.
	 * 
	 * @param t2 Top right t coordinate.
	 */
	public void drawTextureRegion(float x1, float y1, float x2, float y2, float s1, float t1, float s2, float t2) {
		drawTextureRegion(x1, y1, x2, y2, s1, t1, s2, t2, RGBAColor.WHITE);
	}

	/**
	 * Draw a texture region with the currently bound texture on specified
	 * coordinates.
	 *
	 * @param x1 Bottom left x position.
	 * 
	 * @param y1 Bottom left y position.
	 * 
	 * @param x2 Top right x position.
	 * 
	 * @param y2 Top right y position.
	 * 
	 * @param s1 Bottom left s coordinate.
	 * 
	 * @param t1 Bottom left t coordinate.
	 * 
	 * @param s2 Top right s coordinate.
	 * 
	 * @param t2 Top right t coordinate.
	 * 
	 * @param c  The color to use.
	 */
	public void drawTextureRegion(float x1, float y1, float x2, float y2, float s1, float t1, float s2, float t2,
			RGBAColor c) {
		if (vertices.remaining() < 7 * 6) {
			flush();
		}

		float r = c.getRed();
		float g = c.getGreen();
		float b = c.getBlue();
		float a = c.getAlpha();

		vertices.put(x1).put(y1).put(r).put(g).put(b).put(a).put(s1).put(t1);
		vertices.put(x1).put(y2).put(r).put(g).put(b).put(a).put(s1).put(t2);
		vertices.put(x2).put(y2).put(r).put(g).put(b).put(a).put(s2).put(t2);

		vertices.put(x1).put(y1).put(r).put(g).put(b).put(a).put(s1).put(t1);
		vertices.put(x2).put(y2).put(r).put(g).put(b).put(a).put(s2).put(t2);
		vertices.put(x2).put(y1).put(r).put(g).put(b).put(a).put(s2).put(t1);

		numVertices += 6;
	}

	/**
	 * Draw text at the specified position.
	 *
	 * @param text Text to draw.
	 * 
	 * @param x    X coordinate of the text position.
	 * 
	 * @param y    Y coordinate of the text position.
	 */
	public void drawText(CharSequence text, float x, float y) {
		font.drawText(this, text, x, y);
	}

	/**
	 * Draw text at the specified position and color.
	 *
	 * @param text Text to draw.
	 * 
	 * @param x    X coordinate of the text position.
	 * 
	 * @param y    Y coordinate of the text position.
	 * 
	 * @param c    Color to use.
	 */
	public void drawText(CharSequence text, float x, float y, RGBAColor c) {
		font.drawText(this, text, x, y, c);
	}

	/**
	 * Dispose renderer and clean up its used data.
	 */
	public void dispose() {
		MemoryUtil.memFree(vertices);

		if (vao != null) {
			vao.delete();
		}
		vbo.delete();
		prog.delete();
	}

	/**
	 * Calculate total width of a text.
	 *
	 * @param text The text
	 */
	public int getTextWidth(CharSequence text) {
		return font.getWidth(text);
	}

	/**
	 * Calculate total height of a text.
	 *
	 * @param text The text
	 */
	public int getTextHeight(CharSequence text) {
		return font.getHeight(text);
	}

	/**
	 * Setup the default shader program.
	 */
	private void setupShaderProgram() {
		vao = new VAO();
		vao.bind();

		vbo = new VBO();
		vbo.bind(GL_ARRAY_BUFFER);

		vertices = MemoryUtil.memAllocFloat(4096);
		long size = vertices.capacity() * Float.BYTES;
		vbo.uploadData(GL_ARRAY_BUFFER, size, GL_DYNAMIC_DRAW);

		numVertices = 0;
		drawing = false;

		Shader vertexShader, fragmentShader;
		vertexShader = Shader.createShader("gui/render/shaders/default.vs", ShaderType.VERTEX, true);
		fragmentShader = Shader.createShader("gui/render/shaders/default.fs", ShaderType.FRAGMENT, true);

		prog = new ShaderProgram(new Shader[] { vertexShader, fragmentShader });

		/* Get width and height of framebuffer */
		long window = Graphic.win.getHandle();
		int width, height;
		try (MemoryStack stack = MemoryStack.stackPush()) {
			IntBuffer widthBuffer = stack.mallocInt(1);
			IntBuffer heightBuffer = stack.mallocInt(1);
			GLFW.glfwGetFramebufferSize(window, widthBuffer, heightBuffer);
			width = widthBuffer.get();
			height = heightBuffer.get();
		}

		prog.setUniformI("texImage", 0);

		Matrix4f model = new Matrix4f();
		prog.setUniformM4f("model", model);

		/* Set view matrix to identity matrix */
		Matrix4f view = new Matrix4f();
		prog.setUniformM4f("view", view);

		/* Set projection matrix to an orthographic projection */
		Matrix4f projection = DataUtils.orthographic(0f, width, 0f, height, -1f, 1f);
		prog.setUniformM4f("projection", projection);

	}

	/**
	 * Specify the vertex pointers.
	 */
	private void specifyVertexAttributes() {
		/* Specify Vertex Pointer */
		int posAttrib = prog.getAttributeLocation("position");
		prog.enableVertexAttribute(posAttrib);
		prog.pointVertexAttribute(posAttrib, 2, 8 * Float.BYTES, 0);

		/* Specify Color Pointer */
		int colAttrib = prog.getAttributeLocation("color");
		prog.enableVertexAttribute(colAttrib);
		prog.pointVertexAttribute(colAttrib, 4, 8 * Float.BYTES, 2 * Float.BYTES);

		/* Specify Texture Pointer */
		int texAttrib = prog.getAttributeLocation("texcoord");
		prog.enableVertexAttribute(texAttrib);
		prog.pointVertexAttribute(texAttrib, 2, 8 * Float.BYTES, 6 * Float.BYTES);
	}

}
