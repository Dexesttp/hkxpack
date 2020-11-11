package com.dexesttp.hkxpack.hkxwriter.object;

import java.util.List;

import com.dexesttp.hkxpack.data.members.HKXMember;
import com.dexesttp.hkxpack.data.members.HKXPointerMember;
import com.dexesttp.hkxpack.hkxwriter.object.callbacks.HKXMemberCallback;
import com.dexesttp.hkxpack.hkxwriter.utils.PointerObject;

/**
 * Handles a {@link HKXPointerMember} for writing to a HKX File
 */
public class HKXPointerMemberHandler implements HKXMemberHandler {
	private final transient long offset;
	private final transient List<PointerObject> data2;

	/**
	 * Creates a {@link HKXPointerMemberHandler}.
	 * 
	 * @param offset    the offset of the mmember in the class.
	 * @param data2List the list of external pointers to put the pointer resolver
	 *                  into.
	 */
	HKXPointerMemberHandler(final long offset, final List<PointerObject> data2List) {
		this.offset = offset;
		this.data2 = data2List;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public HKXMemberCallback write(final HKXMember member, final long currentPos) {
		HKXPointerMember ptrMember = (HKXPointerMember) member;
		PointerObject ptrObject = new PointerObject();
		ptrObject.from = currentPos + offset;
		ptrObject.to = ptrMember.get();
		data2.add(ptrObject);
		return (callbacks, position) -> {
			return 0;
		};
	}

}
