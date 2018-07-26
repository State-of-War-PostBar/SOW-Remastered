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
 * Utilities of different usages, such as files reading and writing, system
 * properties managements, etc.
 */
public class Utils {

	/**
	 * Get the current directory of the binaries (where the program is executed).
	 */
	public static String getDir() {
		return System.getProperty("user.dir");
	}

	/**
	 * Create a file in a specific path. Directories will be created if not exist.
	 * 
	 * @param fp       The path of the file.
	 * 
	 * @param override Delete the old file if there is file with same path and name.
	 */
	public static void createFile(String fp, boolean override) {
		File f = new File(fp);
		File parent = f.getParentFile();
		if (parent != null && !parent.exists())
			parent.mkdirs();
		if (f.exists())
			if (override) /* if (f.exists() && override) is not applicable for some reason. */
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
	 * Read the context of a specified line in a text file.
	 * 
	 * @param fp   The path of the text file.
	 * 
	 * @param line The line desired to read, starts with 1.
	 * 
	 * @throws IOException
	 */
	public static String readLineS(String fp, int line) throws IOException {
		List<String> lines = Files.readLines(new File(fp), Charset.forName(References.DEFAULT_ENCODE));
		return lines.get(line - 1);
	}

	/**
	 * Read the context after a specified position of a line in a text file.
	 * 
	 * @param fp   The path of the text file.
	 * 
	 * @param line The line desired to read, starts with 1.
	 * 
	 * @param pos  The position in the line desired to read, starts with 1.
	 * 
	 * @throws IOException
	 */
	public static String readLineSP(String fp, int line, int pos) throws IOException {
		pos--;
		String l = readLineS(fp, line);
		if (l.length() < pos)
			pos = l.length();
		return l.substring(pos, l.length());
	}

	/**
	 * Get the content of a text file as a single string.
	 * 
	 * @param fp The path of the text file.
	 * 
	 * @throws IOException
	 */
	public static String readFileToString(String fp) throws IOException {
		return Files.asCharSource(new File(fp), Charset.forName(References.DEFAULT_ENCODE)).toString();
	}

	/**
	 * Get the total lines of a text file.
	 * 
	 * @param fp The path of the text file.
	 * 
	 * @throws IOException
	 */
	public static int getTotalLines(String fp) throws IOException {
		File f = new File(fp);
		List<String> lines = Files.readLines(f, Charset.forName(References.DEFAULT_ENCODE));
		return lines.size() + 1;
	}

	/**
	 * Write a line to a text file. Automatically start a new line after the
	 * writing, <br />
	 * but will NOT start writing on a new line!
	 * 
	 * @param fp  The path of the text file.
	 * 
	 * @param msg The text that is being written.
	 */
	public static void writeLine(String fp, String msg) {
		File f = new File(fp);
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f, true)));
			out.write(msg);
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
	 * Write data to a specific line of a text file. <br />
	 * <i>Will override the old data.</i>
	 * 
	 * @param fp   The path of the text file.
	 * 
	 * @param msg  The data.
	 * 
	 * @param line The line desired to write, starts with 1.
	 * 
	 * @throws IOException
	 */
	public static void writeLineS(String fp, String msg, int line) throws IOException {
		File f = new File(fp);
		List<String> lines = Files.readLines(f, Charset.forName(References.DEFAULT_ENCODE));
		lines.set(line - 1, msg);
		java.nio.file.Files.write(f.toPath(), lines, Charset.forName(References.DEFAULT_ENCODE));
	}

	/**
	 * Write data to a specific position of a specific line of a text file. <br />
	 * <i>Will override the old data after the position.</i>
	 * 
	 * @param fp   The path of the text file.
	 * 
	 * @param msg  The data.
	 * 
	 * @param line The line desired to write, starts with 1.
	 * 
	 * @param pos  The position in the line desired to write, starts with 1.
	 * 
	 * @throws IOException
	 */
	public static void writeLineSP(String fp, String msg, int line, int pos) throws IOException {
		pos--;
		line--;
		File f = new File(fp);
		List<String> lines = Files.readLines(f, Charset.forName(References.DEFAULT_ENCODE));
		String l = lines.get(line);
		if (l.length() < pos)
			pos = l.length();
		l = l.substring(l.length() - pos) + msg;
		lines.set(line, l);
		java.nio.file.Files.write(f.toPath(), lines, Charset.forName(References.DEFAULT_ENCODE));
	}

	/**
	 * Get the content of a resource file as a byte array.
	 * 
	 * @param fp The path of the resource file.
	 * 
	 * @throws IOException
	 */
	public static byte[] getRes(String fp) throws IOException {
		return Resources.toByteArray(Resources.getResource(fp));
	}

	/**
	 * Get the content of a resource file as a list of string. The resource must be
	 * a text file.
	 * 
	 * @param fp The path of the text file.
	 * 
	 * @throws IOException
	 */
	public static List<String> getResAsStrings(String fp) throws IOException {
		return Resources.readLines(Resources.getResource(fp), Charset.forName(References.DEFAULT_ENCODE));
	}

	/**
	 * Get the system time in the format of HOUR:MINUTE:SECOND.
	 */
	public static String getSysTimeStr() {
		Calendar c = Calendar.getInstance();
		return "" + c.get(Calendar.HOUR_OF_DAY) + ':' + c.get(Calendar.MINUTE) + ':' + c.get(Calendar.SECOND);
	}

	/**
	 * Generate a new line symbol.
	 */
	public static String nl() {
		return System.lineSeparator();
	}

	/**
	 * Translate a Unicode code point.
	 * 
	 * @param cp The code point of the Unicode character.
	 */
	public static String unicodeTrans(int cp) {
		return String.valueOf(Character.toChars(cp));
	}

	/**
	 * Convert a list of strings to a single line of string.
	 * 
	 * @param l The list desired to convert.
	 */
	public static String listToString(List<String> l) {
		StringBuilder sb = new StringBuilder();
		for (String s : l)
			sb.append(s).append(nl());
		return sb.toString();
	}

	/**
	 * Get the current operating system.
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
