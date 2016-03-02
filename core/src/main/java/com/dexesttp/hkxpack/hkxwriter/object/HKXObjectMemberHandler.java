package com.dexesttp.hkxpack.hkxwriter.object;

import java.io.IOException;
import java.util.List;

import com.dexesttp.hkxpack.data.HKXObject;
import com.dexesttp.hkxpack.data.members.HKXMember;
import com.dexesttp.hkxpack.hkxwriter.object.callbacks.HKXMemberCallback;

public class HKXObjectMemberHandler implements HKXMemberHandler {
	private final HKXMemberHandlerFactory memberHandlerFactory;
	private final List<HKXMemberCallback> memberCallbacks;
	private final long offset;

	public HKXObjectMemberHandler(long offset, HKXMemberHandlerFactory memberHandlerFactory, List<HKXMemberCallback> memberCallbacks) {
		this.offset = offset;
		this.memberHandlerFactory = memberHandlerFactory;
		this.memberCallbacks = memberCallbacks;
	}

	@Override
	public HKXMemberCallback write(HKXMember member, final long currentPos) throws IOException {
		final HKXObject object = (HKXObject) member;
		HKXInternalObjectHandler internalObjectHandler = new HKXInternalObjectHandler(memberHandlerFactory, memberCallbacks);
		internalObjectHandler.write(object, currentPos + offset);
		return (memberCallbacks, position) ->{ return 0; };
	}

}
