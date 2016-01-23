package com.dexesttp.hkxpack.hkx.structs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.dexesttp.hkxpack.hkx.exceptions.InvalidPositionException;

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
	
	public void read(long position, DataInterface dataInterface) throws IOException, InvalidPositionException {
		for(Member member : members)
			member.read(position, dataInterface);
	}

	public int getSize() {
		Member last = members.get(members.size() - 1);
		if(last != null)
			return (int) (last.offset + last.toRead.length);
		else
			return 0;
	}
}
