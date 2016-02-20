package com.dexesttp.hkxpack.hkxreader.member;

import java.io.IOException;
import java.io.RandomAccessFile;

import com.dexesttp.hkxpack.data.members.HKXDirectMember;
import com.dexesttp.hkxpack.data.members.HKXMember;
import com.dexesttp.hkxpack.descriptor.HKXEnumResolver;
import com.dexesttp.hkxpack.descriptor.enums.HKXType;
import com.dexesttp.hkxpack.hkx.exceptions.InvalidPositionException;
import com.dexesttp.hkxpack.hkx.structs.MemberTypeResolver;
import com.dexesttp.hkxpack.hkxreader.HKXReaderConnector;
import com.dexesttp.hkxpack.resources.ByteUtils;
import com.dexesttp.hkxpack.resources.LoggerUtil;

public class HKXEnumMemberReader implements HKXMemberReader {
	private final HKXReaderConnector connector;
	private final HKXEnumResolver enumResolver;
	private final String name;
	private final HKXType vtype;
	private final String etype;
	private final long memberOffset;

	HKXEnumMemberReader(HKXReaderConnector connector, HKXEnumResolver enumResolver, String name, HKXType vtype, String target, long offset) {
		this.connector = connector;
		this.enumResolver = enumResolver;
		this.name = name;
		this.vtype = vtype;
		this.etype = target;
		this.memberOffset = offset;
	}

	@Override
	public HKXMember read(long classOffset) throws IOException, InvalidPositionException {
		final int memberSize = (int) MemberTypeResolver.getSize(HKXType.TYPE_ENUM);
		RandomAccessFile file = connector.data.setup(classOffset + memberOffset);
		byte[] b = new byte[memberSize];
		file.read(b);
		int contents = ByteUtils.getInt(b);
		HKXDirectMember<String> result = new HKXDirectMember<>(name, vtype);
		try {
			result.set(enumResolver.resolve(etype, contents));
		} catch(NullPointerException e) {
			LoggerUtil.error("HKREAD", "ENUM", "", "Couldn't read enum : " + etype);
			result.set("" + contents);
		}
		return result;
	}
}
