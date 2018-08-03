package cn.stateofwar.sowr.util;

import static cn.stateofwar.sowr.util.Utils.getSysTime;

import java.io.File;

import cn.stateofwar.sowr.References;

public class Logger {

	private File log = new File(References.LOG_FILE_NAME);

	private String origin;

	public Logger() {
		origin = "Unknown";
	}

	public Logger(String _thread) {
		origin = _thread;
	}

	/**
	 * Create the log file. If it's cased for multiple times, the old log file will
	 * be erased.
	 */
	public void init() {
		Utils.createFile(log.toString(), true);
	}

	public void info(String msg) {
		StringBuilder b = new StringBuilder();
		b.append('[').append(getSysTime()).append(']');
		b.append("[INFO]");
		b.append('[').append(origin).append(']');
		b.append(msg);
		System.out.println(b.toString());
		writeToLog(b.toString());
	}

	public void warn(String msg) {
		StringBuilder b = new StringBuilder();
		b.append('[').append(getSysTime()).append(']');
		b.append("[WARNING]");
		b.append('[').append(origin).append(']');
		b.append(msg);
		System.out.println(b.toString());
		writeToLog(b.toString());
	}

	public void error(String msg) {
		StringBuilder b = new StringBuilder();
		b.append('[').append(getSysTime()).append(']');
		b.append("[ERROR]");
		b.append('[').append(origin).append(']');
		b.append(msg);
		System.out.println(b.toString());
		writeToLog(b.toString());
	}

	public void fatal(String msg) {
		StringBuilder b = new StringBuilder();
		b.append('[').append(getSysTime()).append(']');
		b.append("[FATAL ERROR]");
		b.append('[').append(origin).append(']');
		b.append(msg);
		System.out.println(b.toString());
		writeToLog(b.toString());
	}

	public void debug(String msg) {
		StringBuilder b = new StringBuilder();
		b.append('[').append(getSysTime()).append(']');
		b.append("[DEBUG]");
		b.append('[').append(origin).append(']');
		b.append(msg);
		System.out.println(b.toString());
		writeToLog(b.toString());
	}

	private void writeToLog(String msg) {
		Utils.writeLine(log.toString(), msg);
	}

}
