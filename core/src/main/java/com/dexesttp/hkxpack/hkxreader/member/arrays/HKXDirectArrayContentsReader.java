package com.dexesttp.hkxpack.hkxreader.member.arrays;

import java.nio.ByteBuffer;

import com.dexesttp.hkxpack.data.HKXData;
import com.dexesttp.hkxpack.data.members.HKXMember;
import com.dexesttp.hkxpack.descriptor.enums.HKXType;
import com.dexesttp.hkxpack.hkx.exceptions.InvalidPositionException;
import com.dexesttp.hkxpack.hkx.types.MemberDataResolver;
import com.dexesttp.hkxpack.hkx.types.MemberSizeResolver;
import com.dexesttp.hkxpack.hkxreader.HKXReaderConnector;

class HKXDirectArrayContentsReader implements HKXArrayContentsReader {
	private final HKXReaderConnector connector;
	private final HKXType contentType;

	HKXDirectArrayContentsReader(HKXReaderConnector connector, HKXType contentType) {
		this.connector = connector;
		this.contentType = contentType;
	}

	@Override
	public HKXData getContents(long arrayStart, int position) throws InvalidPositionException {
		final int contentSize = (int) MemberSizeResolver.getSize(contentType);
		byte[] b = new byte[contentSize];
		ByteBuffer file = connector.data.setup(arrayStart + position * contentSize);
		file.get(b);
		HKXMember data = MemberDataResolver.getMember("", contentType, b);
		return data;
	}
}
