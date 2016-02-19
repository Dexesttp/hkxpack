package com.dexesttp.hkxpack.hkxreader.member;

import java.io.IOException;

import com.dexesttp.hkxpack.data.HKXData;
import com.dexesttp.hkxpack.data.members.HKXMember;
import com.dexesttp.hkxpack.data.members.HKXPointerMember;
import com.dexesttp.hkxpack.descriptor.enums.HKXType;
import com.dexesttp.hkxpack.hkx.data.DataExternal;
import com.dexesttp.hkxpack.hkx.exceptions.InvalidPositionException;
import com.dexesttp.hkxpack.hkxreader.HKXReaderConnector;
import com.dexesttp.hkxpack.hkxreader.PointerNameGenerator;

public class HKXPointerArrayMemberReader extends HKXArrayMemberReader {
	private final PointerNameGenerator generator;

	HKXPointerArrayMemberReader(HKXReaderConnector connector, PointerNameGenerator generator, String name, HKXType subtype, long offset) {
		super(connector, name, subtype, offset);
		this.generator = generator;
	}

	@Override
	protected HKXData getContents(long arrayStart, int position) throws IOException, InvalidPositionException {
		long contentsPosition = arrayStart + position * 0x08;
		DataExternal data = connector.data2.readNext();
		String target = "null";
		if(data.from == contentsPosition) {
			target = generator.get(data.to);
		} else {
			connector.data2.backtrack();
		}
		HKXMember result = new HKXPointerMember(name, HKXType.TYPE_POINTER, HKXType.TYPE_NONE, target);
		return result;
	}

}
