package com.dexesttp.hkxpack.hkxwriter.object.callbacks;

import java.io.IOException;
import java.util.List;

import com.dexesttp.hkxpack.data.members.HKXArrayMember;
import com.dexesttp.hkxpack.hkx.data.DataInternal;
import com.dexesttp.hkxpack.hkx.types.MemberSizeResolver;
import com.dexesttp.hkxpack.hkxwriter.object.HKXArrayPointerMemberHandler;

public class HKXPointerArrayMemberCallback implements HKXMemberCallback {
	private final List<DataInternal> data1;
	private final DataInternal arrData;
	private final HKXArrayMember arrMember;
	private final List<HKXArrayPointerMemberHandler> apmhList;
	
	public HKXPointerArrayMemberCallback(DataInternal arrData, List<DataInternal> data1, HKXArrayMember arrMember,
			List<HKXArrayPointerMemberHandler> apmhList) {
		this.data1 = data1;
		this.arrData = arrData;
		this.arrMember = arrMember;
		this.apmhList = apmhList;
	}

	@Override
	public long process(List<HKXMemberCallback> memberCallbacks, long position) throws IOException {
		arrData.to = position;
		data1.add(arrData);
		long newPos = position;
		for(HKXArrayPointerMemberHandler apmh : apmhList) {
			long objectSize = MemberSizeResolver.getSize(arrMember.getSubType());
			apmh.resolve(newPos);
			newPos += objectSize;
		}
		return newPos - position;
	}

}
