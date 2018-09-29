package cn.stateofwar.sowr.util;

import java.io.File;
import java.io.IOException;

import cn.stateofwar.sowr.References;

/**
 * Logger for the program. For every class that requires log recording, it needs
 * a private static final instance of this class.
 */
public class Logger {

	/** The file to store logs. */
	private File log = new File(References.LOG_FILE_NAME);

	/** The source that processes logging. */
	private String source;

	/**
	 * Create a logger for the class constructed it. It should be private static
	 * final to avoid anything strange...
	 * 
	 * @param _source Who is recording the log massage.
	 */
	public Logger(String _source) {
		source = _source;
	}

	/**
	 * Create a logger for the class constructed it. It should be private static
	 * final to avoid anything strange...
	 */
	public Logger() {
		source = "Unknown";
	}

	/**
	 * Initialize the log file. This should only be called once, otherwise the old
	 * log file will be erased.
	 */
	public void init() {
		try {
			Utils.createFile(log.toString(), true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * <i>Message Level: <b>Information</b></i><br />
	 * This type of log messages records the procedures of the program.
	 * 
	 * @param message The message to record.
	 */
	public void info(String message) {
		StringBuilder b = new StringBuilder();
		b.append('[').append(Utils.getSysTime()).append(']');
		b.append("[INFO]");
		b.append('[').append(source).append(']');
		b.append(message);
		System.out.println(b.toString());
		writeToLog(b.toString());
	}

	/**
	 * <i>Message Level: <b>Warning</b></i><br />
	 * This type of log messages records something that is not expected when
	 * running, but they might not trigger huge impact.
	 * 
	 * @param message The message to record.
	 */
	public void warn(String message) {
		StringBuilder b = new StringBuilder();
		b.append('[').append(Utils.getSysTime()).append(']');
		b.append("[WARNING]");
		b.append('[').append(source).append(']');
		b.append(message);
		System.out.println(b.toString());
		writeToLog(b.toString());
	}

	/**
	 * <i>Message Level: <b>Error</b></i><br />
	 * This type of log messages records the errors that disable some of the
	 * program. Although they might not be very dangerous, users should take a look
	 * at this and report to the development team.
	 * 
	 * @param message The message to record.
	 */
	public void error(String message) {
		StringBuilder b = new StringBuilder();
		b.append('[').append(Utils.getSysTime()).append(']');
		b.append("[ERROR]");
		b.append('[').append(source).append(']');
		b.append(message);
		System.out.println(b.toString());
		writeToLog(b.toString());
	}

	/**
	 * <i>Message Level: <b>Fatal Error</b></i><br />
	 * This type of log messages records some critical error that makes it
	 * impossible to run anymore.
	 * 
	 * @param message The message to record.
	 */
	public void fatal(String message) {
		StringBuilder b = new StringBuilder();
		b.append('[').append(Utils.getSysTime()).append(']');
		b.append("[FATAL ERROR]");
		b.append('[').append(source).append(']');
		b.append(message);
		System.out.println(b.toString());
		writeToLog(b.toString());
	}

	/**
	 * Debug messages when debug mode is on, or when developers try to detect bugs.
	 * 
	 * @param msg The message to record.
	 */
	public void debug(String msg) {
		StringBuilder b = new StringBuilder();
		b.append("[========DEBUG========]");
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
		try {
			Utils.writeLine(log.toString(), msg);
		} catch (IOException e) {
			System.out.println(e.getLocalizedMessage());
			e.printStackTrace();
		}
	}

}
