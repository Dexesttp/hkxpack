package com.dexesttp.hkxpack.data.members;

import com.dexesttp.hkxpack.descriptor.enums.HKXType;

public class HKXPointerMember implements HKXMember {
	private final String name;
	private final HKXType type;
	private final HKXType subtype;
	private String value;
	
	public HKXPointerMember(String name, HKXType type, HKXType subtype, String target) {
		this.name = name;
		this.type = type;
		this.subtype = subtype;
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

	@Override
	public String getName() {
		return name;
	}

	@Override
	public HKXType getType() {
		return type;
	}
}
