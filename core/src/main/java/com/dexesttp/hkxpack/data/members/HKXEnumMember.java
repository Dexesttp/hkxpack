package com.dexesttp.hkxpack.data.members;

import com.dexesttp.hkxpack.descriptor.enums.HKXType;

public class HKXEnumMember implements HKXMember {
	private final String name;
	private final HKXType type;
	private final HKXType subtype;
	private final String ename;
	private String value;

	public HKXEnumMember(String name, HKXType type, HKXType subtype, String target) {
		this.name = name;
		this.type = type;
		this.subtype = subtype;
		this.ename = target;
	}
	
	public void set(String value) {
		this.value = value;
	}
	
	public String get() {
		return value;
	}
	
	public HKXType getSubtype() {
		return subtype;
	}
	
	public String getEnumName() {
		return ename;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public HKXType getType() {
		return type;
	}
}
