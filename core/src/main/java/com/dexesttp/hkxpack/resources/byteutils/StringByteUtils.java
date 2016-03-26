package com.dexesttp.hkxpack.resources.byteutils;

import java.nio.ByteBuffer;

/**
 * Provides utility functions to read a String from a {@link ByteBuffer}.
 */
class StringByteUtils {
	/**
	 * Reads the next 0-terminated string from the given {@link ByteBuffer}.<br />
	 * This will change the {@link ByteBuffer#position()} value to just after the 0 of the Null-terminated string.
	 * @param inputByteBuffer the {@link ByteBuffer} to read from
	 * @return the read {@link String}
	 */
	static String readString(ByteBuffer inputByteBuffer) {
		String s = "";
		byte b;
		while((b = inputByteBuffer.get()) != 0)
			s += (char) b;
		return s;
	}
}
