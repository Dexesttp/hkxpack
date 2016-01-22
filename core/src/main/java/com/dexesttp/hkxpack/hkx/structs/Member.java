package com.dexesttp.hkxpack.hkx.structs;

import java.io.IOException;
import java.io.RandomAccessFile;

public class Member {
	public final long offset;
	public final byte[] toRead;

	public Member(int size, long offset) {
		toRead = new byte[size];
		this.offset = offset;
	}

	public Member(byte[] content, long offset) {
		toRead = content.clone();
		this.offset = offset;
	}
	
	public void read(RandomAccessFile file) throws IOException {
		file.read(toRead);
	}
}
