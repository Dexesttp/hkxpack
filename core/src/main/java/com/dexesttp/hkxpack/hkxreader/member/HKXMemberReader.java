package com.dexesttp.hkxpack.hkxreader.member;

import com.dexesttp.hkxpack.data.members.HKXMember;
import com.dexesttp.hkxpack.hkx.exceptions.InvalidPositionException;

/**
 * Reads a {@link HKXMember} from a HKX file.
 */
public interface HKXMemberReader {
	/**
	 * Reads a {@link HKXMember} from the HKX file.
	 * @param classOffset the offset of the class that contains the member.
	 * @return the read {@link HKXMember}.
	 * @throws InvalidPositionException if there was a position error while reading the {@link HKXMember}.
	 */
	HKXMember read(long classOffset) throws InvalidPositionException;
}
