package com.dexesttp.hkxpack.data.members;

import com.dexesttp.hkxpack.descriptor.enums.HKXType;

public class HKXDirectMember<T> implements HKXMember {

	private final HKXType type;
	private final String name;
	private T value;

	public HKXDirectMember(String name, HKXType type) {
		this.name = name;
		this.type = type;
	}
	
	public void set(T value) {
		this.value = value;
	}

	public  T get() {
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
