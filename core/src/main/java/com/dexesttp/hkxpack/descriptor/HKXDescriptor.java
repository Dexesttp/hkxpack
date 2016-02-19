package com.dexesttp.hkxpack.descriptor;

import java.util.List;

import com.dexesttp.hkxpack.descriptor.members.HKXMemberTemplate;

/**
 * A HKXDescriptor contains a name, a flag and a list of members.
 */
public class HKXDescriptor {
	private final String name;
	private final long signature;
	private final List<HKXMemberTemplate> members;
	
	/**
	 * Creates a HKXDescriptor
	 * <p>
	 * Note : you should use {@link HKXDescriptorFactory#get(String)} to get a HKXDescriptor, unless you're doing something very technical.
	 * @param name
	 * @param signature
	 * @param members
	 */
	public HKXDescriptor(final String name, final long signature, final List<HKXMemberTemplate> members) {
		this.name = name;
		this.signature = signature;
		this.members = members;
	}
	
	public String getName() {
		return name;
	}
	
	public long getSignature() {
		return signature;
	}
	
	public List<HKXMemberTemplate> getMemberTemplates() {
		return members;
	}
}
