package com.dexesttp.hkxpack.hkxwriter.object.callbacks;

import java.util.ArrayList;
import java.util.List;

import com.dexesttp.hkxpack.data.HKXData;
import com.dexesttp.hkxpack.data.HKXObject;
import com.dexesttp.hkxpack.data.members.HKXArrayMember;
import com.dexesttp.hkxpack.hkx.HKXUtils;
import com.dexesttp.hkxpack.hkx.types.ObjectSizeResolver;
import com.dexesttp.hkxpack.hkxwriter.object.HKXMemberHandler;
import com.dexesttp.hkxpack.hkxwriter.object.HKXMemberHandlerFactory;
import com.dexesttp.hkxpack.hkxwriter.object.HKXObjectMemberHandler;

public class HKXObjectArrayMemberCallback implements HKXArrayMemberCallback {
	private final HKXArrayMember arrMember;
	private HKXMemberHandlerFactory memberHandlerFactory;

	public HKXObjectArrayMemberCallback(HKXArrayMember arrMember, HKXMemberHandlerFactory memberHandlerFactory) {
		this.arrMember = arrMember;
		this.memberHandlerFactory = memberHandlerFactory;
	}
	
	@Override
	public long process(List<HKXMemberCallback> memberCallbacks, long position) {
		long newPos = position;
		List<HKXMemberCallback> internalCallbacks = new ArrayList<>();
		for(HKXData data : arrMember.contents()) {
			if(data instanceof HKXObject) {
				HKXObject internalObject = (HKXObject) data;
				long objectSize = ObjectSizeResolver.getSize(internalObject);
				HKXMemberHandler memberHandler = new HKXObjectMemberHandler(0, memberHandlerFactory.clone(internalCallbacks), internalCallbacks);
				internalCallbacks.add(memberHandler.write(internalObject, newPos));
				newPos += objectSize;
			}
		}
		internalCallbacks.add((callbacks, newPosition) -> {return HKXUtils.snapLine(newPosition) - newPosition;});
		memberCallbacks.addAll(0, internalCallbacks);
		return newPos - position;
	}

}
