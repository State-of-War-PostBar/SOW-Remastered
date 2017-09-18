package com.sowpb.sow.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Utils {

	public static void createFile(String path, boolean override) {
		File f = new File(path);
		File parent = f.getParentFile();
		if (parent != null && !parent.exists())
			parent.mkdirs();
		if (f.exists())
			if (override == true)
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
	 */
	public static String readLineS(String filePath, int line) throws IOException {
		File f = new File(filePath);
		List<String> lines = Files.readAllLines(f.toPath());
		return lines.get(line - 1);
	}

	/**
	 * Read the context after a specified position of a line in a text file.
	 */
	public static String readLineSP(String filePath, int line, int pos) throws IOException {
		pos--;
		String l = readLineS(filePath, line);
		if (l.length() < pos)
			pos = l.length();
		l = l.substring(pos, l.length());
		return l;
	}

	/**
	 * Get the content of a text file as a single string.
	 */
	public static String readFileToSingleString(String path) throws IOException {
		List<String> lns = new ArrayList<>();
		try {
			lns = Files.readAllLines(new File(path).toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		StringBuilder sb = new StringBuilder();
		for (String x : lns)
			sb.append(x).append('\n');
		return sb.toString();
	}

	/** Read the total lines of a text file. */
	public static int getTotalLines(String filePath) throws IOException {
		File f = new File(filePath);
		List<String> lines = Files.readAllLines(f.toPath());
		return lines.size() + 1;
	}

	/**
	 * Write a line to a text file. Automatically start a new line after the
	 * writing, <br>
	 * but will NOT start writing on a new line!
	 */
	public static void writeLine(String filePath, String msg) {
		File f = new File(filePath);
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f, true)));
			out.write(msg);
			switch (getOS()) {
			case Windows:
				out.write("\r\n");
				break;
			default:
				out.write('\n');
			}

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
	 * Write data to a specific line of a text file. <br>
	 * Will override the old data.
	 */
	public static void writeLineS(String filePath, String msg, int line) throws IOException {
		File f = new File(filePath);
		List<String> lines = Files.readAllLines(f.toPath());
		lines.set(line - 1, msg);
		Files.write(f.toPath(), lines);
	}

	/**
	 * Write data to a specific position of a specific line of a text file. <br>
	 * The old data after the position will be erased. <br>
	 */
	public static void writeLineSP(String filePath, String msg, int line, int pos) throws IOException {
		pos--;
		line--;
		File f = new File(filePath);
		List<String> lines = Files.readAllLines(f.toPath());
		String l = lines.get(line);
		if (l.length() < pos)
			pos = l.length();
		l = l.substring(l.length() - pos);
		l = l + msg;
		lines.set(line, l);
		Files.write(f.toPath(), lines);
	}

	/**
	 * The system time in the format of hh:mm:ss.
	 */
	public static String getSysTimeStr() {
		Calendar c = Calendar.getInstance();
		return "" + c.get(Calendar.HOUR_OF_DAY) + ':' + c.get(Calendar.MINUTE) + ':' + c.get(Calendar.SECOND);
	}

	public static enum OSType {
		Windows, MacOS, Linux, Other
	};

	public static OSType getOS() {

		OSType detectedOS = null;
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

}
