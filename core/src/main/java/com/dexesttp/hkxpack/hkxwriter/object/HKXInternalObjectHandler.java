package com.dexesttp.hkxpack.hkxwriter.object;

import java.io.File;
import java.util.List;

import com.dexesttp.hkxpack.data.HKXObject;
import com.dexesttp.hkxpack.data.members.HKXMember;
import com.dexesttp.hkxpack.descriptor.members.HKXMemberTemplate;
import com.dexesttp.hkxpack.hkx.types.MemberSizeResolver;

/**
 * Handles writing a {@link HKXObject}'s contents into a {@link File}.
 */
public class HKXInternalObjectHandler implements HKXMemberHandler {
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
	
	@Override
	public HKXMemberCallback write(HKXMember objectAsMember, long currentPos) {
		HKXObject object = (HKXObject) objectAsMember;
		// Prepare the member handlers, and fill the raw structure.
		List<HKXMember> members = object.members();
		List<HKXMemberTemplate> memberTemplates = object.getDescriptor().getMemberTemplates();
		for(int i = 0; i < memberTemplates.size(); i++) {
			HKXMember member = members.get(i);
			HKXMemberTemplate memberTemplate = memberTemplates.get(i);
			HKXMemberHandler memberHandler = memberHandlerFactory.create(memberTemplate);
			memberCallbacks.add(memberHandler.write(member, currentPos));
		}
		final long positionToReturn = currentPos + MemberSizeResolver.getSize(object.getDescriptor());
		return new HKXMemberCallback() {
			@Override
			public long process(List<HKXMemberCallback> memberCallbacks, long position) {
				return positionToReturn;
			}
		};
	}

}
