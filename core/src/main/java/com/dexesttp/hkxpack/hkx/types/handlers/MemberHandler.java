package com.dexesttp.hkxpack.hkx.types.handlers;

import com.dexesttp.hkxpack.data.members.HKXMember;
import com.dexesttp.hkxpack.descriptor.enums.HKXType;

/**
 * Handles a member based on its type
 */
public interface MemberHandler {
	/**
	 * Creates a member from the given arguments.
	 * @param name
	 * @param type
	 * @param byteArray
	 * @return
	 */
	HKXMember createMember(String name, HKXType type, byte[] byteArray);

	/**
	 * Reads a member into a byteArray
	 * @param member the member to read
	 * @return the read byteArray
	 */
	byte[] readMember(HKXMember member);

	/**
	 * Retrieves the size of this member
	 * @return the member size
	 */
	long getSize();
}
