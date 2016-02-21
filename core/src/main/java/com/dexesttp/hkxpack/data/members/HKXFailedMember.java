package com.dexesttp.hkxpack.data.members;

import com.dexesttp.hkxpack.descriptor.enums.HKXType;

public class HKXFailedMember implements HKXMember {
	private String name;
	private HKXType type;
	private String failMessage;

	public HKXFailedMember(String name, HKXType type, String failMessage) {
		this.name = name;
		this.type = type;
		this.failMessage = failMessage;
	}
	
	public String getFailMessage() {
		return "Couldn't read " + name + " : " + failMessage;
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
