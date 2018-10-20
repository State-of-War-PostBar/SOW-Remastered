package cn.stateofwar.sowr.util;

import java.io.File;
import java.io.IOException;

import cn.stateofwar.sowr.References;

public class Logger {

	public static final Logger PUBLIC_LOGGER = new Logger();

	private File log_file = new File(References.LOG_FILE_NAME);

	private String source;

	public Logger() {
		source = "UNKNOWN";
	}

	public Logger(String _source) {
		source = _source;
	}

	public void init() {
		try {
			Utils.createFile(log_file.toString(), true);
		} catch (IOException e) {
			System.err.println(e.getLocalizedMessage());
			e.printStackTrace();
		}
	}

	public void info(String message) {
		StringBuilder sb = new StringBuilder();
		sb.append('[').append(Utils.getSysTime()).append(']');
		sb.append("[INFO]");
		sb.append('[').append(source).append(']');
		sb.append(message);
		System.out.println(sb.toString());
		record(sb.toString());
	}

	public void warn(String message) {
		StringBuilder sb = new StringBuilder();
		sb.append('[').append(Utils.getSysTime()).append(']');
		sb.append("[WARNING]");
		sb.append('[').append(source).append(']');
		sb.append(message);
		System.out.println(sb.toString());
		record(sb.toString());
	}

	public void error(String message) {
		StringBuilder sb = new StringBuilder();
		sb.append('[').append(Utils.getSysTime()).append(']');
		sb.append("[ERROR]");
		sb.append('[').append(source).append(']');
		sb.append(message);
		System.err.println(sb.toString());
		record(sb.toString());
	}

	public void fatal(String message) {
		StringBuilder sb = new StringBuilder();
		sb.append('[').append(Utils.getSysTime()).append(']');
		sb.append("[FATAL ERROR]");
		sb.append('[').append(source).append(']');
		sb.append(message);
		System.err.println(sb.toString());
		record(sb.toString());
	}

	public void debug(String message) {
		StringBuilder sb = new StringBuilder();
		sb.append('[').append(Utils.getSysTime()).append(']');
		sb.append("[===DEBUG===]");
		sb.append('[').append(source).append(']');
		sb.append(message);
		System.out.println(sb.toString());
		record(sb.toString());
	}

	private void record(String message) {
		try {
			Utils.writeLine(log_file.toString(), message);
		} catch (IOException e) {
			System.err.println(e.getLocalizedMessage());
			e.printStackTrace();
		}
	}

}
