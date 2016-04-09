package com.dexesttp.hkxpack.tagreader.serialized;

import com.dexesttp.hkxpack.data.members.HKXMember;
import com.dexesttp.hkxpack.descriptor.exceptions.ClassFileReadException;
import com.dexesttp.hkxpack.descriptor.members.HKXMemberTemplate;
import com.dexesttp.hkxpack.tagreader.exceptions.InvalidTagXMLException;

/**
 * Handles a serialized member.
 */
public interface TagXMLSerializedHandler {
	/**
	 * Creates a {@link HKXMember} based on a member's content description as a {@link HKXMemberTemplate}
	 * @param memberTemplate the {@link HKXMemberTemplate} to build
	 * @return an empty {@link HKXMember}
	 * @throws InvalidTagXMLException 
	 * @throws ClassFileReadException 
	 */
	HKXMember handleMember(HKXMemberTemplate memberTemplate) throws ClassFileReadException, InvalidTagXMLException;
}
