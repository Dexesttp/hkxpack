package com.dexesttp.hkxpack.hkxreader.member;

import java.io.IOException;
import java.io.RandomAccessFile;

import com.dexesttp.hkxpack.data.HKXData;
import com.dexesttp.hkxpack.data.members.HKXMember;
import com.dexesttp.hkxpack.descriptor.enums.HKXType;
import com.dexesttp.hkxpack.hkx.exceptions.InvalidPositionException;
import com.dexesttp.hkxpack.hkx.types.MemberDataResolver;
import com.dexesttp.hkxpack.hkx.types.MemberSizeResolver;
import com.dexesttp.hkxpack.hkxreader.HKXReaderConnector;

class HKXDirectArrayMemberReader extends HKXArrayMemberReader {

	HKXDirectArrayMemberReader(HKXReaderConnector connector, String name, HKXType contentType, long offset) {
		super(connector, name, contentType, offset);
	}

	@Override
	protected HKXData getContents(long arrayStart, int position) throws IOException, InvalidPositionException {
		final int contentSize = (int) MemberSizeResolver.getSize(subtype);
		byte[] b = new byte[contentSize];
		RandomAccessFile file = connector.data.setup(arrayStart + position * contentSize);
		file.read(b);
		HKXMember data = MemberDataResolver.getMember(name, subtype, b);
		return data;
	}
}
