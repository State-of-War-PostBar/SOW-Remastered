package cn.stateofwar.sowr.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import com.google.common.io.Files;
import com.google.common.io.Resources;

import cn.stateofwar.sowr.References;

/**
 * Small utility library.
 */
public class Utils {

	/**
	 * Types of operating systems.
	 */
	public static enum OSType {
		LINUX, WINDOWS, MAC, OTHERS
	}

	/**
	 * Get the current operating system.
	 * 
	 * @return Current operating system.
	 */
	public static OSType getOS() {
		String os_name = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);

		if (os_name.indexOf("nux") >= 0)
			return OSType.LINUX;
		else if (os_name.indexOf("win") >= 0)
			return OSType.WINDOWS;
		else if ((os_name.indexOf("mac") >= 0) || (os_name.indexOf("darwin") >= 0))
			return OSType.MAC;

		return OSType.OTHERS;
	}

	/**
	 * Generate a line separator.
	 * 
	 * @return Line separator for current OS.
	 */
	public static String nl() {
		return System.lineSeparator();
	}

	/**
	 * Get the current system date.
	 * 
	 * @param separator Separator between elements.
	 * 
	 * @return Current date.
	 */
	public static String getSysDate(char separator) {
		Calendar calendar = Calendar.getInstance();

		String year = Integer.toString(calendar.get(Calendar.YEAR));
		String month = Integer.toString(calendar.get(Calendar.MONTH));
		String day = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));

		return year + separator + month + separator + day;
	}

	/**
	 * Get the current system time.
	 * 
	 * @param separator Separator between elements.
	 * 
	 * @return Current time.
	 */
	public static String getSysTime(char separator) {
		Calendar calendar = Calendar.getInstance();

		String hour = Integer.toString(calendar.get(Calendar.HOUR_OF_DAY));
		hour = hour.length() == 2 ? hour : '0' + hour;

		String minute = Integer.toString(calendar.get(Calendar.MINUTE));
		minute = minute.length() == 2 ? minute : '0' + minute;

		String second = Integer.toString(calendar.get(Calendar.SECOND));
		second = second.length() == 2 ? second : '0' + second;

		return hour + separator + minute + separator + second;
	}

	/**
	 * Create a file.
	 * 
	 * @param path     Path of the file.
	 * 
	 * @param override Override the old file (if there is one).
	 * 
	 * @throws IOException
	 */
	public static void createFile(String path, boolean override) throws IOException {
		File file = new File(path);
		File parent = file.getParentFile();

		if (parent != null && !parent.exists())
			parent.mkdirs();

		if (file.exists())
			if (override)
				file.delete();
			else
				return;

		file.createNewFile();
	}

	/**
	 * Get the data of a resource file.
	 * 
	 * @param path Path of the resource file.
	 * 
	 * @return Byte buffer read from the resource file.
	 * 
	 * @throws IOException
	 */
	public static ByteBuffer getResource(String path) throws IOException {
		return DataUtils.createByteBuffer(Resources.toByteArray(Resources.getResource(path)));
	}

	/**
	 * Get the data of a resource text file as a list of strings.<br />
	 * <i>Works only if the resource file is a pure text file.</i>
	 * 
	 * @param path Path of the resource file.
	 * 
	 * @return List of strings of the resource text file.
	 * 
	 * @throws IOException
	 */
	public static List<String> getResourceAsStrings(String path) throws IOException {
		return Resources.readLines(Resources.getResource(path), Charset.forName(References.DEFAULT_TEXT_ENCODING));
	}

	/**
	 * Convert a list of strings to a single string.
	 * 
	 * @param list     List of strings.
	 * 
	 * @param new_line Insert a line separator at the end of each line.
	 * 
	 * @return The converted string.
	 */
	public static String listToString(List<String> list, boolean new_line) {
		StringBuilder sb = new StringBuilder();

		for (String s : list)
			sb.append(new_line ? s + nl() : s);

		return sb.toString();
	}

	/**
	 * Read a specific line in a text file.<br />
	 * <i>Does not read line separators.</i>
	 * 
	 * @param path Path of the text file.
	 * 
	 * @param line Line to read, starts with 1.
	 * 
	 * @return Text read.
	 * 
	 * @throws IOException
	 */
	public static String readLine(String path, int line) throws IOException {
		line--;
		List<String> lines;

		lines = Files.readLines(new File(path), Charset.forName(References.DEFAULT_TEXT_ENCODING));
		if (lines.size() < line)
			line = lines.size();

		return lines.get(line).replaceAll(nl(), "");
	}

	/**
	 * Read a specific position after a line in a text file.<br />
	 * <i>Does not read line separators.</i>
	 * 
	 * @param path     Path of the text file.
	 * 
	 * @param line     Line to read, starts with 1.
	 * 
	 * @param position Position to read, starts with 1. Text starts being read by
	 *                 this position, not after.
	 * 
	 * @return Text read.
	 * 
	 * @throws IOException
	 */
	public static String readLine(String path, int line, int position) throws IOException {
		position--;

		String l = readLine(path, line);
		if (l.length() < position)
			position = 1;

		return l.substring(position, l.length());
	}

	/**
	 * Read a text file to a single string, with line separator characters and
	 * spaces.
	 * 
	 * @param path Path of the text file.
	 * 
	 * @return Text read.
	 * 
	 * @throws IOException
	 */
	public static String readFileToString(String path) throws IOException {
		return Files.asCharSource(new File(path), Charset.forName(References.DEFAULT_TEXT_ENCODING)).read();
	}

	/**
	 * Write a line to the end of the text file.<br />
	 * <i>Will set a line separator at the end, but will not automatically start
	 * writing at a new line in the beginning!</i>
	 * 
	 * @param path Path of the text file.
	 * 
	 * @param text Text to write.
	 * 
	 * @throws IOException
	 */
	public static void writeLine(String path, String text) throws IOException {
		File file = new File(path);

		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true)));
		out.write(text);
		out.write(nl());

		out.close();
	}

	/**
	 * Write to a text file in a specific line.<br />
	 * <i>The old data will be erased.</i>
	 * 
	 * @param path Path of the text file.
	 * 
	 * @param text Text to write.
	 * 
	 * @param line Line to write, starts with 1.
	 * 
	 * @throws IOException
	 */
	public static void writeLine(String path, String text, int line) throws IOException {
		line--;
		File file = new File(path);

		List<String> lines = Files.readLines(file, Charset.forName(References.DEFAULT_TEXT_ENCODING));
		if (lines.size() < line)
			line = lines.size();

		lines.set(line, text);
		java.nio.file.Files.write(file.toPath(), lines, Charset.forName(References.DEFAULT_TEXT_ENCODING));
	}

	/**
	 * Write to a text file after a specific position of a line.<br />
	 * <i>The old data will be erased.</i>
	 * 
	 * @param path     Path of the text file.
	 * 
	 * @param text     Text to write.
	 * 
	 * @param line     Line to write, starts with 1.
	 * 
	 * @param position Position to write, starts with 1. Text starts being replaced
	 *                 by this position, not after.
	 * 
	 * @throws IOException
	 */
	public static void writeLine(String path, String text, int line, int position) throws IOException {
		position--;
		line--;
		File file = new File(path);

		List<String> lines = Files.readLines(file, Charset.forName(References.DEFAULT_TEXT_ENCODING));
		String l = lines.get(line);

		if (l.length() < position)
			position = 1;
		l = l.substring(l.length() - position) + text;

		lines.set(line, l);
		java.nio.file.Files.write(file.toPath(), lines, Charset.forName(References.DEFAULT_TEXT_ENCODING));
	};

}
