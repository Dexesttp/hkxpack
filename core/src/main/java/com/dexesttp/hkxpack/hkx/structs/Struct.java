package com.dexesttp.hkxpack.hkx.structs;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class Struct {
	public final List<Member> members;
	
	public Struct() {
		members = new ArrayList<>();
	}
	
	/**
	 * 
	 * @param sizes each size has the format [offset, size]
	 */
	public Struct(int[]... sizes) {
		this();
		for(int[] size : sizes)
			members.add(new Member(size[1], size[0]));
	}
	
	public void read(RandomAccessFile file) throws IOException {
		for(Member member : members)
			member.read(file);
	}
}
