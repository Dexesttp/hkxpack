package com.dexesttp.hkxpack.hkx.types.object;

import com.dexesttp.hkxpack.descriptor.enums.HKXType;

/**
 * Contains all the primitive snap sizes
 */
final class PrimitiveSnap {
	private static final long PTR_SIZE = 0x08;
	private PrimitiveSnap() {
		// NO OP
	}
	/**
	 * Get the snap sizes for all primitive types
	 */
	static long primitiveSnap(final HKXType type) {
		switch(type) {
		// Base values
			case TYPE_BOOL:
			case TYPE_CHAR:
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
			case TYPE_REAL:
		// Complex values
			case TYPE_VECTOR4:
			case TYPE_QUATERNION:
			case TYPE_TRANSFORM:
			case TYPE_QSTRANSFORM:
			case TYPE_MATRIX3:
			case TYPE_MATRIX4:
		// Relative Arrays
			case TYPE_RELARRAY:
				return 0x04;
		// Big base values
			case TYPE_UINT64:
			case TYPE_INT64:
				return 0X08;
		// Strings and ptrs
			case TYPE_CSTRING:
			case TYPE_STRINGPTR:
			case TYPE_FUNCTIONPOINTER:
			case TYPE_POINTER:
		// Arrays
			case TYPE_ARRAY:
			case TYPE_SIMPLEARRAY:
				return PTR_SIZE;
			case TYPE_NONE:
			case TYPE_VOID:
			default:
				return 0x00;
		}
	}
}
