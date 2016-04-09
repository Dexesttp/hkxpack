package com.dexesttp.hkxpack.data.members;

import com.dexesttp.hkxpack.descriptor.enums.HKXType;

/**
 * Represents a member that failed to be imported.
 */
public class HKXFailedMember implements HKXMember {
	private final String name;
	private final HKXType type;
	private final String failMessage;

	/**
	 * Creates a {@link HKXFailedMember}.
	 * @param name the name of the failed member.
	 * @param type the intended type of the failed member.
	 * @param failMessage why the member failed to be imported.
	 */
	public HKXFailedMember(final String name, final HKXType type, final String failMessage) {
		this.name = name;
		this.type = type;
		this.failMessage = failMessage;
	}
	
	/**
	 * Returns a sensible message, in english, about the import fail.
	 * @return
	 */
	public String getFailMessage() {
		return "Couldn't read " + name + " : " + failMessage;
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
