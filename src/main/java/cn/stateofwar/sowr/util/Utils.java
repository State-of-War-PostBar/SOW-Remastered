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
	 * Safely create a file.
	 * 
	 * @param fp       Path of the file.
	 * @param override Override the old file (if exists).
	 */
	public static void createFile(String fp, boolean override) {
		File f = new File(fp);
		File parent = f.getParentFile();
		if (parent != null && !parent.exists())
			parent.mkdirs();
		if (f.exists())
			if (override)
				f.delete();
			else
				return;
		try {
			f.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Read a specific line in a text file.
	 * 
	 * @param fp   Path of the text file.
	 * @param line Line to read, starts with 1.
	 * 
	 * @return The text read.
	 */
	public static String readLineS(String fp, int line) {
		line--;
		List<String> lines = null;
		try {
			lines = Files.readLines(new File(fp), Charset.forName(References.DEFAULT_TEXT_ENCODE));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lines.get(line);
	}

	/**
	 * Read a specific position after a line in a text file.
	 * 
	 * @param fp   Path of the text file.
	 * @param line Line to read, starts with 1.
	 * @param pos  Position to read, starts with 1. Text in this position will also
	 *             be read.
	 * 
	 * @return The text read.
	 */
	public static String readLineSP(String fp, int line, int pos) {
		pos--;
		String l = readLineS(fp, line);
		if (l.length() < pos)
			pos = l.length();
		return l.substring(pos, l.length());
	}

	/**
	 * Read a text file to a single string, with line separators.
	 * 
	 * @param fp Path of the text file.
	 * 
	 * @return The text read.
	 */
	public static String readFileToString(String fp) {
		return Files.asCharSource(new File(fp), Charset.forName(References.DEFAULT_TEXT_ENCODE)).toString();
	}

	/**
	 * Get the total count of lines of a text file.
	 * 
	 * @param fp Path of the text file.
	 * 
	 * @return The lines count.
	 */
	public static int getTotalLines(String fp) {
		File f = new File(fp);
		List<String> lines = null;
		try {
			lines = Files.readLines(f, Charset.forName(References.DEFAULT_TEXT_ENCODE));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lines.size() + 1;
	}

	/**
	 * Write a line to the end of the text file. Will set a line separator at the
	 * end. But will not automatically start writing at a new line in the beginning!
	 * 
	 * @param fp   Path of the text file.
	 * @param data The text to write.
	 */
	public static void writeLine(String fp, String data) {
		File f = new File(fp);
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f, true)));
			out.write(data);
			out.write(nl());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Write to a text file in a specific line. <br />
	 * <i>The old data will be erased.</i>
	 * 
	 * @param fp   Path of the text file.
	 * @param data The text to write.
	 * @param line The line to write, starts with 1.
	 */
	public static void writeLineS(String fp, String data, int line) {
		line--;
		File f = new File(fp);
		List<String> lines;
		try {
			lines = Files.readLines(f, Charset.forName(References.DEFAULT_TEXT_ENCODE));
			java.nio.file.Files.write(f.toPath(), lines, Charset.forName(References.DEFAULT_TEXT_ENCODE));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Write to a text file in a specific position of a line. <br />
	 * <i>The old data will be erased.</i>
	 * 
	 * @param fp   Path of the text file.
	 * @param data The text to write.
	 * @param line The line to write, starts with 1.
	 * @param pos  The position to write, starts with 1.
	 */
	public static void writeLineSP(String fp, String data, int line, int pos) throws IOException {
		pos--;
		line--;
		File f = new File(fp);
		List<String> lines = Files.readLines(f, Charset.forName(References.DEFAULT_TEXT_ENCODE));
		String l = lines.get(line);
		if (l.length() < pos)
			pos = l.length();
		l = l.substring(l.length() - pos) + data;
		lines.set(line, l);
		java.nio.file.Files.write(f.toPath(), lines, Charset.forName(References.DEFAULT_TEXT_ENCODE));
	}

	/**
	 * Get the data of the resource file as a byte array.
	 * 
	 * @param fp Path of the resource file.
	 * 
	 * @return The converted byte array of the resource file.
	 */
	public static byte[] getRes(String fp) throws IOException {
		return Resources.toByteArray(Resources.getResource(fp));
	}

	/**
	 * Get the data of the resource text file as a list of strings. <i>Works only if
	 * the resource file is a pure text file.</i>
	 * 
	 * @param fp Path of the resource file.
	 * 
	 * @return The list of strings of the resource text file.
	 */
	public static List<String> getResAsStrings(String fp) throws IOException {
		return Resources.readLines(Resources.getResource(fp), Charset.forName(References.DEFAULT_TEXT_ENCODE));
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
	 * @param l  The list of strings.
	 * @param nl Insert a line separator at the end of each line.
	 * 
	 * @return The converted string.
	 */
	public static String listToString(List<String> l, boolean nl) {
		StringBuilder sb = new StringBuilder();
		for (String s : l)
			if (nl)
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
		Windows, MacOS, Linux, Other
	};

}
