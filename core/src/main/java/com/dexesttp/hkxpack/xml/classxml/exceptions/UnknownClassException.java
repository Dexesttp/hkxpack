package com.dexesttp.hkxpack.xml.classxml.exceptions;

public class UnknownClassException extends Exception {
	private static final long serialVersionUID = 5406060901270382196L;

	public UnknownClassException(String name) {
		super("Class not known (meaning not asked to be imported) : " + name);
	}
}
