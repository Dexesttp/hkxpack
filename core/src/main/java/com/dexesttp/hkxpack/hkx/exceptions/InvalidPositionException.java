package com.dexesttp.hkxpack.hkx.exceptions;

public class InvalidPositionException extends Exception {
	private static final long serialVersionUID = 5256901069828621035L;
	private final String section;
	
	public InvalidPositionException(String sectName, long pos) {
		super("Invalid position in " + sectName + " : " + pos);
		this.section = sectName;
	}

	public String getSection() {
		return section;
	}
}
