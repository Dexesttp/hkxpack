package com.dexesttp.hkxpack.resources.byteutils;

/**
 * Handles converting unsigned {@link long} values from and to Little-Endian
 * {@link byte} arrays
 */
final class ULongByteUtils {
	private static final long BYTE_MASK = 0xFF;

	private ULongByteUtils() {
		// NO OP
	}

	/**
	 * Get an unsigned long from a {@link byte} array.
	 * 
	 * @param list the Little-Endian {@link byte} array to convert
	 * @return the converted unsigned {@link long} value.
	 */
	static long getLong(final byte[] list) {
		final int len = list.length;
		long accu = 1;
		long res = 0;
		for (int i = 0; i < len; i++) {
			res += ((int) (list[i] & 0xFF)) * accu;
			accu *= 256;
		}
		return res;
	}

	/**
	 * Get a {@link byte} array from an unsigned {@link long} value.
	 * 
	 * @param value    the value to convert
	 * @param numBytes the number of bytes in the output {@link byte} array
	 * @return a Little-Endian {@link byte} array.
	 */
	static byte[] fromLong(final long value, final int numBytes) {
		long leftedValue = value;
		byte[] res = new byte[numBytes];
		for (int i = 0; i < numBytes; i++) {
			res[i] = (byte) (leftedValue & BYTE_MASK);
			leftedValue = leftedValue >> 8;
		}
		return res;
	}
}
