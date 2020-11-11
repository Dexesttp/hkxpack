package com.dexesttp.hkxpack.hkx.types.object;

import java.util.List;

import com.dexesttp.hkxpack.data.HKXObject;
import com.dexesttp.hkxpack.descriptor.HKXDescriptor;
import com.dexesttp.hkxpack.descriptor.HKXDescriptorFactory;
import com.dexesttp.hkxpack.descriptor.enums.HKXType;
import com.dexesttp.hkxpack.descriptor.enums.HKXTypeFamily;
import com.dexesttp.hkxpack.descriptor.exceptions.ClassFileReadException;
import com.dexesttp.hkxpack.descriptor.members.HKXMemberTemplate;
import com.dexesttp.hkxpack.hkx.HKXUtils;
import com.dexesttp.hkxpack.hkx.types.MemberSizeResolver;
import com.dexesttp.hkxpack.hkx.types.ObjectSizeResolver;

/**
 * Calculates an object size
 */
public final class ObjectSize {
	private ObjectSize() {
		// NO OP
	}

	/**
	 * @see ObjectSizeResolver#getSize(HKXDescriptor, HKXDescriptorFactory)
	 */
	public static long getSize(final HKXDescriptor descriptor, final HKXDescriptorFactory descriptorFactory)
			throws ClassFileReadException {
		List<HKXMemberTemplate> templates = descriptor.getMemberTemplates();
		if (templates.isEmpty()) {
			return 0;
		}
		long bestSnap = ObjectSnap.getSnap(descriptor, descriptorFactory);
		HKXMemberTemplate lastTemplate = templates.get(templates.size() - 1);
		if (lastTemplate.vtype.getFamily() == HKXTypeFamily.ENUM) {
			return HKXUtils.snapSize(lastTemplate.offset + MemberSizeResolver.getSize(lastTemplate.vsubtype), bestSnap);
		}
		if (lastTemplate.vtype != HKXType.TYPE_STRUCT) {
			return HKXUtils.snapSize(lastTemplate.offset + MemberSizeResolver.getSize(lastTemplate.vtype), bestSnap);
		}
		HKXDescriptor internalDescriptor = descriptorFactory.get(lastTemplate.target);
		return HKXUtils.snapSize(lastTemplate.offset + getSize(internalDescriptor, descriptorFactory), bestSnap);
	}

	/**
	 * @see ObjectSizeResolver#getSize(HKXObject)
	 */
	public static long getSize(final HKXObject object) {
		List<HKXMemberTemplate> templates = object.getDescriptor().getMemberTemplates();
		if (templates.isEmpty()) {
			return 0;
		}
		long bestSnap = ObjectSnap.getSnap(object);
		HKXMemberTemplate lastTemplate = templates.get(templates.size() - 1);
		if (lastTemplate.vtype.getFamily() == HKXTypeFamily.ENUM) {
			return HKXUtils.snapSize(lastTemplate.offset + MemberSizeResolver.getSize(lastTemplate.vsubtype), bestSnap);
		}
		if (lastTemplate.vtype != HKXType.TYPE_STRUCT) {
			return HKXUtils.snapSize(lastTemplate.offset + MemberSizeResolver.getSize(lastTemplate.vtype), bestSnap);
		}
		HKXObject internalObject = (HKXObject) object.getMembersList().get(templates.size() - 1);
		return HKXUtils.snapSize(lastTemplate.offset + getSize(internalObject), bestSnap);
	}
}
