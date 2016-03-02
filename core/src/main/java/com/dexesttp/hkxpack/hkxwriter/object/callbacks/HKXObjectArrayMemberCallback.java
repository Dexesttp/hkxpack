package com.dexesttp.hkxpack.hkxwriter.object.callbacks;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.dexesttp.hkxpack.data.HKXData;
import com.dexesttp.hkxpack.data.HKXObject;
import com.dexesttp.hkxpack.data.members.HKXArrayMember;
import com.dexesttp.hkxpack.hkx.data.DataInternal;
import com.dexesttp.hkxpack.hkx.types.MemberSizeResolver;
import com.dexesttp.hkxpack.hkxwriter.object.HKXMemberHandler;
import com.dexesttp.hkxpack.hkxwriter.object.HKXMemberHandlerFactory;
import com.dexesttp.hkxpack.hkxwriter.object.HKXObjectMemberHandler;

public class HKXObjectArrayMemberCallback implements HKXMemberCallback {
	private final List<DataInternal> data1;
	private final DataInternal arrData;
	private final HKXArrayMember arrMember;
	private HKXMemberHandlerFactory memberHandlerFactory;

	public HKXObjectArrayMemberCallback(List<DataInternal> data1, DataInternal arrData, HKXArrayMember arrMember, HKXMemberHandlerFactory memberHandlerFactory) {
		this.data1 = data1;
		this.arrData = arrData;
		this.arrMember = arrMember;
		this.memberHandlerFactory = memberHandlerFactory;
	}
	
	@Override
	public long process(List<HKXMemberCallback> memberCallbacks, long position) throws IOException {
		arrData.to = position;
		data1.add(arrData);
		long newPos = position;
		List<HKXMemberCallback> internalCallbacks = new ArrayList<>();
		for(HKXData data : arrMember.contents()) {
			if(data instanceof HKXObject) {
				HKXObject internalObject = (HKXObject) data;
				long objectSize = MemberSizeResolver.getSize(internalObject);
				HKXMemberHandler memberHandler = new HKXObjectMemberHandler(0, memberHandlerFactory.clone(internalCallbacks), internalCallbacks);
				internalCallbacks.add(memberHandler.write(internalObject, newPos));
				newPos += objectSize;
			}
		}
		memberCallbacks.addAll(0, internalCallbacks);
		return newPos - position;
	}

}
