package com.dexesttp.hkxpack.hkxwriter.object;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.dexesttp.hkxpack.data.HKXObject;
import com.dexesttp.hkxpack.data.members.HKXMember;
import com.dexesttp.hkxpack.descriptor.members.HKXMemberTemplate;
import com.dexesttp.hkxpack.hkx.types.MemberSizeResolver;

/**
 * Handles writing a {@link HKXObject}'s contents into a {@link File}.
 */
public class HKXInternalObjectHandler {
	private final HKXMemberHandlerFactory memberHandlerFactory;
	private List<HKXMemberCallback> memberCallbacks;

	/**
	 * Creates a handler to write an internal object to a {@link File}.
	 * @param factory the {@link HKXMemberHandlerFactory} to use while solving the {@link HKXObject}'s members.
	 * @param memberCallbacks the list of {@link HKXMemberCallback} to add callbacks into.
	 */
	public HKXInternalObjectHandler(HKXMemberHandlerFactory factory, List<HKXMemberCallback> memberCallbacks) {
		this.memberHandlerFactory = factory;
		this.memberCallbacks = memberCallbacks;
	}
	
	public long write(HKXMember objectAsMember, long currentPos) throws IOException {
		HKXObject object = (HKXObject) objectAsMember;
		// Prepare the member handlers, and fill the raw structure.
		List<HKXMember> members = object.members();
		List<HKXMemberTemplate> memberTemplates = object.getDescriptor().getMemberTemplates();
		for(int i = 0; i < memberTemplates.size(); i++) {
			HKXMember member = members.get(i);
			HKXMemberTemplate memberTemplate = memberTemplates.get(i);
			HKXMemberHandler memberHandler = memberHandlerFactory.create(memberTemplate.vtype, memberTemplate.offset, memberTemplate.target);
			memberCallbacks.add(memberHandler.write(member, currentPos));
		}
		return currentPos + MemberSizeResolver.getSize(object.getDescriptor());
	}

}
