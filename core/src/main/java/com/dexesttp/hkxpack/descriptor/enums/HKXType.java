package com.dexesttp.hkxpack.descriptor.enums;

/**
 * Contains a list of all supported types.
 * <p>
 * If a type is attempted to be retrieved while it isn't supported, the resulting type will be UNKNOWN.
 * It is expected for the program to either throw an exception or warn the user when an UNKNOWN type is handled.
 */
public enum HKXType {
// Base types
	UNKNOWN(HKXTypeFamily.UNKNOWN),
	TYPE_NONE(HKXTypeFamily.UNKNOWN),
	
// Direct types
	TYPE_VOID(HKXTypeFamily.DIRECT),
	TYPE_BOOL(HKXTypeFamily.DIRECT),
	TYPE_CHAR(HKXTypeFamily.DIRECT),
	TYPE_INT8(HKXTypeFamily.DIRECT),
	TYPE_UINT8(HKXTypeFamily.DIRECT),
	TYPE_INT16(HKXTypeFamily.DIRECT),
	TYPE_UINT16(HKXTypeFamily.DIRECT),
	TYPE_INT32(HKXTypeFamily.DIRECT),
	TYPE_UINT32(HKXTypeFamily.DIRECT),
	TYPE_INT64(HKXTypeFamily.DIRECT),
	TYPE_UINT64(HKXTypeFamily.DIRECT),
	TYPE_REAL(HKXTypeFamily.DIRECT),
	// Direct types (Unsure)
	TYPE_ULONG(HKXTypeFamily.DIRECT),
	TYPE_HALF(HKXTypeFamily.DIRECT),

// Complex types
	TYPE_MATRIX3(HKXTypeFamily.COMPLEX),
	TYPE_MATRIX4(HKXTypeFamily.COMPLEX),
	TYPE_VECTOR4(HKXTypeFamily.COMPLEX),
	TYPE_TRANSFORM(HKXTypeFamily.COMPLEX),
	TYPE_QSTRANSFORM(HKXTypeFamily.COMPLEX),
	TYPE_QUATERNION(HKXTypeFamily.COMPLEX),

// Enum types
	TYPE_ENUM(HKXTypeFamily.ENUM),
	TYPE_FLAGS(HKXTypeFamily.ENUM),
	
// Array types
	TYPE_SIMPLEARRAY(HKXTypeFamily.ARRAY),
	TYPE_ARRAY(HKXTypeFamily.ARRAY),
	TYPE_RELARRAY(HKXTypeFamily.ARRAY),
	
// Pointer types
	TYPE_POINTER(HKXTypeFamily.POINTER),
	TYPE_FUNCTIONPOINTER(HKXTypeFamily.POINTER),
	
// String types
	TYPE_CSTRING(HKXTypeFamily.STRING),
	TYPE_STRINGPTR(HKXTypeFamily.STRING),

// Class types
	TYPE_STRUCT(HKXTypeFamily.OBJECT);
	
	
	private final HKXTypeFamily family;

	private HKXType(HKXTypeFamily family) {
		this.family = family;
	}
	
	public static HKXType fromString(String string) {
		if(string.equals(""))
			return HKXType.TYPE_NONE;
		try {
			return HKXType.valueOf(string);
		} catch(Exception e) {
			return HKXType.UNKNOWN;
		}
	}
	
	public HKXTypeFamily getFamily() {
		return this.family;
	}
}
