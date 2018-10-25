package cn.stateofwar.sowr.util;

import java.io.File;
import java.io.IOException;

import cn.stateofwar.sowr.References;

/**
 * Logger for the program. For every class that requires log recording, it needs
 * an instance of this class.
 */
public class Logger {

	/** A public logger, just in case. */
	public static final Logger PUBLIC_LOGGER = new Logger();

	/** File of record storage.. */
	private File log_file = new File(References.LOG_FILE_NAME);

	/** Source that processes logging. */
	private String source;

	/**
	 * Create a logger instance.
	 */
	public Logger() {
		source = "UNKNOWN";
	}

	/**
	 * Create a logger instance.
	 * 
	 * @param _source Source of recording.
	 */
	public Logger(String _source) {
		source = _source;
	}

	/**
	 * Initialize the log file.<br />
	 * <i>This should only be called once, otherwise the old log file will be
	 * erased.</i>
	 */
	public void init() {
		try {
			Utils.createFile(log_file.toString(), true);
		} catch (IOException e) {
			System.err.println(e.getLocalizedMessage());
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
		StringBuilder sb = new StringBuilder();
		sb.append('[').append(Utils.getSysTime(':')).append(']');
		sb.append("[INFO]");
		sb.append('[').append(source).append(']');
		sb.append(message);
		System.out.println(sb.toString());
		log(sb.toString());
	}

	/**
	 * <i>Message Level: <b>Warning</b></i><br />
	 * This type of log messages records something that is not expected when
	 * running, but they might not trigger huge impacts.
	 * 
	 * @param message The message to record.
	 */
	public void warn(String message) {
		StringBuilder sb = new StringBuilder();
		sb.append('[').append(Utils.getSysTime(':')).append(']');
		sb.append("[WARNING]");
		sb.append('[').append(source).append(']');
		sb.append(message);
		System.out.println(sb.toString());
		log(sb.toString());
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
		StringBuilder sb = new StringBuilder();
		sb.append('[').append(Utils.getSysTime(':')).append(']');
		sb.append("[ERROR]");
		sb.append('[').append(source).append(']');
		sb.append(message);
		System.err.println(sb.toString());
		log(sb.toString());
	}

	/**
	 * <i>Message Level: <b>Fatal Error</b></i><br />
	 * This type of log messages records some critical error that makes it
	 * impossible to run anymore.
	 * 
	 * @param message The message to record.
	 */
	public void fatal(String message) {
		StringBuilder sb = new StringBuilder();
		sb.append('[').append(Utils.getSysTime(':')).append(']');
		sb.append("[FATAL ERROR]");
		sb.append('[').append(source).append(']');
		sb.append(message);
		System.err.println(sb.toString());
		log(sb.toString());
	}

	/**
	 * Debug messages when debug mode is on, or when developers try to detect bugs.
	 * 
	 * @param message The message to record.
	 */
	public void debug(String message) {
		StringBuilder sb = new StringBuilder();
		sb.append('[').append(Utils.getSysTime(':')).append(']');
		sb.append("[===DEBUG===]");
		sb.append('[').append(source).append(']');
		sb.append(message);
		System.out.println(sb.toString());
		log(sb.toString());
	}

	/**
	 * Write the recorded information to the log file.
	 * 
	 * @param message The message to write.
	 */
	private void log(String message) {
		try {
			Utils.writeLine(References.LOG_FILE_NAME, message);
		} catch (IOException e) {
			System.err.println(e.getLocalizedMessage());
			e.printStackTrace();
		}
	}

}
