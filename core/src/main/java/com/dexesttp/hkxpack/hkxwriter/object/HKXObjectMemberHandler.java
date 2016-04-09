package com.dexesttp.hkxpack.hkxwriter.object;

import java.util.List;

import com.dexesttp.hkxpack.data.HKXObject;
import com.dexesttp.hkxpack.data.members.HKXMember;
import com.dexesttp.hkxpack.hkxwriter.object.callbacks.HKXMemberCallback;

/**
 * Handles a {@link HKXObject} as a member of a class to write to a HKX File.
 */
public class HKXObjectMemberHandler implements HKXMemberHandler {
	private final transient HKXMemberHandlerFactory memberHandlerFactory;
	private final transient List<HKXMemberCallback> memberCallbacks;
	private final transient long offset;

	/**
	 * Creates a {@link HKXObjectMemberHandler}.
	 * @param offset the offset of the {@link HKXObject} member in the class.
	 * @param memberHandlerFactory the {@link HKXMemberHandlerFactory} to use while resolving the object.
	 * @param memberCallbacks the list of callbacks to add this object's members to.
	 */
	public HKXObjectMemberHandler(final long offset, final HKXMemberHandlerFactory memberHandlerFactory,
			final List<HKXMemberCallback> memberCallbacks) {
		this.offset = offset;
		this.memberHandlerFactory = memberHandlerFactory;
		this.memberCallbacks = memberCallbacks;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public HKXMemberCallback write(final HKXMember member, final long currentPos) {
		final HKXObject object = (HKXObject) member;
		HKXInternalObjectHandler internalObjectHandler = new HKXInternalObjectHandler(memberHandlerFactory, memberCallbacks);
		internalObjectHandler.write(object, currentPos + offset);
		return (memberCallbacks, position) ->{ return 0; };
	}

}
