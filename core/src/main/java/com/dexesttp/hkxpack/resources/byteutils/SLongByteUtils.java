package com.dexesttp.hkxpack.resources.byteutils;

/**
 * Handles converting signed {@link long} values from and to Little-Endian {@link byte} arrays
 */
final class SLongByteUtils {
	private static final long BYTE_MASK = 0xFF;
	private SLongByteUtils() {
		// NO OP
	}
	
	/**
	 * Get a signed long from a {@link byte} array.
	 * @param list the Little-Endian {@link byte} array to convert
	 * @return the converted signed {@link long} value.
	 */
	static long getLong(final byte[] list) {
		final int len = list.length;
		int accu = 1;
		int res = 0;
		for(int i = 0; i < len; i++) {
			res += ((int) (list[i] & 0xFF)) * accu;
			accu *= 256;
		}
		return res;
	}

	/**
	 * Get a {@link byte} array from a signed {@link long} value.
	 * @param value the value to convert
	 * @param numBytes the number of bytes in the output {@link byte} array
	 * @return a Little-Endian {@link byte} array.
	 */
	static byte[] fromLong(final long value, final int numBytes) {
		long shiftedValue = value;
		byte[] res = new byte[numBytes];
		for(int i = 0; i < numBytes; i++) {
			res[i] = (byte) (shiftedValue & BYTE_MASK);
			shiftedValue = shiftedValue >> 8;
		}
		return res;
	}
}
