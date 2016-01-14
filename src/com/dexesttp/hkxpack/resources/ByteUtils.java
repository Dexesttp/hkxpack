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

	public static String readString(RandomAccessFile in) throws IOException {
		String s = "";
		byte b;
		while((b = in.readByte()) != 0)
			s += (char) b;
		return s;
	}
}
