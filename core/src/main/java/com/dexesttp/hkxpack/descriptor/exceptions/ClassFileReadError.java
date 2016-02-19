package com.dexesttp.hkxpack.descriptor.exceptions;

import java.io.IOException;

/**
 * A ClassFileReadError is thrown when a class file described in the class list couldn't be read.
 */
public class ClassFileReadError extends IOException {
	private static final long serialVersionUID = 3053825597885294212L;

	public ClassFileReadError(String message) {
		super(message);
	}

	public ClassFileReadError(Throwable throwable) {
		super(throwable);
	}

	public ClassFileReadError(String message, Throwable throwable) {
		super(message, throwable);
	}
}
