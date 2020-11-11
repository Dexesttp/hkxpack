package com.dexesttp.hkxpack.descriptor.exceptions;

import java.io.IOException;

/**
 * A {@link ClassFileReadException} is thrown when a class file described in the
 * class list couldn't be read.
 */
public class ClassFileReadException extends IOException {
	private static final long serialVersionUID = 3053825597885294212L;

	/**
	 * Create a {@link ClassFileReadException}.
	 * 
	 * @param message the error message
	 */
	public ClassFileReadException(final String message) {
		super(message);
	}

	/**
	 * Create a {@link ClassFileReadException}.
	 * 
	 * @param throwable the exception that caused the
	 *                  {@link ClassFileReadException}.
	 */
	public ClassFileReadException(final Throwable throwable) {
		super(throwable);
	}

	/**
	 * Create a {@link ClassFileReadException}.
	 * 
	 * @param message   the error message
	 * @param throwable the exception that caused the
	 *                  {@link ClassFileReadException}.
	 */
	public ClassFileReadException(final String message, final Throwable throwable) {
		super(message, throwable);
	}
}
