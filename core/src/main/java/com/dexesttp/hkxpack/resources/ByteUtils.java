package com.dexesttp.hkxpack.resources;

import java.io.IOException;
import java.io.RandomAccessFile;

public class ByteUtils {
	public static int getInt(byte[] list) {
		final int len = list.length;
		int accu = 1;
		int res = 0;
		for(int i = 0; i < len; i++) {
			res += ((int) (list[i] & 0xFF)) * accu;
			accu *= 256;
		}
		return res;
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

	public static String readString(RandomAccessFile in) throws IOException {
		String s = "";
		byte b;
		while((b = in.readByte()) != 0)
			s += (char) b;
		return s;
	}

	// TODO output actually signed int please.
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

	public static byte[] fromInt(int value) {
		byte[] res = new byte[4];
		res[3] = (byte) (value / (Byte.MAX_VALUE ^3));
		res[2] = (byte) ((value / (Byte.MAX_VALUE ^2)) % Byte.MAX_VALUE);
		res[1] = (byte) ((value / Byte.MAX_VALUE) % Byte.MAX_VALUE);
		res[0] = (byte) (value % Byte.MAX_VALUE);
		return res;
	}

	public static float getFloat(byte[] value) {
		int val = getInt(value);
		return Float.intBitsToFloat(val);
	}
}
