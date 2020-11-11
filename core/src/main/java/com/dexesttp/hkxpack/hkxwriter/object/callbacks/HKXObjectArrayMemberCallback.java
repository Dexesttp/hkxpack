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

/**
 * the base {@link HKXObject} array member callback.
 */
public class HKXObjectArrayMemberCallback implements HKXArrayMemberCallback {
	private final transient HKXArrayMember arrMember;
	private final transient HKXMemberHandlerFactory memberHandlerFactory;

	/**
	 * Creates a {@link HKXObjectArrayMemberCallback}
	 * 
	 * @param arrMember            the {@link HKXArrayMember} to base the callbak
	 *                             on.
	 * @param memberHandlerFactory the {@link HKXMemberHandlerFactory} to generate
	 *                             the obejct's member handlers from.
	 */
	public HKXObjectArrayMemberCallback(final HKXArrayMember arrMember,
			final HKXMemberHandlerFactory memberHandlerFactory) {
		this.arrMember = arrMember;
		this.memberHandlerFactory = memberHandlerFactory;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public long process(final List<HKXMemberCallback> memberCallbacks, final long position) {
		long newPos = position;
		List<HKXMemberCallback> internalCallbacks = new ArrayList<>();
		for (HKXData data : arrMember.getContentsList()) {
			if (data instanceof HKXObject) {
				HKXObject internalObject = (HKXObject) data;
				long objectSize = ObjectSizeResolver.getSize(internalObject);
				HKXMemberHandler memberHandler = createObjectHandler(internalCallbacks);
				internalCallbacks.add(memberHandler.write(internalObject, newPos));
				newPos += objectSize;
			}
		}
		internalCallbacks.add((callbacks, newPosition) -> {
			return HKXUtils.snapLine(newPosition) - newPosition;
		});
		memberCallbacks.addAll(0, internalCallbacks);
		return newPos - position;
	}

	private HKXMemberHandler createObjectHandler(final List<HKXMemberCallback> internalCallbacks) {
		return new HKXObjectMemberHandler(0, memberHandlerFactory.clone(internalCallbacks), internalCallbacks);
	}

}
