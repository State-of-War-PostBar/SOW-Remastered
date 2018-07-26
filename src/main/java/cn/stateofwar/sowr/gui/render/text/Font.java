package cn.stateofwar.sowr.gui.render.text;

import static java.awt.Font.MONOSPACED;
import static java.awt.Font.PLAIN;
import static java.awt.Font.TRUETYPE_FONT;

import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.system.MemoryUtil;

import cn.stateofwar.sowr.gui.render.RGBAColor;
import cn.stateofwar.sowr.gui.render.Renderer;
import cn.stateofwar.sowr.gui.render.Texture;
import cn.stateofwar.sowr.util.Logger;

/**
 * The font textures.
 */
public class Font {

	private static final Logger logger = new Logger("Render");

	/**
	 * Contains the glyphs for each char.
	 */
	private final Map<Character, FontGlyph> glyphs;
	/**
	 * Contains the font texture.
	 */
	private final Texture texture;

	/**
	 * Height of the font.
	 */
	private int fontHeight;

	/**
	 * Create a default antialiased font with monospaced glyphs and default size 16.
	 */
	public Font() {
		this(new java.awt.Font(MONOSPACED, PLAIN, 16), true);
	}

	/**
	 * Create a default font with monospaced glyphs and default size 16.
	 *
	 * @param antiAlias Whether the font should be antialiased or not.
	 */
	public Font(boolean antiAlias) {
		this(new java.awt.Font(MONOSPACED, PLAIN, 16), antiAlias);
	}

	/**
	 * Create a default antialiased font with monospaced glyphs and specified size.
	 *
	 * @param size Font size.
	 */
	public Font(int size) {
		this(new java.awt.Font(MONOSPACED, PLAIN, size), true);
	}

	/**
	 * Create a default font with monospaced glyphs and specified size.
	 *
	 * @param size      Font size.
	 * 
	 * @param antiAlias Whether the font should be antialiased or not.
	 */
	public Font(int size, boolean antiAlias) {
		this(new java.awt.Font(MONOSPACED, PLAIN, size), antiAlias);
	}

	/**
	 * Create a antialiased Font from an input stream.
	 *
	 * @param in   The input stream.
	 * 
	 * @param size Font size.
	 *
	 * @throws FontFormatException if fontFile does not contain the required font
	 *                             tables for the specified format.
	 * 
	 * @throws IOException         If font can't be read.
	 */
	public Font(InputStream in, int size) throws FontFormatException, IOException {
		this(in, size, true);
	}

	/**
	 * Create a Font from an input stream.
	 *
	 * @param in        The input stream.
	 * 
	 * @param size      Font size.
	 * 
	 * @param antiAlias Whether the font should be antialiased or not.
	 *
	 * @throws FontFormatException if fontFile does not contain the required font
	 *                             tables for the specified format.
	 * 
	 * @throws IOException         If font can't be read.
	 */
	public Font(InputStream in, int size, boolean antiAlias) throws FontFormatException, IOException {
		this(java.awt.Font.createFont(TRUETYPE_FONT, in).deriveFont(PLAIN, size), antiAlias);
	}

	/**
	 * Creates a antialiased font from an AWT Font.
	 *
	 * @param font The AWT Font
	 */
	public Font(java.awt.Font font) {
		this(font, true);
	}

	/**
	 * Creates a font from an AWT Font.
	 *
	 * @param font      The AWT Font.
	 * 
	 * @param antiAlias Whether the font should be antialiased or not.
	 */
	public Font(java.awt.Font font, boolean antiAlias) {
		glyphs = new HashMap<>();
		texture = createFontTexture(font, antiAlias);
	}

	/**
	 * Creates a font texture from specified AWT font.
	 *
	 * @param font      The AWT font.
	 * 
	 * @param antiAlias Whether the font should be antialiased or not.
	 */
	private Texture createFontTexture(java.awt.Font font, boolean antiAlias) {
		int imageWidth = 0;
		int imageHeight = 0;

		/* Start at char #32, because ASCII 0 to 31 are just control codes. */
		for (int i = 32; i < 256; i++) {
			if (i == 127) {
				/* Skip DEL control code. */
				continue;
			}
			char c = (char) i;
			BufferedImage ch = createCharImage(font, c, antiAlias);
			if (ch == null) {
				/* If char image is null that font does not contain the character. */
				continue;
			}

			imageWidth += ch.getWidth();
			imageHeight = Math.max(imageHeight, ch.getHeight());
		}

		fontHeight = imageHeight;

		/* Image for the texture. */
		BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = image.createGraphics();

		int x = 0;

		/*
		 * Create image for the standard chars, again we omit ASCII 0 to 31 because they
		 * are just control codes.
		 */
		for (int i = 32; i < 256; i++) {
			if (i == 127) {
				/* Skip DEL control code. */
				continue;
			}
			char c = (char) i;
			BufferedImage charImage = createCharImage(font, c, antiAlias);
			if (charImage == null) {
				/* If char image is null that font does not contain the character. */
				continue;
			}

			int charWidth = charImage.getWidth();
			int charHeight = charImage.getHeight();

			/* Create glyph and draw char on image. */
			FontGlyph ch = new FontGlyph(charWidth, charHeight, x, image.getHeight() - charHeight, 0f);
			g.drawImage(charImage, x, 0, null);
			x += ch.width;
			glyphs.put(c, ch);
		}

		/* Flip image Horizontal to get the origin to bottom left. */
		AffineTransform transform = AffineTransform.getScaleInstance(1f, -1f);
		transform.translate(0, -image.getHeight());
		AffineTransformOp operation = new AffineTransformOp(transform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		image = operation.filter(image, null);

		/* Get charWidth and charHeight of image. */
		int width = image.getWidth();
		int height = image.getHeight();

		/* Get pixel data of image. */
		int[] pixels = new int[width * height];
		image.getRGB(0, 0, width, height, pixels, 0, width);

		/* Put pixel data into a ByteBuffer. */
		ByteBuffer buffer = MemoryUtil.memAlloc(width * height * 4);
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				int pixel = pixels[i * width + j];

				buffer.put((byte) ((pixel >> 16) & 0xFF)); /* r */
				buffer.put((byte) ((pixel >> 8) & 0xFF)); /* g */
				buffer.put((byte) (pixel & 0xFF)); /* b */
				buffer.put((byte) ((pixel >> 24) & 0xFF)); /* a */
			}
		}
		buffer.flip();

		/* Create texture. */
		Texture fontTexture = Texture.createTexture(width, height, buffer);
		MemoryUtil.memFree(buffer);

		logger.info("Successfully created a font texture.");

		return fontTexture;
	}

	/**
	 * Creates a char image from specified AWT font and char.
	 *
	 * @param font      The AWT font.
	 * 
	 * @param c         The char.
	 * 
	 * @param antiAlias Whether the char should be antialiased or not.
	 */
	private static BufferedImage createCharImage(java.awt.Font font, char c, boolean antiAlias) {
		BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = image.createGraphics();
		if (antiAlias) {
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		}
		g.setFont(font);
		FontMetrics metrics = g.getFontMetrics();
		g.dispose();

		int charWidth = metrics.charWidth(c);
		int charHeight = metrics.getHeight();

		if (charWidth == 0)
			return null;

		/* Create image for holding the char. */
		image = new BufferedImage(charWidth, charHeight, BufferedImage.TYPE_INT_ARGB);
		g = image.createGraphics();
		if (antiAlias) {
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		}
		g.setFont(font);
		g.setPaint(java.awt.Color.WHITE);
		g.drawString(String.valueOf(c), 0, metrics.getAscent());
		g.dispose();

		return image;
	}

	/**
	 * Gets the width of the specified text.
	 *
	 * @param text The text
	 */
	public int getWidth(CharSequence text) {
		int width = 0;
		int lineWidth = 0;
		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			if (c == '\n') {
				width = Math.max(width, lineWidth);
				lineWidth = 0;
				continue;
			}
			if (c == '\r')
				continue;

			FontGlyph g = glyphs.get(c);
			lineWidth += g.width;
		}
		width = Math.max(width, lineWidth);
		return width;
	}

	/**
	 * Gets the height of the specified text.
	 *
	 * @param text The text
	 */
	public int getHeight(CharSequence text) {
		int height = 0;
		int lineHeight = 0;
		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			if (c == '\n') {
				height += lineHeight;
				lineHeight = 0;
				continue;
			}
			if (c == '\r')
				continue;

			FontGlyph g = glyphs.get(c);
			lineHeight = Math.max(lineHeight, g.height);
		}
		height += lineHeight;
		return height;
	}

	/**
	 * Draw text at the specified position and color.
	 *
	 * @param renderer The renderer to use.
	 * 
	 * @param text     Text to draw.
	 * 
	 * @param x        X coordinate of the text position.
	 * 
	 * @param y        Y coordinate of the text position.
	 * 
	 * @param c        Color to use.
	 */
	public void drawText(Renderer renderer, CharSequence text, float x, float y, RGBAColor c) {
		int textHeight = getHeight(text);

		float drawX = x;
		float drawY = y;
		if (textHeight > fontHeight) {
			drawY += textHeight - fontHeight;
		}

		texture.bind();
		renderer.start();
		for (int i = 0; i < text.length(); i++) {
			char ch = text.charAt(i);
			if (ch == '\n') {
				drawY -= fontHeight;
				drawX = x;
				continue;
			}
			if (ch == '\r')
				continue;

			FontGlyph g = glyphs.get(ch);
			renderer.drawTextureRegion(texture, drawX, drawY, (float) g.x, (float) g.y, (float) g.width,
					(float) g.height, c);
			drawX += g.width;
		}
		renderer.end();
	}

	/**
	 * Draw text at the specified position.
	 *
	 * @param renderer The renderer to use.
	 * 
	 * @param text     Text to draw.
	 * 
	 * @param x        X coordinate of the text position.
	 * 
	 * @param y        Y coordinate of the text position.
	 */
	public void drawText(Renderer renderer, CharSequence text, float x, float y) {
		drawText(renderer, text, x, y, RGBAColor.WHITE);
	}

	/**
	 * Disposes the font.
	 */
	public void dispose() {
		texture.delete();
	}

}
