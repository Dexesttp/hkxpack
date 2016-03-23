package com.dexesttp.hkxpack.hkxreader.member.arrays;

import java.io.IOException;

import com.dexesttp.hkxpack.data.HKXData;
import com.dexesttp.hkxpack.hkx.exceptions.InvalidPositionException;

public interface HKXArrayContentsReader {
	public long getSize();
	public HKXData getContents(long arrayStart, int position) throws IOException, InvalidPositionException;
}
