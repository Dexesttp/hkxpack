package com.dexesttp.hkxpack.hkxreader.member.arrays;

import com.dexesttp.hkxpack.data.HKXData;
import com.dexesttp.hkxpack.hkx.exceptions.InvalidPositionException;

/**
 * Reads array-specific contents.
 */
public interface HKXArrayContentsReader {
	/**
	 * Reads array contents as {@link HKXData}.
	 * @param arrayStart the start of the array contents.
	 * @param position the position in the array
	 * @return the read {@link HKXData}.
	 * @throws InvalidPositionException if there was an error accessing the array data.
	 */
	HKXData getContents(long arrayStart, int position) throws InvalidPositionException;
}
