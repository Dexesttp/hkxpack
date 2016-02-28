package com.dexesttp.hkxpack.hkxwriter.object;

import java.io.IOException;
import java.util.List;

import com.dexesttp.hkxpack.data.HKXObject;
import com.dexesttp.hkxpack.data.members.HKXMember;
import com.dexesttp.hkxpack.hkx.data.DataInternal;

public class HKXObjectMemberHandler implements HKXMemberHandler {
	private final HKXMemberHandlerFactory memberHandlerFactory;
	private final List<DataInternal> data1;
	private final long offset;

	public HKXObjectMemberHandler(long offset, HKXMemberHandlerFactory memberHandlerFactory, List<DataInternal> data1) {
		this.offset = offset;
		this.memberHandlerFactory = memberHandlerFactory;
		this.data1 = data1;
	}

	@Override
	public HKXMemberCallback write(HKXMember member, final long currentPos) throws IOException {
		final HKXObject object = (HKXObject) member;
		final DataInternal posDescriptor = new DataInternal();
		posDescriptor.from = currentPos + offset;
		data1.add(posDescriptor);
		return (memberCallbacks, position) ->{
				HKXInternalObjectHandler internalObjectHandler = new HKXInternalObjectHandler(memberHandlerFactory, memberCallbacks);
				posDescriptor.to = position;
				return internalObjectHandler.write(object, position);
		};
	}

}
