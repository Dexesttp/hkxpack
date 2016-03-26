package com.dexesttp.hkxpack.data;

import java.util.ArrayList;
import java.util.List;

import com.dexesttp.hkxpack.data.members.HKXMember;
import com.dexesttp.hkxpack.descriptor.HKXDescriptor;
import com.dexesttp.hkxpack.descriptor.enums.HKXType;

/**
 * Represents a HKX object instantiating a class, stored in memory.
 */
public class HKXObject implements HKXMember {
	private final String name;
	private final HKXDescriptor descriptor;
	private final List<HKXMember> members;
	
	/**
	 * Creates a HKXObject
	 * @param name the name of the object to create
	 * @param template the template to create the object from.
	 */
	public HKXObject(String name, HKXDescriptor template) {
		this(name, template, new ArrayList<HKXMember>());
	}

	public HKXObject(String name, HKXDescriptor descriptor, ArrayList<HKXMember> members) {
		this.name = name;
		this.descriptor = descriptor;
		this.members = members;
	}
	
	public String getName() {
		return name;
	}
	
	public HKXDescriptor getDescriptor() {
		return descriptor;
	}
	
	public List<HKXMember> members() {
		return members;
	}

	@Override
	public HKXType getType() {
		return HKXType.TYPE_STRUCT;
	}
}
