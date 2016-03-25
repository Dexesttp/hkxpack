package com.dexesttp.hkxpack.hkxreader.member;

import java.io.IOException;
import java.nio.ByteBuffer;

import com.dexesttp.hkxpack.data.HKXData;
import com.dexesttp.hkxpack.data.members.HKXStringMember;
import com.dexesttp.hkxpack.descriptor.enums.HKXType;
import com.dexesttp.hkxpack.hkx.data.DataInternal;
import com.dexesttp.hkxpack.hkx.exceptions.InvalidPositionException;
import com.dexesttp.hkxpack.hkxreader.HKXReaderConnector;
import com.dexesttp.hkxpack.resources.ByteUtils;

class HKXStringArrayMemberReader extends HKXArrayMemberReader {

	HKXStringArrayMemberReader(HKXReaderConnector connector, String name, HKXType subtype, long offset) {
		super(connector, name, subtype, offset);
	}

	@Override
	protected HKXData getContents(long arrayStart, int position) throws IOException, InvalidPositionException {
		long descriptorPosition = arrayStart + position * 0x08;
		DataInternal data = connector.data1.readNext();
		String contents = "";
		if(data.from == descriptorPosition) {
			ByteBuffer file = connector.data.setup(data.to);
			contents = ByteUtils.readString(file);
		} else {
			connector.data1.backtrack();
		}
		HKXStringMember result = new HKXStringMember(name, subtype);
		result.set(contents);
		return result;
	}

}
