package com.dexesttp.hkxpack.resources;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles all logged operations.
 */
public final class LoggerUtil {
	private static List<Throwable> eList = new ArrayList<>();

	private LoggerUtil() {
		// NO OP
	}

	/**
	 * Add an exception as a log.
	 * 
	 * @param exception the exception to add.
	 */
	public static void add(final Throwable exception) {
		eList.add(exception);
	}

	/**
	 * Add a new generic exception as a log, by its message.
	 * 
	 * @param message the message to add
	 */
	public static void addNewException(final String message) {
		eList.add(new Exception(message));
	}

	/**
	 * Get the logger list.
	 * 
	 * @return
	 */
	public static List<Throwable> getList() {
		return eList;
	}
}
