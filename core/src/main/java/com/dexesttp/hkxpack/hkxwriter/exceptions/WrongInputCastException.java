package com.dexesttp.hkxpack.hkxwriter.exceptions;

public class WrongInputCastException extends Exception {
	private static final long serialVersionUID = 6483999356904172141L;

	public WrongInputCastException(String message) {
		super(message);
	}

	public WrongInputCastException(Throwable e) {
		super(e);
	}

	public WrongInputCastException(String message, Throwable e) {
		super(message, e);
	}
}
