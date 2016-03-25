package com.dexesttp.hkxpack.hkxreader.member;

import com.dexesttp.hkxpack.data.members.HKXMember;
import com.dexesttp.hkxpack.data.members.HKXPointerMember;
import com.dexesttp.hkxpack.descriptor.enums.HKXType;
import com.dexesttp.hkxpack.hkx.data.DataExternal;
import com.dexesttp.hkxpack.hkx.exceptions.InvalidPositionException;
import com.dexesttp.hkxpack.hkxreader.HKXReaderConnector;
import com.dexesttp.hkxpack.hkxreader.PointerNameGenerator;

class HKXPointerMemberReader implements HKXMemberReader {
	private final HKXReaderConnector connector;
	private final PointerNameGenerator generator;
	private final String name;
	private final HKXType vtype;
	private final long memberOffset;

	HKXPointerMemberReader(HKXReaderConnector connector, PointerNameGenerator generator, String name, HKXType contentType, long offset) {
		this.connector = connector;
		this.generator = generator;
		this.name = name;
		this.vtype = contentType;
		this.memberOffset = offset;
	}

	@Override
	public HKXMember read(long classOffset) throws InvalidPositionException {
		DataExternal data = connector.data2.readNext();
		String target = "null";
		if(data.from == memberOffset + classOffset) {
			target = generator.get(data.to);
		} else {
			connector.data2.backtrack();
		}
		HKXMember result = new HKXPointerMember(name, HKXType.TYPE_POINTER, vtype, target);
		return result;
	}
}
