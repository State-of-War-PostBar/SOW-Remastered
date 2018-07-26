package cn.stateofwar.sowr.util;

import static cn.stateofwar.sowr.util.Utils.getSysTimeStr;

import java.io.File;

import cn.stateofwar.sowr.References;

/**
 * A very basic logging system with only console printing and log file
 * recording.
 */
public class Logger {

	/**
	 * The log file.
	 */
	private File log = new File(References.LOG_FILE_NAME);

	/**
	 * The thread or process this logger records.
	 */
	private String thread;

	public Logger() {
		thread = "Unknown";
	}

	public Logger(String _thread) {
		thread = _thread;
	}

	/**
	 * Create the log file. <br />
	 * If it's cased for multiple times, the old log file will be deleted and it's
	 * very possible to trigger an error.
	 */
	public void init() {
		Utils.createFile(log.toString(), true);
	}

	/**
	 * <i>Level: Information</i><br />
	 * This type of message shows the user what is happening inside the program.
	 * 
	 * @param msg The message to log.
	 */
	public void info(String msg) {
		StringBuilder b = new StringBuilder();
		b.append('[').append(getSysTimeStr()).append(']');
		b.append("[INFO]");
		b.append('[').append(thread).append(']');
		b.append(msg);
		System.out.println(b.toString());
		writeToLog(b.toString());
	}

	/**
	 * <i>Level: Warning</i><br />
	 * This type of message shows something that could hinder the running of the
	 * program.
	 * 
	 * @param msg The message to log.
	 */
	public void warn(String msg) {
		StringBuilder b = new StringBuilder();
		b.append('[').append(getSysTimeStr()).append(']');
		b.append("[WARNING]");
		b.append('[').append(thread).append(']');
		b.append(msg);
		System.out.println(b.toString());
		writeToLog(b.toString());
	}

	/**
	 * <b><i>Level: Error</i></b><br />
	 * This type of message shows that something is blocking the running of the
	 * program.
	 * 
	 * @param msg The message to log.
	 */
	public void error(String msg) {
		StringBuilder b = new StringBuilder();
		b.append('[').append(getSysTimeStr()).append(']');
		b.append("[ERROR]");
		b.append('[').append(thread).append(']');
		b.append(msg);
		System.out.println(b.toString());
		writeToLog(b.toString());
	}

	/**
	 * <b><i>Level: Fatal Error</b></i><br />
	 * This type of message indicates the critical errors that can destroy the whole
	 * program.
	 * 
	 * @param msg The message to log.
	 */
	public void fatal(String msg) {
		StringBuilder b = new StringBuilder();
		b.append('[').append(getSysTimeStr()).append(']');
		b.append("[FATAL ERROR]");
		b.append('[').append(thread).append(']');
		b.append(msg);
		System.out.println(b.toString());
		writeToLog(b.toString());
	}

	/**
	 * Write the logging message to the log file.
	 */
	private void writeToLog(String msg) {
		Utils.writeLine(log.toString(), msg);
	}

}
