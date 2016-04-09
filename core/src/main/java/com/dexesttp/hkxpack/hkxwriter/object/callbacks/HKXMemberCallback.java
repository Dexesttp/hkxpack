package com.dexesttp.hkxpack.hkxwriter.object.callbacks;

import java.util.List;

/**
 * Defines a {@link HKXMember} post operations
 * @see #process(List, long)
 */
public interface HKXMemberCallback {
	/**
	 * Handles a {@link HKXMember} post operations.
	 * @param memberCallbacks the callback list to add to.
	 * @param position the position in the file at the beginning of the post process
	 * @return the next valid position at the end of the post process
	 */
	long process(List<HKXMemberCallback> memberCallbacks, long position);
}
