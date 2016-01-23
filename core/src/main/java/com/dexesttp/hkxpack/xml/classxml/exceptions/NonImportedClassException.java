package com.dexesttp.hkxpack.xml.classxml.exceptions;

public class NonImportedClassException extends Exception {
	private static final long serialVersionUID = -4321691873312079978L;

	public NonImportedClassException(String name) {
		super("Class non imported : " + name);
	}
}
