package com.dexesttp.hkxpack.hkxwriter.object;

import com.dexesttp.hkxpack.data.members.HKXMember;
import com.dexesttp.hkxpack.hkxwriter.object.callbacks.HKXMemberCallback;

/**
 * Handles writing a {@link HKXMember} to a HKX File.
 * 
 * @see #write(HKXMember, long)
 */
public interface HKXMemberHandler {
	/**
	 * Writes a {@link HKXMember}'s contents to the HKX File.
	 * 
	 * @param member     the {@link HKXMember} to write
	 * @param currentPos the current position of the class in the file
	 * @return this member's delayed operation while writing.
	 */
	HKXMemberCallback write(HKXMember member, long currentPos);
}
