package com.dexesttp.hkxpack.resources;

import java.nio.ByteBuffer;
 

public class ByteUtils {
	public static int getSInt(byte[] list) {
		final int len = list.length;
		int accu = 1;
		int res = 0;
		for(int i = 0; i < len; i++) {
			res += ((int) (list[i] & 0xFF)) * accu;
			accu *= 256;
		}
		return res;
	}
	
	public static int getInt(byte[] list) {
		// TODO remake this
		return getSInt(list);
	}

	public static long getLong(byte[] list) {
		final int len = list.length;
		long accu = 1;
		long res = 0;
		for(int i = 0; i < len; i++) {
			res += ((int) (list[i] & 0xFF)) * accu;
			accu *= 256;
		}
		return res;
	}

	public static float getFloat(byte[] value) {
		int val = getInt(value);
		return Float.intBitsToFloat(val);
	}

	public static String readString(ByteBuffer in) {
		String s = "";
		byte b;
		while((b = in.get()) != 0)
			s += (char) b;
		return s;
	}

	public static byte[] fromInt(int value) {
		return fromLong(value, 4);
	}

	public static byte[] fromLong(long value, int numBytes) {
		long mask = 0xFF;
		byte[] res = new byte[numBytes];
		for(int i = 0; i < numBytes; i++) {
			res[i] = (byte) (value & mask);
			value = value >> 8;
		}
		return res;
	}

	public static byte[] fromFloat(double double1, int i) {
		if(i == 8) {
			long temp = Double.doubleToLongBits(double1);
			return fromLong(temp, i);
		} else {
			int temp = Float.floatToIntBits((float) double1);
			return fromLong(temp, i);
		}
	}

	
	// ToString functions.
	public static String getSIntString(byte[] list) {
		return ""+getSInt(list);
	}

	public static String getIntString(byte[] list) {
		return ""+getInt(list);
	}

	public static String getLongString(byte[] list) {
		final int len = list.length;
		long accu = 1;
		long res = 0;
		for(int i = 0; i < len; i++) {
			res += ((int) (list[i] & 0xFF)) * accu;
			accu *= 256;
		}
		return ""+res;
	}

	public static byte[] fromSLong(long character, int i) {
		// TODO maybe revisit this ?
		byte[] res = fromLong(character, i);
		return res;
	}
}
