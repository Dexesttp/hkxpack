package com.dexesttp.hkxpack.resources.byteutils;

import java.nio.ByteBuffer;

/**
 * Entry point for all things byte relevant. These are merely convenience
 * functiosn to access all classes inside
 * <p>
 * 
 * @see SLongByteUtils
 * @see ULongByteUtils
 * @see StringByteUtils
 * @see FloatByteUtils
 */
public final class ByteUtils {
	private ByteUtils() {
		// NO OP
	}

	// Get functions
	/**
	 * @see SLongByteUtils#getLong(byte[])
	 */
	public static int getSInt(final byte[] list) {
		return (int) SLongByteUtils.getLong(list);
	}

	/**
	 * @see ULongByteUtils#getLong(byte[])
	 */
	public static int getUInt(final byte[] list) {
		return (int) ULongByteUtils.getLong(list);
	}

	/**
	 * @see SLongByteUtils#getLong(byte[])
	 */
	public static long getULong(final byte[] list) {
		return ULongByteUtils.getLong(list);
	}

	/**
	 * @see FloatByteUtils#getFloat(byte[])
	 */
	public static float getFloat(final byte[] value) {
		return FloatByteUtils.getFloat(value);
	}

	// From functions
	/**
	 * @see ULongByteUtils#fromLong(byte[])
	 */
	public static byte[] fromUInt(final int value) {
		return ULongByteUtils.fromLong(value, 4);
	}

	/**
	 * @see SLongByteUtils#fromLong(byte[])
	 */
	public static byte[] fromSLong(final long value, final int numBytes) {
		return SLongByteUtils.fromLong(value, numBytes);
	}

	/**
	 * @see ULongByteUtils#fromLong(byte[])
	 */
	public static byte[] fromULong(final long value, final int numBytes) {
		return ULongByteUtils.fromLong(value, numBytes);
	}

	/**
	 * @see FloatByteUtils#fromFloat(byte[])
	 */
	public static byte[] fromFloat(final double value, final int numBytes) {
		return FloatByteUtils.fromFloat(value, numBytes);
	}

	// ToString functions.
	/**
	 * Convert a {@link byte} array representing a signed {@link int} to a
	 * {@link String}.
	 * 
	 * @param list the {@link byte} array
	 * @return the relevant {@link String}.
	 */
	public static String getSIntString(final byte[] list) {
		return Integer.toString(getSInt(list));
	}

	/**
	 * Convert a {@link byte} array representing an unsigned {@link int} to a
	 * {@link String}.
	 * 
	 * @param list the {@link byte} array
	 * @return the relevant {@link String}.
	 */
	public static String getUIntString(final byte[] list) {
		return Integer.toString(getUInt(list));
	}

	/**
	 * Convert a {@link byte} array representing a Signed {@link long} to a
	 * {@link String}.
	 * 
	 * @param list the {@link byte} array
	 * @return the relevant {@link String}.
	 */
	public static String getULongString(final byte[] list) {
		return Long.toString(getULong(list));
	}

	/**
	 * Read a null-terminated string from a {@link ByteBuffer}
	 * 
	 * @param inputByteBuffer the {@link ByteBuffer} to read from
	 * @return the read {@link String}
	 * @see StringByteUtils#readString(ByteBuffer)
	 */
	public static String readString(final ByteBuffer inputByteBuffer) {
		return StringByteUtils.readString(inputByteBuffer);
	}
}
