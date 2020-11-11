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
	private final transient List<HKXMember> members;

	/**
	 * Creates a {@link HKXObject}.
	 * 
	 * @param name     the name of the object to create
	 * @param template the template to create the object from.
	 */
	public HKXObject(final String name, final HKXDescriptor template) {
		this(name, template, new ArrayList<HKXMember>());
	}

	/**
	 * Creates a {@link HKXObject}
	 * 
	 * @param name       the name of the object to create
	 * @param descriptor the descriptor to create the object from.
	 * @param members    the list of members to add to the object.
	 */
	public HKXObject(final String name, final HKXDescriptor descriptor, final List<HKXMember> members) {
		this.name = name;
		this.descriptor = descriptor;
		this.members = members;
	}

	/**
	 * Get this object's name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get this object's descriptor.
	 * 
	 * @return the {@link HKXDescriptor} that represents this object.
	 */
	public HKXDescriptor getDescriptor() {
		return descriptor;
	}

	/**
	 * Get this {@link HKXObject}'s member list.
	 * 
	 * @return an ordered list of all the members of this object.
	 */
	public List<HKXMember> getMembersList() {
		return members;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public HKXType getType() {
		return HKXType.TYPE_STRUCT;
	}
}
