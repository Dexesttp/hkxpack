package com.dexesttp.hkxpack.hkxwriter.object;

import java.io.IOException;
import java.io.RandomAccessFile;

import com.dexesttp.hkxpack.data.members.HKXMember;
import com.dexesttp.hkxpack.hkx.types.MemberDataResolver;

public class HKXDirectMemberHandler implements HKXMemberHandler {
	private final RandomAccessFile outFile;
	private final long memberOffset;

	HKXDirectMemberHandler(RandomAccessFile outFile, long memberOffset) {
		this.outFile = outFile;
		this.memberOffset = memberOffset;
	}

	@Override
	public HKXMemberCallback write(HKXMember member, long currentPos) throws IOException {
		byte[] value = MemberDataResolver.fromMember(member);
		outFile.seek(currentPos + memberOffset);
		outFile.write(value);
		return (memberCallbacks, position) -> { return 0; };
	}
}
