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

	public static enum OSType {
		LINUX, WINDOWS, MAC, OTHERS
	}

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

	public static String nl() {
		return System.lineSeparator();
	}

	public static String getSysTime() {
		Calendar calendar = Calendar.getInstance();

		String hour = Integer.toString(calendar.get(Calendar.HOUR_OF_DAY));
		hour = hour.length() == 2 ? hour : '0' + hour;

		String minute = Integer.toString(calendar.get(Calendar.MINUTE));
		minute = minute.length() == 2 ? minute : '0' + minute;

		String second = Integer.toString(calendar.get(Calendar.SECOND));
		second = second.length() == 2 ? second : '0' + second;

		return "" + hour + ':' + minute + ':' + second;
	}

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

	public static byte[] getResource(String path) throws IOException {
		return Resources.toByteArray(Resources.getResource(path));
	}

	public static List<String> getResourceAsStrings(String path) throws IOException {
		return Resources.readLines(Resources.getResource(path), Charset.forName(References.DEFAULT_TEXT_ENCODING));
	}

	public static String listToString(List<String> list, boolean new_line) {
		StringBuilder sb = new StringBuilder();

		for (String s : list)
			if (new_line)
				sb.append(s).append(nl());
			else
				sb.append(s);

		return sb.toString();
	}

	public static int getTotalLines(String path) throws IOException {
		File file = new File(path);
		List<String> lines;

		lines = Files.readLines(file, Charset.forName(References.DEFAULT_TEXT_ENCODING));

		return lines.size();
	}

	public static String readLineS(String path, int line) throws IOException {
		line--;
		List<String> lines;

		lines = Files.readLines(new File(path), Charset.forName(References.DEFAULT_TEXT_ENCODING));
		if (lines.size() < line)
			line = lines.size();

		return lines.get(line).replaceAll(nl(), "");
	}

	public static String readLineSP(String path, int line, int position) throws IOException {
		position--;

		String l = readLineS(path, line);
		if (l.length() < position)
			position = l.length();

		return l.substring(position, l.length());
	}

	public static String readFileToString(String path) throws IOException {
		return Files.asCharSource(new File(path), Charset.forName(References.DEFAULT_TEXT_ENCODING)).read();
	}

	public static void writeLine(String path, String text) throws IOException {
		File file = new File(path);

		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true)));
		out.write(text);
		out.write(nl());

		out.close();
	}

	public static void writeLineS(String path, String text, int line) throws IOException {
		line--;
		File file = new File(path);

		List<String> lines = Files.readLines(file, Charset.forName(References.DEFAULT_TEXT_ENCODING));
		if (lines.size() < line)
			line = lines.size();

		lines.set(line, text);
		java.nio.file.Files.write(file.toPath(), lines, Charset.forName(References.DEFAULT_TEXT_ENCODING));
	}

	public static void writeLineSP(String path, String text, int line, int position) throws IOException {
		position--;
		line--;
		File file = new File(path);

		List<String> lines = Files.readLines(file, Charset.forName(References.DEFAULT_TEXT_ENCODING));
		String l = lines.get(line);
		if (l.length() < position)
			position = l.length();
		l = l.substring(l.length() - position) + text;

		lines.set(line, l);
		java.nio.file.Files.write(file.toPath(), lines, Charset.forName(References.DEFAULT_TEXT_ENCODING));
	};

}
