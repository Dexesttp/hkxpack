package com.dexesttp.hkxpack.hkxwriter.object;

import java.io.IOException;
import java.io.RandomAccessFile;

import com.dexesttp.hkxpack.data.members.HKXDirectMember;
import com.dexesttp.hkxpack.data.members.HKXMember;
import com.dexesttp.hkxpack.descriptor.HKXEnumResolver;
import com.dexesttp.hkxpack.resources.ByteUtils;

public class HKXEnumMemberHandler implements HKXMemberHandler {
	private final RandomAccessFile outFile;
	private final long offset;
	private final HKXEnumResolver enumResolver;
	private final String etype;

	public HKXEnumMemberHandler(RandomAccessFile outFile, long offset, HKXEnumResolver enumResolver, String etype) {
		this.outFile = outFile;
		this.offset = offset;
		this.enumResolver = enumResolver;
		this.etype = etype;
	}

	@Override
	public HKXMemberCallback write(HKXMember member, long currentPos) throws IOException {
		@SuppressWarnings("unchecked")
		HKXDirectMember<String> enumMember = (HKXDirectMember<String>) member;
		if(!etype.isEmpty()) {
			outFile.seek(currentPos + offset);
			long enumVal = enumResolver.resolve(etype, enumMember.get());
			byte[] res = ByteUtils.fromLong(enumVal, 4);
			outFile.write(res);
		}
		return (memberCallbacks, position) -> { return 0; };
	}

}
