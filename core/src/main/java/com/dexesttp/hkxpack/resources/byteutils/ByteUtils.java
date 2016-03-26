package com.dexesttp.hkxpack.resources.byteutils;

import java.nio.ByteBuffer;
 
/**
 * Entry point for all things byte relevant. These are merely convenience functiosn to access all classes inside 
 * <p>
 * @see SLongByteUtils
 * @see ULongByteUtils
 * @see StringByteUtils
 * @see FloatByteUtils
 */
public class ByteUtils {
	
	// Get functions
	public static int getSInt(byte[] list) {
		return (int) SLongByteUtils.getLong(list);
	}

	public static int getUInt(byte[] list) {
		return (int) ULongByteUtils.getLong(list);
	}

	public static long getULong(byte[] list) {
		return ULongByteUtils.getLong(list);
	}

	public static float getFloat(byte[] value) {
		return FloatByteUtils.getFloat(value);
	}

	// From functions
	public static byte[] fromUInt(int value) {
		return ULongByteUtils.fromLong(value, 4);
	}

	public static byte[] fromSLong(long value, int numBytes) {
		return SLongByteUtils.fromLong(value, numBytes);
	}

	public static byte[] fromULong(long value, int numBytes) {
		return ULongByteUtils.fromLong(value, numBytes);
	}

	public static byte[] fromFloat(double value, int numBytes) {
		return FloatByteUtils.fromFloat(value, numBytes);
	}

	// ToString functions.
	public static String getSIntString(byte[] list) {
		return ""+getSInt(list);
	}

	public static String getUIntString(byte[] list) {
		return ""+getUInt(list);
	}

	public static String getULongString(byte[] list) {
		return ""+getULong(list);
	}

	/**
	 * Read a null-terminated string from a {@link ByteBuffer}
	 * @param inputByteBuffer the {@link ByteBuffer} to read from
	 * @return the read {@link String}
	 * @see StringByteUtils#readString(ByteBuffer)
	 */
	public static String readString(ByteBuffer inputByteBuffer) {
		return StringByteUtils.readString(inputByteBuffer);
	}
}
