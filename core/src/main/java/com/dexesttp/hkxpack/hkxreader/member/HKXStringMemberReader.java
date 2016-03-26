package com.dexesttp.hkxpack.hkxreader.member;

import java.nio.ByteBuffer;

import com.dexesttp.hkxpack.data.members.HKXMember;
import com.dexesttp.hkxpack.data.members.HKXStringMember;
import com.dexesttp.hkxpack.descriptor.enums.HKXType;
import com.dexesttp.hkxpack.hkx.data.DataInternal;
import com.dexesttp.hkxpack.hkx.exceptions.InvalidPositionException;
import com.dexesttp.hkxpack.hkxreader.HKXReaderConnector;
import com.dexesttp.hkxpack.resources.ByteUtils;

class HKXStringMemberReader implements HKXMemberReader {
	private final HKXReaderConnector connector;
	private final String name;
	private final long memberOffset;
	private final HKXType vtype;

	HKXStringMemberReader(HKXReaderConnector connector, String name, HKXType vtype, long offset) {
		this.connector = connector;
		this.name = name;
		this.memberOffset = offset;
		this.vtype = vtype;
	}

	@Override
	public HKXMember read(long classOffset) throws InvalidPositionException {
		String contents = "";
		try {
			DataInternal data = connector.data1.readNext();
			if(data.from == memberOffset + classOffset) {
				ByteBuffer file = connector.data.setup(data.to);
				contents = ByteUtils.readString(file);
			} else {
				connector.data1.backtrack();
			}
		} catch(InvalidPositionException e) {
			// NO OP.
		}
		HKXStringMember result = new HKXStringMember(name, vtype);
		result.set(contents);
		return result;
	}
}
