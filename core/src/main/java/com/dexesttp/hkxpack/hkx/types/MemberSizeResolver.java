package com.dexesttp.hkxpack.hkx.types;

import com.dexesttp.hkxpack.descriptor.HKXDescriptor;
import com.dexesttp.hkxpack.descriptor.enums.HKXType;
import com.dexesttp.hkxpack.hkx.types.handlers.MemberHandlerFactory;

/**
 * Intended to retrieve {@link HKXType}-specific data.
 * <p>
 * {@link #getSize(HKXType)} retrieves the size of a {@link HKXType}
 * {@link #getSize(HKXDescriptor)} retrieves the size of a
 * {@link HKXDescriptor}, including padding.
 */
public final class MemberSizeResolver {
	private static final long PTR_SIZE = 0x08;

	private MemberSizeResolver() {
		// NO OP
	}

	/**
	 * Retrieve the size of a standard {@link HKXType}.
	 * 
	 * @param type the {@link HKXType} to retrieve the size of.
	 * @return the {@link HKXType}'s size.
	 * @throws IllegalArgumentException if the given {@link HKXType} isn't standard.
	 */
	public static long getSize(final HKXType type) {
		switch (type) {
		case TYPE_NONE:
		case TYPE_VOID:
			return 0X00;
		case TYPE_ENUM:
		case TYPE_FLAGS:
			return 0X04;
		// Base values
		case TYPE_BOOL:
		case TYPE_CHAR:
		case TYPE_UINT8:
		case TYPE_INT8:
		case TYPE_HALF:
		case TYPE_UINT16:
		case TYPE_INT16:
		case TYPE_ULONG:
		case TYPE_UINT32:
		case TYPE_INT32:
		case TYPE_UINT64:
		case TYPE_INT64:
		case TYPE_REAL:
			// Complex values
		case TYPE_VECTOR4:
		case TYPE_QUATERNION:
		case TYPE_QSTRANSFORM:
		case TYPE_MATRIX3:
		case TYPE_TRANSFORM:
		case TYPE_MATRIX4:
			return MemberHandlerFactory.getMemberHandler(type).getSize();
		// Strings and ptrs
		case TYPE_CSTRING:
		case TYPE_STRINGPTR:
			return PTR_SIZE;
		case TYPE_FUNCTIONPOINTER:
		case TYPE_POINTER:
			return PTR_SIZE;
		// Arrays
		case TYPE_RELARRAY:
			return 0x4;
		case TYPE_ARRAY:
		case TYPE_SIMPLEARRAY:
			return 0X10;
		default:
			break;
		}
		throw new IllegalArgumentException(type.toString() + " can't be analyzed with MemberTypeResolver#getSize");
	}
}
