package com.dexesttp.hkxpack.hkxreader.member.arrays;

import java.io.IOException;
import java.io.RandomAccessFile;

import com.dexesttp.hkxpack.data.HKXData;
import com.dexesttp.hkxpack.data.members.HKXStringMember;
import com.dexesttp.hkxpack.descriptor.enums.HKXType;
import com.dexesttp.hkxpack.hkx.data.DataInternal;
import com.dexesttp.hkxpack.hkx.exceptions.InvalidPositionException;
import com.dexesttp.hkxpack.hkxreader.HKXReaderConnector;
import com.dexesttp.hkxpack.resources.ByteUtils;

class HKXStringArrayContentsReader implements HKXArrayContentsReader {
	private HKXReaderConnector connector;
	private HKXType contentsType;

	HKXStringArrayContentsReader(HKXReaderConnector connector, HKXType contentsType) {
		this.connector = connector;
		this.contentsType = contentsType;
	}

	@Override
	public long getSize() {
		return 0x08;
	}

	@Override
	public HKXData getContents(long arrayStart, int position) throws IOException, InvalidPositionException {
		long descriptorPosition = arrayStart + position * getSize();
		DataInternal data = connector.data1.readNext();
		String contents = "";
		if(data.from == descriptorPosition) {
			RandomAccessFile file = connector.data.setup(data.to);
			contents = ByteUtils.readString(file);
		} else {
			connector.data1.backtrack();
		}
		HKXStringMember result = new HKXStringMember("", contentsType);
		result.set(contents);
		return result;
	}

}
