package com.dexesttp.hkxpack.resources;

public class InvalidFormatException extends Exception {
	private static final long serialVersionUID = -2824271887951719499L;
	public InvalidFormatException(int version) {
		super("Invalid version : " + version);
	}
}
