package com.dexesttp.hkxpack.hkxreader.member.arrays;

import com.dexesttp.hkxpack.data.HKXData;
import com.dexesttp.hkxpack.hkx.exceptions.InvalidPositionException;

public interface HKXArrayContentsReader {
	public HKXData getContents(long arrayStart, int position) throws InvalidPositionException;
}
