package com.dexesttp.hkxpack.tagreader.exceptions;

/**
 * Thrown where a non XML error was enocuntered while understanding the TagXML file.
 */
public class InvalidTagXMLException extends Exception {
	private static final long serialVersionUID = -7902902818953946055L;

	/**
	 * Creates an {@link InvalidTagXMLException}.
	 * @param string the message.
	 */
	public InvalidTagXMLException(final String string) {
		super(string);
	}
}
