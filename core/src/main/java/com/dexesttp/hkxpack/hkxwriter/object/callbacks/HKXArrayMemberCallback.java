package com.dexesttp.hkxpack.hkxwriter.object.callbacks;

import java.util.List;

/**
 * General purpose callback handler for an Array
 */
public interface HKXArrayMemberCallback {
	/**
	 * Proces an array component callback list to its end.
	 * @param memberCallbacks the {@link HKXMemberCallback} to process
	 * @param position the start position of the array values
	 * @return the first valid position after the array
	 */
	long process(List<HKXMemberCallback> memberCallbacks, long position);
}
