package com.dexesttp.hkxpack.xml.classxml.exceptions;

public class NonResolvedClassException extends Exception {
	private static final long serialVersionUID = 2918935976191467569L;

	public NonResolvedClassException(String name) {
		super("Class non resolved : " + name);
	}
}
