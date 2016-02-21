package com.dexesttp.hkxpack.data.members;

import com.dexesttp.hkxpack.descriptor.enums.HKXType;

public class HKXStringMember implements HKXMember {
	private final String name;
	private final HKXType type;
	private String value;

	public HKXStringMember(String name, HKXType type) {
		this.name = name;
		this.type = type;
	}
	
	public void set(String value) {
		this.value = value;
	}
	
	public String get() {
		return value;
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
