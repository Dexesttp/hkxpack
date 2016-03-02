package com.dexesttp.hkxpack.hkx.types;

import java.util.List;

import com.dexesttp.hkxpack.data.HKXObject;
import com.dexesttp.hkxpack.descriptor.HKXDescriptor;
import com.dexesttp.hkxpack.descriptor.HKXDescriptorFactory;
import com.dexesttp.hkxpack.descriptor.enums.HKXType;
import com.dexesttp.hkxpack.descriptor.exceptions.ClassFileReadError;
import com.dexesttp.hkxpack.descriptor.members.HKXMemberTemplate;
import com.dexesttp.hkxpack.hkx.HKXUtils;

/**
 * Resolve the size of a {@link HKXObject} or a {@link HKXDescriptor}.
 */
public class ObjectSizeResolver {
	private static final long PTR_SIZE = 0x08;

	private static long primitiveSnap(HKXType type) {
		switch(type) {
		case TYPE_NONE:
		case TYPE_VOID:
			return 0X00;
		case TYPE_ENUM:
		case TYPE_FLAGS:
			return 0X04;
	// Base values
		case TYPE_BOOL:
			return 0X01;
		case TYPE_CHAR:
			return 0X01;
		case TYPE_UINT8:
		case TYPE_INT8:
			return 0X01;
		case TYPE_HALF:
		case TYPE_UINT16:
		case TYPE_INT16:
			return 0X02;
		case TYPE_ULONG:
		case TYPE_UINT32:
		case TYPE_INT32:
			return 0X04;
		case TYPE_UINT64:
		case TYPE_INT64:
			return 0X08;
		case TYPE_REAL:
			return 0X08;
	// Complex values
		case TYPE_MATRIX3:
			return 0x0A;
		case TYPE_VECTOR4:
		case TYPE_QUATERNION:
		case TYPE_TRANSFORM:
			return 0x04;
		case TYPE_QSTRANSFORM:
			return 0x04;
	// Strings and ptrs
		case TYPE_CSTRING:
		case TYPE_STRINGPTR:
			return PTR_SIZE ;
		case TYPE_FUNCTIONPOINTER:
		case TYPE_POINTER:
			return PTR_SIZE;
	// Arrays
		case TYPE_RELARRAY:
			return 0x4;
		case TYPE_ARRAY:
		case TYPE_SIMPLEARRAY:
			return PTR_SIZE;
		default:
			return 0x00;
		}
	}
	
	/**
	 * Retrieves the size of a {@link HKXDescriptor}, including end padding if needed.
	 * @param descriptor the {@link HKXDescriptor} to retrieve the size from.
	 * @return the {@link HKXDescriptor}'s size, in bytes.
	 * @throws ClassFileReadError if there was an error resolving this {@link HKXDescriptor}'s subclass 
	 */
	public static long getSize(HKXDescriptor descriptor, HKXDescriptorFactory descriptorFactory) throws ClassFileReadError {
		List<HKXMemberTemplate> templates = descriptor.getMemberTemplates();
		if(templates.isEmpty())
			return 0;
		long bestSnap = getSnap(descriptor, descriptorFactory);
		HKXMemberTemplate lastTemplate = templates.get(templates.size() - 1);
		if(lastTemplate.vtype != HKXType.TYPE_STRUCT)
			return HKXUtils.snapSize(lastTemplate.offset + MemberSizeResolver.getSize(lastTemplate.vtype), bestSnap);
		HKXDescriptor internalDescriptor = descriptorFactory.get(lastTemplate.target);
		return HKXUtils.snapSize(lastTemplate.offset + getSize(internalDescriptor, descriptorFactory), bestSnap);
	}

	private static long getSnap(HKXDescriptor descriptor, HKXDescriptorFactory descriptorFactory) throws ClassFileReadError {
		long bestSnap = 0;
		List<HKXMemberTemplate> list = descriptor.getMemberTemplates();
		for(int i = 0; i < list.size(); i++) {
			HKXMemberTemplate template = list.get(i);
			long currSnap = 0;
			if(template.vtype != HKXType.TYPE_STRUCT)
				currSnap = primitiveSnap(template.vtype);
			else {
				HKXDescriptor internalDescriptor = descriptorFactory.get(template.target);
				currSnap = getSnap(internalDescriptor, descriptorFactory);
			}
			bestSnap = currSnap > bestSnap ? currSnap : bestSnap;
		}
		return bestSnap;
	}

	/**
	 * Retrieves the size of a {@link HKXObject}, including end padding if needed.
	 * @param object the {@link HKXObject} to retrieve the size from.
	 * @return the {@link HKXObject}'s size, in bytes.
	 */
	public static long getSize(HKXObject object) {
		List<HKXMemberTemplate> templates = object.getDescriptor().getMemberTemplates();
		if(templates.isEmpty())
			return 0;
		long bestSnap = getSnap(object);
		HKXMemberTemplate lastTemplate = templates.get(templates.size() - 1);
		if(lastTemplate.vtype != HKXType.TYPE_STRUCT)
			return HKXUtils.snapSize(lastTemplate.offset + MemberSizeResolver.getSize(lastTemplate.vtype), bestSnap);
		HKXObject internalObject = (HKXObject) object.members().get(templates.size() - 1);
		return HKXUtils.snapSize(lastTemplate.offset + getSize(internalObject), bestSnap);
	}

	private static long getSnap(HKXObject object) {
		long bestSnap = 0;
		List<HKXMemberTemplate> list = object.getDescriptor().getMemberTemplates();
		for(int i = 0; i < list.size(); i++) {
			HKXMemberTemplate template = list.get(i);
			long currSnap = 0;
			if(template.vtype != HKXType.TYPE_STRUCT)
				currSnap = primitiveSnap(template.vtype);
			else {
				HKXObject internalObject = (HKXObject) object.members().get(i);
				currSnap = getSnap(internalObject);
			}
			bestSnap = currSnap > bestSnap ? currSnap : bestSnap;
		}
		return bestSnap;
	}
}
