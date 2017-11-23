package com.sowpb.sow.util;

import static com.sowpb.sow.util.Utils.createFile;
import static com.sowpb.sow.util.Utils.getSysTimeStr;
import static com.sowpb.sow.util.Utils.writeLine;

import java.io.File;

public class Logger {

	/**
	 * The log file.
	 */
	private File log = new File("SOW-Log.txt");

	/**
	 * The thread that this logger is recording.
	 */
	private String thread;

	public Logger() {
		this.thread = "Unknown";
	}

	public Logger(String thread) {
		this.thread = thread;
	}

	/**
	 * Create the log file. This should only be cased once. <br>
	 * If it's cased for multiple times, the old log file <br>
	 * will be deleted.
	 */
	public void logFile() {
		createFile(log.toString(), true);
		info("Logger initialized.");
	}

	/**
	 * Write the logging message to the log file.
	 */
	private void writeToLog(String msg) {
		writeLine(log.toString(), msg);
	}

	/**
	 * <i>Level: Info</i><br>
	 * This type of message tells the user what is happening inside the <br>
	 * program, and can be ignored.
	 */
	public void info(String msg) {
		StringBuilder b = new StringBuilder();
		b.append('[');
		b.append(getSysTimeStr());
		b.append(']');
		b.append("[INFO]");
		b.append("[Thread_");
		b.append(thread);
		b.append(']');
		b.append(msg);
		System.out.println(b.toString());
		writeToLog(b.toString());
	}

	/**
	 * The debug information is used by developers to determine <br>
	 * whether some codes are correct or not.
	 */
	public void debug(String msg) {
		StringBuilder b = new StringBuilder();
		b.append('[');
		b.append(getSysTimeStr());
		b.append(']');
		b.append("[DEBUG]");
		b.append("[Thread_");
		b.append(thread);
		b.append(']');
		b.append(msg);
		System.out.println(b.toString());
		writeToLog(b.toString());
	}

	/**
	 * <i>Level: Warning</i> <br>
	 * This type of message is used to show something that could hinder the
	 * running <br>
	 * of the program. Although they couldn't cause some major problem, users
	 * should also <br>
	 * check their properties to match the right way.
	 */
	public void warn(String msg) {
		StringBuilder b = new StringBuilder();
		b.append('[');
		b.append(getSysTimeStr());
		b.append(']');
		b.append("[WARNING]");
		b.append("[Thread_");
		b.append(thread);
		b.append(']');
		b.append(msg);
		System.out.println(b.toString());
		writeToLog(b.toString());
	}

	/**
	 * <i>Level: Error</i><br>
	 * This type of message shows that something blocks the running of the
	 * program. <br>
	 * Users should look up the manual to fix it, or contact developers.
	 */
	public void error(String msg) {
		StringBuilder b = new StringBuilder();
		b.append('[');
		b.append(getSysTimeStr());
		b.append(']');
		b.append("[ERROR]");
		b.append("[Thread_");
		b.append(thread);
		b.append(']');
		b.append(msg);
		System.out.println(b.toString());
		writeToLog(b.toString());
	}

	/**
	 * <b><i>Level: Fatal</b></i> <br>
	 * This type of message indicates the critical errors that can destroy the
	 * <br>
	 * whole program. Users should report this type of message to <br>
	 * the developers immediately.
	 */
	public void fatal(String msg) {
		StringBuilder b = new StringBuilder();
		b.append('[');
		b.append(getSysTimeStr());
		b.append(']');
		b.append("[FATAL ERROR]");
		b.append("[Thread_");
		b.append(thread);
		b.append(']');
		b.append(msg);
		System.out.println(b.toString());
		writeToLog(b.toString());
	}

}
