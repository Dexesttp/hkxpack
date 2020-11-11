package com.dexesttp.hkxpack.data.members;

import com.dexesttp.hkxpack.descriptor.enums.HKXType;

/**
 * A {@link HKXMember} whose content is a String.
 */
public class HKXStringMember implements HKXMember {
	private final String name;
	private final HKXType type;
	private transient String value;

	/**
	 * Creates a {@link HKXStringMember}.
	 * 
	 * @param name the name of the {@link HKXStringMember}.
	 * @param type the {@link HKXType} of the {@link HKXStringMember}. Note that
	 *             {@link HKXType#getFamily()} should return
	 *             {@link HKXTypeFamily#STRING}, although this isn't checked.
	 */
	public HKXStringMember(final String name, final HKXType type) {
		this.name = name;
		this.type = type;
	}

	/**
	 * Set the String of this member.
	 * 
	 * @param value the new String to affect.
	 */
	public void set(final String value) {
		this.value = value;
	}

	/**
	 * Get this {@link HKXStringMember}'s content.
	 * 
	 * @return the value of this member.
	 */
	public String get() {
		return value;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public String getName() {
		return name;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public HKXType getType() {
		return type;
	}
}
