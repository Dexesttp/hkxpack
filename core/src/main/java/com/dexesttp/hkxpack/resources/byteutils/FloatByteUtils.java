package com.dexesttp.hkxpack.resources.byteutils;

/**
 * Handles converting {@link float} and {@link double} values from and to Little-Endian {@link byte} arrays
 */
final class FloatByteUtils {
	private static final int DOUBLE_SIZE = 0x08;
	private FloatByteUtils() {
		// NO OP
	}

	/**
	 * Convert a {@link double} value to a {@link byte} array.
	 * @param value the {@link double} value to convert
	 * @param numBytes the number of bytes in the {@link byte} array
	 * @return the relevant {@link byte} array
	 */
	static byte[] fromFloat(final double value, final int numBytes) {
		if(numBytes == DOUBLE_SIZE) {
			long temp = Double.doubleToLongBits(value);
			return ULongByteUtils.fromLong(temp, numBytes);
		} else {
			int temp = Float.floatToIntBits((float) value);
			return ULongByteUtils.fromLong(temp, numBytes);
		}
	}

	/**
	 * Convert a {@link byte} array containing a {@link float} value to a {@link float}.
	 * @param value the {@link byte} array to convert
	 * @return the converted {@link float}
	 */
	static float getFloat(final byte[] value) {
		int val = (int) ULongByteUtils.getLong(value);
		return Float.intBitsToFloat(val);
	}
}
