package com.dexesttp.hkxpack.hkxwriter.object;

import java.io.IOException;

import com.dexesttp.hkxpack.data.members.HKXMember;

public interface HKXMemberHandler {
	public HKXMemberCallback write(HKXMember member, long currentPos) throws IOException;
}
