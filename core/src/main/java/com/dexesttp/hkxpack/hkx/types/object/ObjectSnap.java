package com.dexesttp.hkxpack.hkx.types.object;

import java.util.List;

import com.dexesttp.hkxpack.data.HKXObject;
import com.dexesttp.hkxpack.descriptor.HKXDescriptor;
import com.dexesttp.hkxpack.descriptor.HKXDescriptorFactory;
import com.dexesttp.hkxpack.descriptor.enums.HKXType;
import com.dexesttp.hkxpack.descriptor.enums.HKXTypeFamily;
import com.dexesttp.hkxpack.descriptor.exceptions.ClassFileReadException;
import com.dexesttp.hkxpack.descriptor.members.HKXMemberTemplate;

/**
 * Handle object snapping
 */
final class ObjectSnap {
	private ObjectSnap() {
		// NO OP
	}

	static long getSnap(final HKXDescriptor descriptor, final HKXDescriptorFactory descriptorFactory) throws ClassFileReadException {
		long bestSnap = 0;
		List<HKXMemberTemplate> list = descriptor.getMemberTemplates();
		for(int i = 0; i < list.size(); i++) {
			HKXMemberTemplate template = list.get(i);
			long currSnap = 0;
			if(template.vtype.getFamily() == HKXTypeFamily.ENUM) {
				currSnap = PrimitiveSnap.primitiveSnap(template.vsubtype);
			}
			else if(template.vtype == HKXType.TYPE_STRUCT) {
				HKXDescriptor internalDescriptor = descriptorFactory.get(template.target);
				currSnap = getSnap(internalDescriptor, descriptorFactory);
			}
			else {
				currSnap = PrimitiveSnap.primitiveSnap(template.vtype);
			}
			bestSnap = currSnap > bestSnap ? currSnap : bestSnap;
		}
		return bestSnap;
	}

	static long getSnap(final HKXObject object) {
		long bestSnap = 0;
		List<HKXMemberTemplate> list = object.getDescriptor().getMemberTemplates();
		for(int i = 0; i < list.size(); i++) {
			HKXMemberTemplate template = list.get(i);
			long currSnap = 0;
			if(template.vtype.getFamily() == HKXTypeFamily.ENUM) {
				currSnap = PrimitiveSnap.primitiveSnap(template.vsubtype);
			}
			else if(template.vtype == HKXType.TYPE_STRUCT) {
				HKXObject internalObject = (HKXObject) object.getMembersList().get(i);
				currSnap = getSnap(internalObject);
			}
			else {
				currSnap = PrimitiveSnap.primitiveSnap(template.vtype);
			}
			bestSnap = currSnap > bestSnap ? currSnap : bestSnap;
		}
		return bestSnap;
	}
}
