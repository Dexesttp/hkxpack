package com.dexesttp.hkxpack.hkxreader.member;

import java.io.IOException;
import java.nio.ByteBuffer;

import com.dexesttp.hkxpack.data.members.HKXArrayMember;
import com.dexesttp.hkxpack.data.members.HKXMember;
import com.dexesttp.hkxpack.descriptor.enums.HKXType;
import com.dexesttp.hkxpack.hkx.exceptions.InvalidPositionException;
import com.dexesttp.hkxpack.hkxreader.HKXReaderConnector;
import com.dexesttp.hkxpack.hkxreader.member.arrays.HKXArrayContentsReader;
import com.dexesttp.hkxpack.resources.ByteUtils;

public class HKXRelArrayMemberReader implements HKXMemberReader {
	private final HKXReaderConnector connector;
	private final String name;
	private final HKXType subtype;
	private final long offset;
	private final HKXArrayContentsReader internals;

	public HKXRelArrayMemberReader(HKXReaderConnector connector, String name, HKXType subtype, HKXArrayContentsReader arrayContentsReader, long offset) {
		this.connector = connector;
		this.name = name;
		this.subtype = subtype;
		this.internals = arrayContentsReader;
		this.offset = offset;
	}
	
	@Override
	public HKXMember read(long classOffset) throws IOException, InvalidPositionException {
		ByteBuffer file = connector.data.setup(classOffset + offset);
		byte[] bSize = new byte[2];
		byte[] bOff = new byte[2];
		file.get(bSize);
		file.get(bOff);
		int size = ByteUtils.getInt(bSize)-1;
		int offset = ByteUtils.getInt(bOff);
		HKXArrayMember res = new HKXArrayMember(name, HKXType.TYPE_RELARRAY, subtype);
		for(int i = 0; i < size; i++)
			res.add(internals.getContents(classOffset + offset, i));
		return res;
	}

}
