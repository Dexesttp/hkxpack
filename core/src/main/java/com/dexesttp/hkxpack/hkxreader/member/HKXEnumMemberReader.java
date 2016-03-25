package com.dexesttp.hkxpack.hkxreader.member;

import java.io.IOException;
import java.nio.ByteBuffer;

import com.dexesttp.hkxpack.data.members.HKXEnumMember;
import com.dexesttp.hkxpack.data.members.HKXMember;
import com.dexesttp.hkxpack.descriptor.HKXEnumResolver;
import com.dexesttp.hkxpack.descriptor.enums.HKXType;
import com.dexesttp.hkxpack.hkx.exceptions.InvalidPositionException;
import com.dexesttp.hkxpack.hkx.types.MemberSizeResolver;
import com.dexesttp.hkxpack.hkxreader.HKXReaderConnector;
import com.dexesttp.hkxpack.resources.ByteUtils;

public class HKXEnumMemberReader implements HKXMemberReader {
	private final HKXReaderConnector connector;
	private final HKXEnumResolver enumResolver;
	private final String name;
	private final HKXType vtype;
	private final HKXType vsubtype;
	private final String etype;
	private final long memberOffset;

	HKXEnumMemberReader(HKXReaderConnector connector, HKXEnumResolver enumResolver, String name, HKXType vtype, HKXType vsubtype, String target, long offset) {
		this.connector = connector;
		this.enumResolver = enumResolver;
		this.name = name;
		this.vtype = vtype;
		this.vsubtype = vsubtype;
		this.etype = target;
		this.memberOffset = offset;
	}

	@Override
	public HKXMember read(long classOffset) throws IOException, InvalidPositionException {
		final int memberSize = (int) MemberSizeResolver.getSize(vsubtype);
		ByteBuffer file = connector.data.setup(classOffset + memberOffset);
		byte[] b = new byte[memberSize];
		file.get(b);
		int contents = ByteUtils.getInt(b);
		HKXEnumMember result = new HKXEnumMember(name, vtype, vsubtype, etype);
		result.set(enumResolver.resolve(etype, contents));
		return result;
	}
}
