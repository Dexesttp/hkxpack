package com.dexesttp.hkxpack.descriptor.enums;

public enum Flag {
	UNKNOWN,
	FLAGS_NONE,
	NOT_OWNED,
	ALIGN_8,
	ALIGN_16,
	SERIALIZE_IGNORED;
	
	public static Flag fromString(String string) {
		try {
			return Flag.valueOf(string);
		} catch(Exception e) {
			return Flag.UNKNOWN;
		}
	}
}
