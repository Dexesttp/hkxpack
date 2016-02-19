package com.dexesttp.hkxpack.hkxreader.member;

import java.io.IOException;

import com.dexesttp.hkxpack.data.members.HKXMember;
import com.dexesttp.hkxpack.hkx.exceptions.InvalidPositionException;

public interface HKXMemberReader {
	public HKXMember read(long classOffset) throws IOException, InvalidPositionException;
}
