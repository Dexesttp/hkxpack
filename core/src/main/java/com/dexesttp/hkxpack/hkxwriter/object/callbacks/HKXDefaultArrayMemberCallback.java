package com.dexesttp.hkxpack.hkxwriter.object.callbacks;

import java.util.List;

import com.dexesttp.hkxpack.data.HKXData;
import com.dexesttp.hkxpack.data.members.HKXArrayMember;
import com.dexesttp.hkxpack.data.members.HKXMember;
import com.dexesttp.hkxpack.hkx.HKXUtils;
import com.dexesttp.hkxpack.hkx.types.MemberSizeResolver;
import com.dexesttp.hkxpack.hkxwriter.object.HKXMemberHandler;
import com.dexesttp.hkxpack.hkxwriter.object.HKXMemberHandlerFactory;

/**
 * Handles callbacks for most members.
 */
public class HKXDefaultArrayMemberCallback implements HKXArrayMemberCallback {
	private final transient HKXArrayMember arrMember;
	private final transient HKXMemberHandlerFactory memberHandlerFactory;

	/**
	 * Creates a {@link HKXDefaultArrayMemberCallback}
	 * @param arrMember the {@link HKXArrayMember} this callback handles
	 * @param memberHandlerFactory the {@link HKXMemberHandlerFactory} to use while creating the array component's handlers.
	 */
	public HKXDefaultArrayMemberCallback(final HKXArrayMember arrMember, final HKXMemberHandlerFactory memberHandlerFactory) {
		this.arrMember = arrMember;
		this.memberHandlerFactory = memberHandlerFactory;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public long process(final List<HKXMemberCallback> memberCallbacks, final long position) {
		long newPos = position;
		long memberSize = MemberSizeResolver.getSize(arrMember.getSubType());
		for(HKXData data : arrMember.getContentsList()) {
			if(data instanceof HKXMember) {
				HKXMember internalMember = (HKXMember) data;
				HKXMemberHandler memberHandler = memberHandlerFactory.create(internalMember.getType(), 0);
				memberCallbacks.add(memberHandler.write(internalMember, newPos));
				newPos += memberSize;
			}
		}
		return HKXUtils.snapLine(newPos) - position;
	}

}
