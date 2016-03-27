package com.dexesttp.hkxpack.cli.utils;

/**
 * Thrown if there was an error while parsing arguments.
 */
public class WrongSizeException extends Exception {
	private static final long serialVersionUID = -4241588440572992978L;
	public WrongSizeException(String option) {
		super(option);
	}
}
