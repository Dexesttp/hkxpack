package com.dexesttp.hkxpack.hkx.types;

import com.dexesttp.hkxpack.data.members.HKXMember;
import com.dexesttp.hkxpack.descriptor.enums.HKXType;
import com.dexesttp.hkxpack.hkx.types.handlers.MemberHandlerFactory;

/**
 * Intended to retrieve {@link HKXType}-specific data.
 * {@link #getMember(String, HKXType, byte[])} converts a {@link byte} array to
 * a {@link HKXMember}, given the {@link HKXType} of the member is standard.
 */
public final class MemberDataResolver {
	private MemberDataResolver() {
		// NO OP
	}

	/**
	 * Read a simple / defined member from a byte array.
	 * 
	 * @param name      the name of the member to create.
	 * @param type      the {@link HKXType} of data to convert the array into.
	 * @param byteArray the {@link byte} array to read the member from.
	 * @return the {@link HKXMember} containing the data.
	 * @throws IllegalArgumentException if the given {@link HKXType} isn't standard.
	 */
	public static HKXMember getMember(final String name, final HKXType type, final byte[] byteArray) {
		return MemberHandlerFactory.getMemberHandler(type).createMember(name, type, byteArray);
	}

	/**
	 * Write a simple / defined member to a byte array.
	 * 
	 * @param member the {@link HKXMember} of data to create the array from.
	 * @return the byte aray containing the data.
	 * @throws IllegalArgumentException if the given {@link HKXMember} isn't
	 *                                  standard.
	 */
	public static byte[] fromMember(final HKXMember member) {
		return MemberHandlerFactory.getMemberHandler(member.getType()).readMember(member);
	}
}
