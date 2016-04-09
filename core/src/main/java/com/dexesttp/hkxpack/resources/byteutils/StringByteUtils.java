package com.dexesttp.hkxpack.resources.byteutils;

import java.nio.ByteBuffer;

/**
 * Provides utility functions to read a String from a {@link ByteBuffer}.
 */
final class StringByteUtils {
	private StringByteUtils() {
		// NO OP
	}
	
	/**
	 * Reads the next 0-terminated string from the given {@link ByteBuffer}.<br />
	 * This will change the {@link ByteBuffer#position()} value to just after the 0 of the Null-terminated string.
	 * @param inputByteBuffer the {@link ByteBuffer} to read from
	 * @return the read {@link String}
	 */
	static String readString(final ByteBuffer inputByteBuffer) {
		byte readByte = inputByteBuffer.get();
		StringBuffer resultBuffer = new StringBuffer();
		while(readByte != 0) {
			resultBuffer.append((char) readByte);
			readByte = inputByteBuffer.get();
		}
		return resultBuffer.toString();
	}
}
