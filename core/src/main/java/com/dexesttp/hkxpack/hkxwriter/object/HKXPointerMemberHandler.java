package com.dexesttp.hkxpack.hkxwriter.object;

import java.io.IOException;
import java.util.List;

import com.dexesttp.hkxpack.data.members.HKXMember;
import com.dexesttp.hkxpack.data.members.HKXPointerMember;
import com.dexesttp.hkxpack.hkxwriter.utils.PointerObject;

public class HKXPointerMemberHandler implements HKXMemberHandler {
	private final long offset;
	private final List<PointerObject> data2;

	public HKXPointerMemberHandler(long offset, List<PointerObject> data2List) {
		this.offset = offset;
		this.data2 = data2List;
	}

	@Override
	public HKXMemberCallback write(HKXMember member, long currentPos) throws IOException {
		HKXPointerMember ptrMember = (HKXPointerMember) member;
		PointerObject ptrObject = new PointerObject();
		ptrObject.from = currentPos + offset;
		ptrObject.to = ptrMember.get();
		data2.add(ptrObject);
		return (callbacks, position) -> { return 0; };
	}

}
