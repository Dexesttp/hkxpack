package com.dexesttp.hkxpack.hkxreader.member;

import java.io.IOException;
import java.io.RandomAccessFile;

import com.dexesttp.hkxpack.data.HKXData;
import com.dexesttp.hkxpack.data.members.HKXArrayMember;
import com.dexesttp.hkxpack.data.members.HKXMember;
import com.dexesttp.hkxpack.descriptor.enums.HKXType;
import com.dexesttp.hkxpack.hkx.data.Data1Interface;
import com.dexesttp.hkxpack.hkx.data.DataInternal;
import com.dexesttp.hkxpack.hkx.exceptions.InvalidPositionException;
import com.dexesttp.hkxpack.hkx.types.MemberSizeResolver;
import com.dexesttp.hkxpack.hkxreader.HKXReaderConnector;
import com.dexesttp.hkxpack.resources.ByteUtils;

public abstract class HKXArrayMemberReader implements HKXMemberReader {
	protected final HKXReaderConnector connector;
	protected final String name;
	protected final HKXType subtype;
	protected final long memberOffset;

	HKXArrayMemberReader(HKXReaderConnector connector, String name, HKXType subtype, long offset) {
		this.connector = connector;
		this.name = name;
		this.subtype = subtype;
		this.memberOffset = offset;
	}

	@Override
	public HKXMember read(long classOffset) throws IOException, InvalidPositionException {
		final int memberSize = (int) MemberSizeResolver.getSize(HKXType.TYPE_ARRAY);
		RandomAccessFile file = connector.data.setup(classOffset + memberOffset);
		byte[] b = new byte[memberSize];
		file.read(b);
		HKXArrayMember result = new HKXArrayMember(name, HKXType.TYPE_ARRAY, subtype);
		int arrSize = getSizeComponent(b);
		if(arrSize > 0) {
			Data1Interface data1 = connector.data1;
			DataInternal arrValue = data1.readNext();
			assert(arrValue.from == classOffset + memberOffset);
			for(int i = 0; i < arrSize; i++ ) {
				HKXData data = getContents(arrValue.to, i);
				result.add(data);
			}
		}
		return result;
	}

	protected abstract HKXData getContents(long arrayStart, int position) throws IOException, InvalidPositionException;

	private int getSizeComponent(byte[] b) {
		byte[] newB = new byte[]{b[8], b[9], b[10], b[11]};
		return ByteUtils.getInt(newB);
	}
}

