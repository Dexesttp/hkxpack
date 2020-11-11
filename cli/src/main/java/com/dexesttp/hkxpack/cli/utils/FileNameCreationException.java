package com.dexesttp.hkxpack.cli.utils;

/**
 * Thrown when a file couldn't be converted to its HKX or XML equivalent.
 */
public class FileNameCreationException extends Exception {
	private static final long serialVersionUID = -4150191898446776060L;

	/**
	 * Create a {@link FileNameCreationException}
	 * 
	 * @param message        the message of the exception
	 * @param innerException the exception that causes this exception to be thrown
	 */
	public FileNameCreationException(final String message, final Exception innerException) {
		super(message, innerException);
	}
}
