package com.dexesttp.hkxpack.hkxreader.member.arrays;

import java.io.IOException;

import com.dexesttp.hkxpack.data.HKXData;
import com.dexesttp.hkxpack.data.members.HKXMember;
import com.dexesttp.hkxpack.data.members.HKXPointerMember;
import com.dexesttp.hkxpack.descriptor.enums.HKXType;
import com.dexesttp.hkxpack.hkx.data.DataExternal;
import com.dexesttp.hkxpack.hkx.exceptions.InvalidPositionException;
import com.dexesttp.hkxpack.hkxreader.HKXReaderConnector;
import com.dexesttp.hkxpack.hkxreader.PointerNameGenerator;

public class HKXPointerArrayContentsReader implements HKXArrayContentsReader {
	private final PointerNameGenerator generator;
	private final HKXReaderConnector connector;

	HKXPointerArrayContentsReader(HKXReaderConnector connector, PointerNameGenerator generator) {
		this.connector = connector;
		this.generator = generator;
	}

	@Override
	public long getSize() {
		return 0x08;
	}

	@Override
	public HKXData getContents(long arrayStart, int position) throws IOException, InvalidPositionException {
		long contentsPosition = arrayStart + position * getSize();
		DataExternal data = connector.data2.readNext();
		String target = "null";
		if(data.from == contentsPosition) {
			target = generator.get(data.to);
		} else {
			connector.data2.backtrack();
		}
		HKXMember result = new HKXPointerMember("", HKXType.TYPE_POINTER, HKXType.TYPE_NONE, target);
		return result;
	}
}
