package com.dexesttp.hkxpack.xml.classxml.exceptions;

public class UnknownEnumerationException extends Exception {
	private static final long serialVersionUID = -3489082666424521307L;

	public UnknownEnumerationException(String name) {
		super("Unknown enumeration : " + name);
	}
}
