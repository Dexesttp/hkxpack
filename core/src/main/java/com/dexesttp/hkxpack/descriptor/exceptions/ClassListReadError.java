package com.dexesttp.hkxpack.descriptor.exceptions;

import java.io.IOException;

/**
 * A ClassListReadError is thrown when the class list couldn't be read properly.
 * <p>
 * The ClassList path is supposed to be defined as a static, final resource in {@link com.dexesttp.hkxpack.descriptor.reader.ClassXMLList}.
 */
public class ClassListReadError extends IOException {
	private static final long serialVersionUID = -6189166645845112170L;

	public ClassListReadError(String message) {
		super(message);
	}

	public ClassListReadError(Throwable throwable) {
		super(throwable);
	}

	public ClassListReadError(String message, Throwable throwable) {
		super(message, throwable);
	}
}
