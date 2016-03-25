package com.dexesttp.hkxpack.hkxwriter.object;

import java.nio.ByteBuffer;

import com.dexesttp.hkxpack.data.members.HKXEnumMember;
import com.dexesttp.hkxpack.data.members.HKXMember;
import com.dexesttp.hkxpack.descriptor.HKXEnumResolver;
import com.dexesttp.hkxpack.hkx.types.MemberSizeResolver;
import com.dexesttp.hkxpack.hkxwriter.object.callbacks.HKXMemberCallback;
import com.dexesttp.hkxpack.resources.ByteUtils;

public class HKXEnumMemberHandler implements HKXMemberHandler {
	private final ByteBuffer outFile;
	private final long offset;
	private final HKXEnumResolver enumResolver;

	public HKXEnumMemberHandler(ByteBuffer outFile, long offset, HKXEnumResolver enumResolver) {
		this.outFile = outFile;
		this.offset = offset;
		this.enumResolver = enumResolver;
	}

	@Override
	public HKXMemberCallback write(HKXMember member, long currentPos) {
		HKXEnumMember enumMember = (HKXEnumMember) member;
		if(!enumMember.getEnumName().isEmpty()) {
			outFile.position((int) (currentPos + offset));
			long enumVal = enumResolver.resolve(enumMember.getEnumName(), enumMember.get());
			byte[] res = ByteUtils.fromLong(enumVal, (int) MemberSizeResolver.getSize(enumMember.getSubtype()));
			outFile.put(res);
		}
		return (memberCallbacks, position) -> { return 0; };
	}

}
