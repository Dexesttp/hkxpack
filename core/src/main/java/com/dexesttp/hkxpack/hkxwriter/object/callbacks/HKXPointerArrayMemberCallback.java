package com.dexesttp.hkxpack.hkxwriter.object.callbacks;

import java.util.List;

import com.dexesttp.hkxpack.data.members.HKXArrayMember;
import com.dexesttp.hkxpack.data.members.HKXPointerMember;
import com.dexesttp.hkxpack.hkx.HKXUtils;
import com.dexesttp.hkxpack.hkx.types.MemberSizeResolver;
import com.dexesttp.hkxpack.hkxwriter.object.array.HKXArrayPointerMemberHandler;

/**
 * Handles callbacks for {@link HKXPointerMember}'s array components.
 */
public class HKXPointerArrayMemberCallback implements HKXArrayMemberCallback {
	private final transient HKXArrayMember arrMember;
	private final transient List<HKXArrayPointerMemberHandler> apmhList;

	/**
	 * Creates a {@link HKXPointerArrayMemberCallback}
	 * 
	 * @param arrMember the {@link HKXArrayMember} the callback is for
	 * @param apmhList  the {@link HKXArrayPointerMemberHandler} to base the
	 *                  callback on.
	 */
	public HKXPointerArrayMemberCallback(final HKXArrayMember arrMember,
			final List<HKXArrayPointerMemberHandler> apmhList) {
		this.arrMember = arrMember;
		this.apmhList = apmhList;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public long process(final List<HKXMemberCallback> memberCallbacks, final long position) {
		long newPos = position;
		for (HKXArrayPointerMemberHandler apmh : apmhList) {
			long objectSize = MemberSizeResolver.getSize(arrMember.getSubType());
			apmh.resolve(newPos);
			newPos += objectSize;
		}
		return HKXUtils.snapLine(newPos) - position;
	}

}
