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

public class Utils {

	/**
	 * Create a file safely.
	 * 
	 * @param fp       Path of the file.
	 * @param override Whether override the old file (if exists) or not.
	 */
	public static void createFile(String fp, boolean override) {
		File f = new File(fp);
		File parent = f.getParentFile();
		if (parent != null && !parent.exists()) {
			parent.mkdirs();
		}
		if (f.exists()) {
			if (override) { // if(f.exist() && override) is not applicable.
				f.delete();
			} else {
				return;
			}
		}
		try {
			f.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Read a line of a text file.
	 * 
	 * @param fp   Path of the text file.
	 * @param line Line to read, starts by 1.
	 */
	public static String readLineS(String fp, int line) throws IOException {
		List<String> lines = Files.readLines(new File(fp), Charset.forName(References.DEFAULT_ENCODING));
		return lines.get(line - 1);
	}

	/**
	 * Read a line after a position of a text file.
	 * 
	 * @param fp   Path of the text file.
	 * @param line Line to read, starts by 1.
	 * @param pos  Position to read, starts by 1.
	 */
	public static String readLineSP(String fp, int line, int pos) throws IOException {
		pos--;
		String l = readLineS(fp, line);
		if (l.length() < pos) {
			pos = l.length();
		} // Start reading at the end of line if the position exceeds.
		return l.substring(pos, l.length());
	}

	/**
	 * Read a text file to a single string (including special characters).
	 * 
	 * @param fp Path of the text file.
	 */
	public static String readFileToString(String fp) throws IOException {
		return Files.asCharSource(new File(fp), Charset.forName(References.DEFAULT_ENCODING)).toString();
	}

	/**
	 * Get the lines count of a text file.
	 * 
	 * @param fp Path of the text file.
	 */
	public static int getTotalLines(String fp) throws IOException {
		File f = new File(fp);
		List<String> lines = Files.readLines(f, Charset.forName(References.DEFAULT_ENCODING));
		return lines.size() + 1;
	}

	/**
	 * Write a new line to a text file.
	 * 
	 * @param fp   Path of the text file.
	 * @param data Data to write.
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
	 * Write a line of a text file.
	 * 
	 * @param fp   Path of the text file.
	 * @param data Data to write.
	 * @param line Line to write, starts by 1.
	 */
	public static void writeLineS(String fp, String data, int line) throws IOException {
		File f = new File(fp);
		List<String> lines = Files.readLines(f, Charset.forName(References.DEFAULT_ENCODING));
		lines.set(line - 1, data);
		java.nio.file.Files.write(f.toPath(), lines, Charset.forName(References.DEFAULT_ENCODING));
	}

	/**
	 * Write after a position of a line of a text file.
	 * 
	 * @param fp   Path of the text file.
	 * @param data Data to write.
	 * @param line Line to write, starts by 1.
	 * @param pos  Position to write, starts by 1.
	 */
	public static void writeLineSP(String fp, String data, int line, int pos) throws IOException {
		pos--;
		line--;
		File f = new File(fp);
		List<String> lines = Files.readLines(f, Charset.forName(References.DEFAULT_ENCODING));
		String l = lines.get(line);
		if (l.length() < pos) {
			pos = l.length();
		} // Start writing at the end of line if the position exceeds.
		l = l.substring(l.length() - pos) + data;
		lines.set(line, l);
		java.nio.file.Files.write(f.toPath(), lines, Charset.forName(References.DEFAULT_ENCODING));
	}

	/**
	 * Get a resource file as a byte array.
	 * 
	 * @param fp Path of the resource.
	 */
	public static byte[] getRes(String fp) throws IOException {
		return Resources.toByteArray(Resources.getResource(fp));
	}

	/**
	 * Read a resource text file to lines of string.
	 * 
	 * @param fp Path of the resource.
	 */
	public static List<String> getResAsStrings(String fp) throws IOException {
		return Resources.readLines(Resources.getResource(fp), Charset.forName(References.DEFAULT_ENCODING));
	}

	/**
	 * Get the system time in the format of <b>HH:MM:SS</b>.
	 */
	public static String getSysTime() {
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
	 * Convert lines of string to a single string (including lines separator).
	 * 
	 * @param l Lines of string.
	 */
	public static String listToString(List<String> l) {
		StringBuilder sb = new StringBuilder();
		for (String s : l) {
			sb.append(s).append(nl());
		}
		return sb.toString();
	}

	/**
	 * Get the current operating system.
	 */
	public static OSType getOS() {
		OSType detectedOS;
		String OS = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
		if ((OS.indexOf("mac") >= 0) || (OS.indexOf("darwin") >= 0)) {
			detectedOS = OSType.MacOS;
		} else if (OS.indexOf("win") >= 0) {
			detectedOS = OSType.Windows;
		} else if (OS.indexOf("nux") >= 0) {
			detectedOS = OSType.Linux;
		} else {
			detectedOS = OSType.Other;
		}
		return detectedOS;
	}

	public static enum OSType {
		Windows, MacOS, Linux, Other
	};

}
