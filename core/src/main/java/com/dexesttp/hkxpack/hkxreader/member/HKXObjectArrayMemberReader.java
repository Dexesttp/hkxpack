package com.dexesttp.hkxpack.hkxreader.member;

import java.io.IOException;

import com.dexesttp.hkxpack.data.HKXData;
import com.dexesttp.hkxpack.data.HKXObject;
import com.dexesttp.hkxpack.descriptor.HKXDescriptor;
import com.dexesttp.hkxpack.descriptor.enums.HKXType;
import com.dexesttp.hkxpack.hkx.exceptions.InvalidPositionException;
import com.dexesttp.hkxpack.hkx.types.MemberSizeResolver;
import com.dexesttp.hkxpack.hkxreader.HKXObjectReader;
import com.dexesttp.hkxpack.hkxreader.HKXReaderConnector;

class HKXObjectArrayMemberReader extends HKXArrayMemberReader {
	private final HKXObjectReader reader;
	private final HKXDescriptor descriptor;
	private final int contentSize;

	HKXObjectArrayMemberReader(HKXReaderConnector connector, HKXObjectReader reader, String name, long offset, HKXDescriptor descriptor) {
		super(connector, name, HKXType.TYPE_STRUCT, offset);
		this.reader = reader;
		this.descriptor = descriptor;
		this.contentSize = (int) MemberSizeResolver.getSize(descriptor);
	}

	@Override
	protected HKXData getContents(long arrayStart, int position) throws IOException, InvalidPositionException {
		long contentsPos = arrayStart + position * contentSize;
		HKXObject data = reader.createHKXObject("", contentsPos, descriptor);
		return data;
	}
}
