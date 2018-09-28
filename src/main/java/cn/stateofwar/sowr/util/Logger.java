package cn.stateofwar.sowr.util;

import static cn.stateofwar.sowr.util.Utils.*;

import java.io.File;
import java.io.IOException;

import cn.stateofwar.sowr.References;

/**
 * Logger for the program. For every class that needs log recording, it needs a
 * private instance of this class.
 */
public class Logger {

	/** The file to store logs. */
	private File log = new File(References.LOG_FILE_NAME);

	/** The source that processes logging. */
	private String source;

	public Logger() {
		source = "Unknown";
	}

	public Logger(String _thread) {
		source = _thread;
	}

	/**
	 * Initialize the log file.
	 */
	public void init() {
		try {
			createFile(log.toString(), true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * <i>Message Level: <b>Information</b></i><br />
	 * This type of log messages records the procedures of the program.
	 * 
	 * @param msg The message to record.
	 */
	public void info(String msg) {
		StringBuilder b = new StringBuilder();
		b.append('[').append(getSysTime()).append(']');
		b.append("[INFO]");
		b.append('[').append(source).append(']');
		b.append(msg);
		System.out.println(b.toString());
		writeToLog(b.toString());
	}

	/**
	 * <i>Message Level: <b>Warning</b></i><br />
	 * This type of log messages records something that is not expected when
	 * running, but they might not trigger huge impact.
	 * 
	 * @param msg The message to record.
	 */
	public void warn(String msg) {
		StringBuilder b = new StringBuilder();
		b.append('[').append(getSysTime()).append(']');
		b.append("[WARNING]");
		b.append('[').append(source).append(']');
		b.append(msg);
		System.out.println(b.toString());
		writeToLog(b.toString());
	}

	/**
	 * <i>Message Level: <b>Error</b></i><br />
	 * This type of log messages records the errors that disable some of the
	 * program. Although they might not be very dangerous, users should take a look
	 * at this and report to the development team.
	 * 
	 * @param msg The message to record.
	 */
	public void error(String msg) {
		StringBuilder b = new StringBuilder();
		b.append('[').append(getSysTime()).append(']');
		b.append("[ERROR]");
		b.append('[').append(source).append(']');
		b.append(msg);
		System.out.println(b.toString());
		writeToLog(b.toString());
	}

	/**
	 * <i>Message Level: <b>Fatal Error</b></i><br />
	 * This type of log messages records some critical error that makes it
	 * impossible to run anymore.
	 * 
	 * @param msg The message to record.
	 */
	public void fatal(String msg) {
		StringBuilder b = new StringBuilder();
		b.append('[').append(getSysTime()).append(']');
		b.append("[FATAL ERROR]");
		b.append('[').append(source).append(']');
		b.append(msg);
		System.out.println(b.toString());
		writeToLog(b.toString());
	}

	/**
	 * Debug messages when debug mode is on.
	 * 
	 * @param msg The message to record.
	 */
	public void debug(String msg) {
		StringBuilder b = new StringBuilder();
		b.append('[').append(getSysTime()).append(']');
		b.append("[DEBUG]");
		b.append('[').append(source).append(']');
		b.append(msg);
		System.out.println(b.toString());
		writeToLog(b.toString());
	}

	/**
	 * Write the recorded information to the log file.
	 * 
	 * @param msg The message to write.
	 */
	private void writeToLog(String msg) {
		writeLine(log.toString(), msg);
	}

}
