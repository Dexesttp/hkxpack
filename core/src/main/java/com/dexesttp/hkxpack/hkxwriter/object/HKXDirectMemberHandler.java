package com.dexesttp.hkxpack.hkxwriter.object;

import java.nio.ByteBuffer;

import com.dexesttp.hkxpack.data.members.HKXMember;
import com.dexesttp.hkxpack.hkx.types.MemberDataResolver;
import com.dexesttp.hkxpack.hkxwriter.object.callbacks.HKXMemberCallback;

public class HKXDirectMemberHandler implements HKXMemberHandler {
	private final ByteBuffer outFile;
	private final long memberOffset;

	HKXDirectMemberHandler(ByteBuffer outFile, long memberOffset) {
		this.outFile = outFile;
		this.memberOffset = memberOffset;
	}

	@Override
	public HKXMemberCallback write(HKXMember member, long currentPos) {
		byte[] value = MemberDataResolver.fromMember(member);
		outFile.position((int) (currentPos + memberOffset));
		outFile.put(value);
		return (memberCallbacks, position) -> { return 0; };
	}
}
