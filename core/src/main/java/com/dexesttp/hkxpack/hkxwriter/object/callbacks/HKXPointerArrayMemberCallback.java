package com.dexesttp.hkxpack.hkxwriter.object.callbacks;

import java.util.List;

import com.dexesttp.hkxpack.data.members.HKXArrayMember;
import com.dexesttp.hkxpack.hkx.HKXUtils;
import com.dexesttp.hkxpack.hkx.types.MemberSizeResolver;
import com.dexesttp.hkxpack.hkxwriter.object.array.HKXArrayPointerMemberHandler;

public class HKXPointerArrayMemberCallback implements HKXArrayMemberCallback {
	private final HKXArrayMember arrMember;
	private final List<HKXArrayPointerMemberHandler> apmhList;
	
	public HKXPointerArrayMemberCallback(HKXArrayMember arrMember, List<HKXArrayPointerMemberHandler> apmhList) {
		this.arrMember = arrMember;
		this.apmhList = apmhList;
	}

	@Override
	public long process(List<HKXMemberCallback> memberCallbacks, long position) {
		long newPos = position;
		for(HKXArrayPointerMemberHandler apmh : apmhList) {
			long objectSize = MemberSizeResolver.getSize(arrMember.getSubType());
			apmh.resolve(newPos);
			newPos += objectSize;
		}
		return HKXUtils.snapLine(newPos) - position;
	}

}
