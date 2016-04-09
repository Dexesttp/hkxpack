package com.dexesttp.hkxpack.descriptor.exceptions;

import java.io.IOException;

/**
 * A ClassListReadError is thrown when the class list couldn't be read properly.
 * <p>
 * The ClassList path is supposed to be defined as a static, final resource in {@link com.dexesttp.hkxpack.descriptor.reader.ClassXMLList}.
 */
public class ClassListReadException extends IOException {
	private static final long serialVersionUID = -6189166645845112170L;

	/**
	 * Creates a {@link ClassListReadException}.
	 * @param message a message to explain the exception.
	 */
	public ClassListReadException(final String message) {
		super(message);
	}

	/**
	 * Creates a {@link ClassListReadException}.
	 * @param throwable the exception that caused the {@link ClassListReadException}.
	 */
	public ClassListReadException(final Throwable throwable) {
		super(throwable);
	}

	/**
	 * Creates a {@link ClassListReadException}.
	 * @param message a message to explain the exception.
	 * @param throwable the exception that caused the {@link ClassListReadException}.
	 */
	public ClassListReadException(final String message, final Throwable throwable) {
		super(message, throwable);
	}
}
