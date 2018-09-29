package cn.stateofwar.sowr.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import com.google.common.io.Files;
import com.google.common.io.Resources;

import cn.stateofwar.sowr.References;

/**
 * Utilities for other code.
 */
public class Utils {

	/**
	 * Check if the program is running in a normal universe.
	 * 
	 * @return If this universe is normal.
	 */
	public static boolean inNormalUniverse() {
		if (Integer.compare(1 + 1, 2) == 0)
			return true;
		return false;
	}

	/**
	 * Create a file.
	 * 
	 * @param path     Path of the file.
	 * 
	 * @param override Override the old file (if that exists).
	 * 
	 * @throws IOException
	 */
	public static void createFile(String path, boolean override) throws IOException {
		File f = new File(path);
		File parent = f.getParentFile();
		if (parent != null && !parent.exists())
			parent.mkdirs();
		if (f.exists())
			if (override) /* if (f.exists() && override) is not applicable. */
				f.delete();
			else
				return;
		f.createNewFile();
	}

	/**
	 * Read a specific line in a text file.<br />
	 * <i>Does not read any line separators.</i>
	 * 
	 * @param path Path of the text file.
	 * 
	 * @param line Line to read, starts with 1.
	 * 
	 * @return The text read.
	 * 
	 * @throws IOException
	 */
	public static String readLineS(String path, int line) throws IOException {
		line--;
		List<String> lines = null;
		lines = Files.readLines(new File(path), Charset.forName(References.DEFAULT_TEXT_ENCODE));
		if (lines.size() < line)
			line = lines.size(); /*
									 * Read the last line of file (not EOF line) instead if desired line is
									 * unreachable.
									 */
		return lines.get(line).replaceAll(nl(), "");
	}

	/**
	 * Read a specific position after a line in a text file.<br />
	 * <i>Does not read any line separators.</i>
	 * 
	 * @param path Path of the text file.
	 * 
	 * @param line Line to read, starts with 1.
	 * 
	 * @param pos  Position to read, starts with 1. Text in this position will also
	 *             be read.
	 * 
	 * @return The text read.
	 * 
	 * @throws IOException
	 */
	public static String readLineSP(String path, int line, int pos) throws IOException {
		pos--;
		String l = readLineS(path, line);
		if (l.length() < pos)
			pos = l.length(); /*
								 * Read the last position of the line instead if desired position is
								 * unreachable.
								 */
		return l.substring(pos, l.length()).replaceAll(nl(), "");
	}

	/**
	 * Read a text file to a single string, with line separator characters and
	 * spaces.
	 * 
	 * @param path Path of the text file.
	 * 
	 * @return The text read.
	 * 
	 * @throws IOException
	 */
	public static String readFileToString(String path) throws IOException {
		return Files.asCharSource(new File(path), Charset.forName(References.DEFAULT_TEXT_ENCODE)).read();
	}

	/**
	 * Get the total count of lines of a text file.
	 * 
	 * @param path Path of the text file.
	 * 
	 * @return The lines count.
	 * 
	 * @throws IOException
	 */
	public static int getTotalLines(String path) throws IOException {
		File f = new File(path);
		List<String> lines = null;
		lines = Files.readLines(f, Charset.forName(References.DEFAULT_TEXT_ENCODE));
		return lines.size();
	}

	/**
	 * Write a line to the end of the text file. Will set a line separator at the
	 * end, but will not automatically start writing at a new line in the beginning!
	 * 
	 * @param path Path of the text file.
	 * 
	 * @param text The text to write.
	 * 
	 * @throws IOException
	 */
	public static void writeLine(String path, String text) throws IOException {
		File f = new File(path);
		BufferedWriter out = null;
		out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f, true)));
		out.write(text);
		out.write(nl());
		out.close();
	}

	/**
	 * Write to a text file in a specific line. <br />
	 * <i>The old data will be erased.</i>
	 * 
	 * @param fp   Path of the text file.
	 * 
	 * @param text The text to write.
	 * 
	 * @param line The line to write, starts with 1. Text starts replacing in this
	 *             position, not after.
	 * 
	 * @throws IOException
	 */
	public static void writeLineS(String path, String text, int line) throws IOException {
		line--;
		File f = new File(path);
		List<String> lines;
		lines = Files.readLines(f, Charset.forName(References.DEFAULT_TEXT_ENCODE));
		java.nio.file.Files.write(f.toPath(), lines, Charset.forName(References.DEFAULT_TEXT_ENCODE));
	}

	/**
	 * Write to a text file after a specific position of a line. <br />
	 * <i>The old data will be erased.</i>
	 * 
	 * @param path Path of the text file.
	 * 
	 * @param text The text to write.
	 * 
	 * @param line The line to write, starts with 1.
	 * 
	 * @param pos  The position to write, starts with 1.
	 * 
	 * @throws IOException
	 */
	public static void writeLineSP(String path, String text, int line, int pos) throws IOException {
		pos--;
		line--;
		File f = new File(path);
		List<String> lines = Files.readLines(f, Charset.forName(References.DEFAULT_TEXT_ENCODE));
		String l = lines.get(line);
		if (l.length() < pos)
			pos = l.length();
		l = l.substring(l.length() - pos) + text;
		lines.set(line, l);
		java.nio.file.Files.write(f.toPath(), lines, Charset.forName(References.DEFAULT_TEXT_ENCODE));
	}

	/**
	 * Get the data of a resource file as a byte array.
	 * 
	 * @param url Path of the resource file.
	 * 
	 * @return The converted byte array of the resource file.
	 */
	public static byte[] getRes(String url) throws IOException {
		return Resources.toByteArray(Resources.getResource(url));
	}

	/**
	 * Get the data of a resource text file as a list of strings.<br />
	 * <i>Works only if the resource file is a pure text file.</i>
	 * 
	 * @param url Path of the resource file.
	 * 
	 * @return The list of strings of the resource text file.
	 */
	public static List<String> getResAsStrings(String url) throws IOException {
		return Resources.readLines(Resources.getResource(url), Charset.forName(References.DEFAULT_TEXT_ENCODE));
	}

	/**
	 * Get the current system time.
	 * 
	 * @return The current time in the format of <i>Hour:Minute:Second</i>
	 */
	public static String getSysTime() {
		Calendar c = Calendar.getInstance();
		return "" + c.get(Calendar.HOUR_OF_DAY) + ':' + c.get(Calendar.MINUTE) + ':' + c.get(Calendar.SECOND);
	}

	/**
	 * Create a line separator.
	 * 
	 * @return The line separator for current OS.
	 */
	public static String nl() {
		return System.lineSeparator();
	}

	/**
	 * Convert a list of strings to a single string.
	 * 
	 * @param list    The list of strings.
	 * 
	 * @param newLine Insert a line separator at the end of each line.
	 * 
	 * @return The converted string.
	 */
	public static String listToString(List<String> list, boolean newLine) {
		StringBuilder sb = new StringBuilder();
		for (String s : list)
			if (newLine)
				sb.append(s).append(nl());
			else
				sb.append(s);
		return sb.toString();
	}

	/**
	 * Get the current operating system.
	 * 
	 * @return The operating system.
	 */
	public static OSType getOS() {
		OSType detectedOS;
		String OS = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
		if ((OS.indexOf("mac") >= 0) || (OS.indexOf("darwin") >= 0))
			detectedOS = OSType.MacOS;
		else if (OS.indexOf("win") >= 0)
			detectedOS = OSType.Windows;
		else if (OS.indexOf("nux") >= 0)
			detectedOS = OSType.Linux;
		else
			detectedOS = OSType.Other;
		return detectedOS;
	}

	/**
	 * Types of operating systems.
	 */
	public static enum OSType {
		Linux, MacOS, Other, Windows
	};

}
