package com.dexesttp.hkxpack.data.members;

import java.util.ArrayList;
import java.util.List;

import com.dexesttp.hkxpack.data.HKXData;
import com.dexesttp.hkxpack.descriptor.enums.HKXType;

public class HKXArrayMember implements HKXMember {
	
	private String name;
	private HKXType type;
	private HKXType subtype;
	private List<HKXData> contents;

	public HKXArrayMember(String name, HKXType type, HKXType subtype) {
		this.name = name;		
		this.type = type;
		this.subtype = subtype;
		this.contents = new ArrayList<>();
	}

	public HKXType getSubType() {
		return subtype;
	}
	
	public void add(HKXData data) {
		if(subtype != HKXType.TYPE_NONE && data.getType() != subtype)
			throw new IllegalArgumentException("Array data type is defined");
		this.contents.add(data);
	}
	
	public HKXData get(int pos) {
		return contents.get(pos);
	}
	
	public List<HKXData> contents() {
		return this.contents;
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
